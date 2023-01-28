package com.connorcode.mastodonlink;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import static com.connorcode.mastodonlink.MastodonLink.*;

public class Mastodon {
    public String acct = "";
    String token;

    void auth() {
        var cfg = plugin.getConfig();
        var savedToken = cfg.get("bot.token");
        if (savedToken != null) {
            token = savedToken.toString();
            logger.log(Level.INFO, "Using saved token");
            refreshAcct();
            return;
        }

        var client = HttpClient.newHttpClient();
        var data = Map.of(
                "grant_type", "authorization_code",
                "redirect_uri", "urn:ietf:wg:oauth:2.0:oob",
                "scope", "read write",
                "client_id", config.bot.clientId(),
                "client_secret", config.bot.clientSecret(),
                "code", config.bot.auth()
        );

        var request = HttpRequest.newBuilder()
                .uri(query(URI.create(config.bot.address() + "/oauth/token"), data))
                .method("POST", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var json = JsonParser.parseString(response.body()).getAsJsonObject();
            if (json.has("error")) {
                logger.log(Level.SEVERE,
                        String.format("Error getting token (%s) %s", json.get("error"), json.get("error_description")));
                return;
            }

            token = json.get("access_token").getAsString();
            cfg.set("bot.token", token);
            plugin.saveConfig();

            logger.log(Level.ALL, "Got token!");
            refreshAcct();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void initEventHandler() {
        // get notifs
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(this::getNewNotifications, 0, config.bot.refresh(),
                        java.util.concurrent.TimeUnit.SECONDS);
    }

    void getNewNotifications() {
        request("GET", "notifications", Map.of("types", "mention direct"), (response, json) -> {
            json.getAsJsonArray().forEach(notif -> {
                var notifJson = notif.getAsJsonObject();
                var id = notifJson.get("id").getAsString();

                final var processTypes = List.of("direct", "mention");
                if (processTypes.contains(notifJson.get("type").getAsString())) processMention(notifJson);

                request("POST", String.format("notifications/%s/dismiss", id), Map.of(), (response1, json1) -> {});
            });
        });
    }

    void processMention(JsonObject notifJson) {
        var id = notifJson.get("status").getAsJsonObject().get("id").getAsString();
        var account = notifJson.get("account").getAsJsonObject();
        var acct = Misc.mastodonAcct(account.get("acct").getAsString(), account.get("url").getAsString());
        logger.log(Level.INFO, String.format("Processing mention %s from %s", id, acct));
        var code = Misc.findCode(notifJson.get("status").getAsJsonObject().get("content").getAsString());
        if (code.isEmpty()) {
            logger.log(Level.INFO, String.format("No code found in mention %s", id));
            request("POST", "statuses", Map.of(
                    "status", String.format("@%s I couldn't find a code in your message.", acct),
                    "in_reply_to_id", id,
                    "visibility", "direct"
            ), (response, json) -> {});
            return;
        }

        var name = database.confirmAccount(code.get(), acct, account.get("id").getAsString());
        if (name.isEmpty()) {
            logger.log(Level.INFO, String.format("Code %s not found in pending codes", code.get()));
            request("POST", "statuses", Map.of(
                    "status", String.format("@%s That code is not valid.", acct),
                    "in_reply_to_id", id,
                    "visibility", "direct"
            ), (response, json) -> {});
            return;
        }

        logger.log(Level.INFO, String.format("Code %s found in pending codes", code.get()));
        request("POST", "statuses", Map.of(
                "status", String.format("@%s You are now linked to %s.", acct, name.get()),
                "in_reply_to_id", id,
                "visibility", "direct"
        ), (response, json) -> {});
    }

    private void refreshAcct() {
        request("GET", "accounts/verify_credentials", Map.of(), (response, json) -> {
            acct = json.getAsJsonObject().get("acct").getAsString() + "@" + URI.create(config.bot.address()).getHost();
            logger.log(Level.ALL, "Logged in as " + acct);
        });
    }

    private void request(String method, String endpoint, Map<String, String> data, RequestCallback callback) {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(query(URI.create(config.bot.address() + "/api/v1/" + endpoint), data))
                .header("Authorization", "Bearer " + token)
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();

        var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> {
            var json = JsonParser.parseString(res.body());
            if (json.isJsonObject() && json.getAsJsonObject().has("error"))
                logger.log(Level.SEVERE, "Mastodon error: " + json.getAsJsonObject().get("error").getAsString());

            callback.call(res, json);
        });

        response.exceptionally(e -> {
            logger.log(Level.SEVERE, "Mastodon error: " + e.getMessage());
            return null;
        });
    }

    URI query(URI uri, Map<String, String> data) {
        var builder = new StringBuilder(uri.toString()).append("?");
        for (var entry : data.entrySet())
            builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .append("&");

        // dont ask me how long it took me to figure this out
        var out = builder.toString().replace("+", "%20");
        if (out.endsWith("&")) out = out.substring(0, out.length() - 1);
        return URI.create(out);
    }

    interface RequestCallback {
        void call(HttpResponse<String> response, JsonElement json);
    }
}

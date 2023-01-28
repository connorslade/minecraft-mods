package com.connorcode.mastodonlink;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
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
            var json = new Gson().fromJson(response.body(), Map.class);
            if (json.containsKey("error")) {
                logger.log(Level.SEVERE,
                        String.format("Error getting token (%s) %s", json.get("error"), json.get("error_description")));
                return;
            }

            token = (String) json.get("access_token");
            cfg.set("bot.token", token);
            plugin.saveConfig();

            logger.log(Level.ALL, "Got token!");
            refreshAcct();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshAcct() {
        request("GET", "accounts/verify_credentials", Map.of(), (response, json) -> {
            acct = json.get("acct") + "@" + URI.create(config.bot.address()).getHost();
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
            var json = new Gson().fromJson(res.body(), Map.class);
            if (json.containsKey("error"))
                logger.log(Level.SEVERE, "Mastodon error: " + json.get("error"));

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
        void call(HttpResponse<String> response, Map<?, ?> json);
    }
}

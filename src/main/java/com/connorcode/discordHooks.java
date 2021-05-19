package com.connorcode;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class discordHooks {
    public static String hookUrl = "";

    public static void jsonPost(String passedUrl, String data) throws IOException {
        byte[] out = data.getBytes(StandardCharsets.UTF_8);
        URL url = new URL(passedUrl);
        int length = out.length;
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json");
        http.connect();
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
    }

    public static void onStart() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String word = common.randomFromArray(wordList.goodWords);
        jsonPost(hookUrl, "{\"content\":null,\"embeds\":[{\"title\":\"\uD83C\uDF20 Server Started!\",\"description\":\"Today is going to be a **" + word + "** day!\",\"color\":4123687,\"footer\":{\"text\":\"" + timeStamp + "\"}}]}");
    }

    public static void onStop() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String word = common.randomFromArray(wordList.badWords);
        jsonPost(hookUrl, "{\"content\":null,\"embeds\":[{\"title\":\"\uD83D\uDED1 Server Stopped\",\"description\":\"Could be a **" + word + "** day : /\",\"color\":15795461,\"footer\":{\"text\":\"" + timeStamp + "\"}}]}");
    }

    public static void onTpsDrop(double tps) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String word = common.randomFromArray(wordList.badWords);
        jsonPost(hookUrl, "{\"content\":null,\"embeds\":[{\"title\":\"⌛ Low TPS\",\"description\":\"This is **" + word + "**...\\nServer TPS is `" + String.valueOf(tps) + "TPS`\",\"color\":15494695,\"footer\":{\"text\":\"" + timeStamp + "\"}}]}");
    }

    public static void onPlayerJoin(String playerName, @NotNull UUID uuid) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        jsonPost(hookUrl, "{\"content\":null,\"embeds\":[{\"title\":\"\uD83E\uDD98 Server Join\",\"description\":\"`" + playerName + "` joined the server!\",\"color\":3781367,\"footer\":{\"text\":\"" + timeStamp + "\"},\"thumbnail\":{\"url\":\"https://crafatar.com/avatars/" + uuid.toString() + "\"}}]}");
    }

    public static void onPlayerLeave(String playerName, @NotNull UUID uuid) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        jsonPost(hookUrl, "{\"content\":null,\"embeds\":[{\"title\":\"\uD83C\uDF3F Server Leave\",\"description\":\"`" + playerName + "` left the server!\",\"color\":11346938,\"footer\":{\"text\":\"" + timeStamp + "\"},\"thumbnail\":{\"url\":\"https://crafatar.com/avatars/" + uuid.toString() + "\"}}]}");
    }
}
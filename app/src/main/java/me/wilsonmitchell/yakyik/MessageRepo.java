package me.wilsonmitchell.yakyik;

import android.location.Location;
import android.util.Log;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wilsonmitchell on 11/5/14.
 */
public class MessageRepo {

    public static final String ENDPOINT = "wilsonmitchell.me";
    public static final int PORT = 5000;
    public static final String MESSAGES_ENDPOINT = "/messages";
    public static final String UPVOTE_ENDPOINT = "/messages/upvote";
    public static final String DOWNVOTE_ENDPOINT = "/messages/downvote";

    public static List<Message> getNearbyMessages(Location location) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpHost httpHost = new HttpHost(ENDPOINT, PORT);
        HttpRequest messageRequest = new HttpGet(MESSAGES_ENDPOINT
                + "?longitude=" + location.getLongitude()
                + "&latitude=" + location.getLatitude());

        HttpResponse httpResponse;
        InputStream is;
        try {
            httpResponse = httpClient.execute(httpHost, messageRequest);
            is = httpResponse.getEntity().getContent();
        }
        catch (Exception e) {
            return new ArrayList<Message>();
        }

        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            return new ArrayList<Message>();
        }

        List<Message> messageList = new ArrayList<Message>();
        JSONObject messagesJson = ServerUtils.fromInputStream(is);
        JSONArray messageArray = messagesJson.optJSONArray("messages");
        if (messageArray != null) {
            for (int i = 0; i < messageArray.length(); i++) {
                JSONObject messageObj = messageArray.optJSONObject(i);
                String messageText = messageObj.optString("message", "");
                long timePosted = messageObj.optLong("time_posted", System.currentTimeMillis());
                int score = messageObj.optInt("score", 0);
                String author = messageObj.optString("author", "");
                String id = messageObj.optJSONObject("_id").optString("$oid", "");

                Message message = new Message(author, messageText, score, timePosted, id);
                messageList.add(message);
            }
        }

        return messageList;
    }

    public static boolean postMessage(Message message, Location location) {
        return true;
    }
}

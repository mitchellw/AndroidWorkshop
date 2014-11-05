package me.wilsonmitchell.yakyik;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wilsonmitchell on 11/5/14.
 */
public class MessageRepo {

    public List<Message> getNearbyMessages(Location location) {
        List<Message> testList = new ArrayList<Message>();
        // Add messages with authors
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            long randomTimePosted = random.nextInt(1000*60*60);
            Message message = new Message("author " + i, "message text from message " + i, random.nextInt(100), randomTimePosted);
            testList.add(message);
        }
        // Add messages with no author
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            long randomTimePosted = random.nextInt(1000*60*60);
            Message message = new Message(null, "message text from authorless message " + i, random.nextInt(100), randomTimePosted);
            testList.add(message);
        }
        return testList;
    }

    public boolean postMessage(Message message, Location location) {
        return true;
    }
}

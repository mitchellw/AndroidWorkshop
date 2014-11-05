package me.wilsonmitchell.yakyik;

/**
 * Created by wilsonmitchell on 11/5/14.
 */
public class Message {
    private String author;
    private String message;
    private int score;
    private long timePostedMills;

    public Message(String author, String message, int score, long timePostedMills) {
        this.author = author;
        this.message = message;
        this.score = score;
        this.timePostedMills = timePostedMills;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public int getScore() {
        return score;
    }

    public long getTimePostedMills() {
        return timePostedMills;
    }
}

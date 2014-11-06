package me.wilsonmitchell.yakyik;

/**
 * Created by wilsonmitchell on 11/5/14.
 */
public class Message {
    private String author;
    private String message;
    private int score;
    private long timePostedMills;
    private String id;

    public Message(String author, String message, int score, long timePostedMills, String id) {
        this.author = author;
        this.message = message;
        this.score = score;
        this.timePostedMills = timePostedMills;
        this.id = id;
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

    public void incrementScore() {
        score++;
    }

    public void decrementScore() {
        score--;
    }

    public String getId() {
        return id;
    }
}

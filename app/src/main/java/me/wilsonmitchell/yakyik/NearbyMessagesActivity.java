package me.wilsonmitchell.yakyik;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class NearbyMessagesActivity extends ActionBarActivity {

    private ListView messageListView;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_messages);

        messageListView = (ListView) findViewById(R.id.messageListView);
        messageAdapter = new MessageAdapter(this, MessageRepo.getNearbyMessages(null));
        messageListView.setAdapter(messageAdapter);
    }
}

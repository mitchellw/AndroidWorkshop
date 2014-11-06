package me.wilsonmitchell.yakyik;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;


public class NearbyMessagesActivity extends ActionBarActivity {

    private ListView messageListView;
    private MessageAdapter messageAdapter;
    private Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_messages);

        messageListView = (ListView) findViewById(R.id.messageListView);
    }

    private class DownloadMessagesTask extends AsyncTask<Void, Void, List<Message>> {
        @Override
        protected List<Message> doInBackground(Void... voids) {
            return MessageRepo.getNearbyMessages(lastKnownLocation);
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);
            messageAdapter = new MessageAdapter(NearbyMessagesActivity.this, messages);
            messageListView.setAdapter(messageAdapter);
        }
    }
}

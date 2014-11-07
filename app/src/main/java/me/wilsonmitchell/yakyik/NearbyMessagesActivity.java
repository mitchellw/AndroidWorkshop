package me.wilsonmitchell.yakyik;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;


public class NearbyMessagesActivity extends ActionBarActivity {

    private ListView messageListView;
    private MessageAdapter messageAdapter;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_messages);

        messageListView = (ListView) findViewById(R.id.messageListView);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                lastKnownLocation = location;

                new DownloadMessagesTask().execute();
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nearby_messages, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_post_message:
                Intent postMessageIntent = new Intent(this, PostMessageActivity.class);
                this.startActivity(postMessageIntent);
                return true;
            case R.id.action_refresh:
                if (lastKnownLocation != null) {
                    new DownloadMessagesTask().execute();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

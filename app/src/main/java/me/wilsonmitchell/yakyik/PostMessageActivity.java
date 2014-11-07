package me.wilsonmitchell.yakyik;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class PostMessageActivity extends ActionBarActivity {

    private EditText messageEditText;
    private EditText authorEditText;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);

        messageEditText = (EditText) findViewById(R.id.messageEditText);
        authorEditText = (EditText) findViewById(R.id.authorEditText);

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

    public void onSubmitClicked(View v) {
        String messageText = messageEditText.getText().toString();
        String author = authorEditText.getText().toString();

        if (lastKnownLocation != null) {
            if (messageText.length() > 0) {
                new PostMessageTask().execute(messageText, author);
            }
            else {
                Toast.makeText(PostMessageActivity.this, R.string.message_required_message, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(PostMessageActivity.this, R.string.location_not_found_message, Toast.LENGTH_SHORT).show();
        }
    }

    private class PostMessageTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            if (strings.length != 2) {
                return false;
            }
            String messageText = strings[0];
            String author = strings[1];

            return MessageRepo.postMessage(messageText, author, lastKnownLocation);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                finish();
            }
            else {
                Toast.makeText(PostMessageActivity.this, R.string.post_fail_message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

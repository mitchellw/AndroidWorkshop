package me.wilsonmitchell.yakyik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class ServerUtils {
    private static final String TAG = "ServerUtils";

    public static JSONObject fromInputStream(InputStream is) {
        String jsString = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while((line = reader.readLine()) != null){
                jsString += line;
            }
            is.close();
            reader.close();
        }
        catch (IOException e) {
            Log.d(TAG, "ioe");
            return null;
        }
        try {
            return new JSONObject(jsString);
        }
        catch (JSONException e) {
            Log.d(TAG, "jse");
            return null;
        }
    }
}


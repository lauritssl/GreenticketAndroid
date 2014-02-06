package dk.greenticket.GTmodels;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lalan on 29/01/14.
 */
public interface GTConnectListner {
    public void connectionCompleted(JSONObject json) throws JSONException;
}

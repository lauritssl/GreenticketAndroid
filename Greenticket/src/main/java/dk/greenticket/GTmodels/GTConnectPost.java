package dk.greenticket.GTmodels;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalan on 29/01/14.
 */
public class GTConnectPost extends AsyncTask<String, Integer, JSONObject>{
    private static final String SECRET = "MqHtRec35Zr6lAAOFZtb";
    private static final String url = "http://api.greenticket.dk/";
    private String path;
    private JSONObject para;

    public GTConnectPost(String path, JSONObject para){
        this.path = path;
        this.para = para;
        
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String result = "NULL";
        String urlPath = url + path;
        String hash = path + SECRET;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost request = new HttpPost(urlPath);
        request.addHeader("Authorization", hash);


        //request.setEntity(new UrlEncodedFormEntity(para,"UTF-8"));
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            result = httpclient.execute(request, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpclient.getConnectionManager().shutdown();

        JSONObject json = null;

        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


}

package dk.greenticket.GTmodels;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lalan on 29/01/14.
 */
public class GTConnect{
    private static final String SECRET = "MqHtRec35Zr6lAAOFZtb";
    private static final String url = "https://greenticket.dk/api/";
    private String path;

    public GTConnect(String path){
        this.path = path;

    }

    public JSONObject GET() {
        String result = "";
        InputStream is = null;
        String urlPath = url + path;
        String hash = path + SECRET;
        JSONObject json = null;
        HttpClient httpClient = new DefaultHttpClient();

        Log.e("url", urlPath.toString());

        try {
            HttpGet request = new HttpGet(urlPath);
            request.addHeader("Authorization", hash);
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (Exception e) {
            Log.e("connection", e.toString());
        }


        Log.e("******IS", is.toString());
        httpClient.getConnectionManager().shutdown();

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        Log.e("RESTULT", result);

        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        Log.e("json", json.toString());

        return json;
    }

    public JSONObject POST(List<NameValuePair> para) {
        String result = "";
        InputStream is = null;
        String urlPath = "https://greenticket.dk/api/login";
        String hash = path + SECRET;
        JSONObject json = null;
        HttpClient httpClient = new DefaultHttpClient();
        Log.e("URL", urlPath);

        Log.e("PARA", para.toString());
        try {
            HttpPost httpPost = new HttpPost(urlPath);
            httpPost.addHeader("Authorization", hash);
            httpPost.setEntity(new UrlEncodedFormEntity(para, HTTP.UTF_8));
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (Exception e) {
            Log.e("connection", e.toString());
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                Log.e("line", line);
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            json = new JSONObject(result);
        } catch (Exception e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        Log.e("json", json.toString());

        return json;
    }
}

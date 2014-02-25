package dk.greenticket.greenticket;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.webkit.WebView;

public class WebActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        String url = getIntent().getExtras().getString("link");
        url = "https://www.greenticket.dk/" + url + "?app_web_view=1";
        myWebView.loadUrl(url);
    }



}

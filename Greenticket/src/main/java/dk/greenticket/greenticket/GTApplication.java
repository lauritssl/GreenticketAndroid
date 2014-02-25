package dk.greenticket.greenticket;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import dk.greenticket.GTmodels.GTUser;

/**
 * Created by lalan on 05/02/14.
 */
public class GTApplication extends Application {
    private GTUser user;

    public GTUser getUser() {
        return user;
    }

    public void setUser(GTUser user) {
        this.user = user;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}

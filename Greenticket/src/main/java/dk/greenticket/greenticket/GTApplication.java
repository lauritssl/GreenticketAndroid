package dk.greenticket.greenticket;

import android.app.Application;

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
}

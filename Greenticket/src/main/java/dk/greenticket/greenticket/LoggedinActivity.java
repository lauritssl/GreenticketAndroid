package dk.greenticket.greenticket;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class LoggedinActivity extends FragmentActivity {
    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("Tickets").setIndicator("Tickets"),
                OrdersFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Events").setIndicator("Events"),
                EventsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Settings").setIndicator("Settings"),
                SettingsFragment.class, null);
    }
}
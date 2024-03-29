package dk.greenticket.greenticket;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

public class LoggedinActivity extends FragmentActivity {
    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.tab_orders_title)).setIndicator(getResources().getString(R.string.tab_orders_title)),
                OrdersFragment.class, null);
        //mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.tab_events_title)).setIndicator(getResources().getString(R.string.tab_events_title)),
        //        EventsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.tab_settings_title)).setIndicator(getResources().getString(R.string.tab_settings_title)),
                SettingsFragment.class, null);

        TabWidget tabWidget = mTabHost.getTabWidget();
        for(int i = 0; i < tabWidget.getChildCount(); i++){
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_selector);
        }

    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

}
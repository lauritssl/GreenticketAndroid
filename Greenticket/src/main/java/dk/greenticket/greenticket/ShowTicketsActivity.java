package dk.greenticket.greenticket;

import android.app.Activity;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.ArrayList;
import java.util.List;

import dk.greenticket.GTmodels.GTOrder;
import dk.greenticket.GTmodels.GTTicket;
import dk.greenticket.GTmodels.GTUser;

public class ShowTicketsActivity extends FragmentActivity {
    GTTicketPageAdapter pageAdapter;
    float screenBrightness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tickets);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        screenBrightness = layoutParams.screenBrightness;
        layoutParams.screenBrightness = 1.0F;
        getWindow().setAttributes(layoutParams);

        Integer orderID = getIntent().getExtras().getInt("order");

        GTApplication application = (GTApplication) this.getApplication();
        GTUser user = application.getUser();

        GTOrder order = user.getOrder(orderID);

        FetchableImageView cover = (FetchableImageView) findViewById(R.id.ticketCover);
        cover.setImage(order.getEvent().getCoverLink(), R.drawable.defaultcover);

        List<Fragment> fragments = getFragments(order.getTickets());
        pageAdapter = new GTTicketPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager)findViewById(R.id.ticketInfoPager);

        pager.setAdapter(pageAdapter);
        //pager.setOffscreenPageLimit(0);

        if(order.getTickets().size()>1){
            CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
            indicator.setViewPager(pager);
            indicator.setCurrentItem(pager.getChildCount());
            indicator.setFillColor(Color.BLACK);
            indicator.setStrokeColor(Color.BLACK);
            indicator.setRadius(7.0f);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker tracker = EasyTracker.getInstance(this);
        tracker.set(Fields.SCREEN_NAME, "ShowTicket");
        tracker.send(MapBuilder.createAppView().build());
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = screenBrightness;
        getWindow().setAttributes(layoutParams);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        screenBrightness = layoutParams.screenBrightness;
        layoutParams.screenBrightness = 1.0F;
        getWindow().setAttributes(layoutParams);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private List<Fragment> getFragments(ArrayList<GTTicket> tickets){
        List<Fragment> ticketList = new ArrayList<Fragment>();

        for(int i = 0; i < tickets.size(); i++){
            Integer ticketCount = i + 1;
            ticketList.add(GTTicketFragment.newInstance(tickets.get(i),ticketCount));
        }


        return ticketList;

    }




}

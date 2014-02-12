package dk.greenticket.greenticket;

import android.app.Activity;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;
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
    TextView ordreIDField;
    GTTicketPageAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tickets);

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
        pager.setOffscreenPageLimit(0);

        if(order.getTickets().size()>1){
            CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
            indicator.setViewPager(pager);
            indicator.setCurrentItem(pager.getChildCount());
            indicator.setFillColor(Color.BLACK);
            indicator.setStrokeColor(Color.BLACK);
            indicator.setRadius(7.5f);
        }


    }

    private List<Fragment> getFragments(ArrayList<GTTicket> tickets){
        List<Fragment> ticketList = new ArrayList<Fragment>();

        for(int i = 0; i < tickets.size(); i++){
            ticketList.add(GTTicketFragment.newInstance(tickets.get(i)));
        }


        return ticketList;

    }



}

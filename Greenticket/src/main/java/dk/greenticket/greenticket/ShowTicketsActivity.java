package dk.greenticket.greenticket;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
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

import dk.greenticket.GTmodels.GTOrder;
import dk.greenticket.GTmodels.GTUser;

public class ShowTicketsActivity extends Activity {
    TextView ordreIDField;
    GTQRMaker qrMaker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tickets);

        Integer orderID = getIntent().getExtras().getInt("order");


        ordreIDField = (TextView) findViewById(R.id.orderIDField);

        GTApplication application = (GTApplication) this.getApplication();
        GTUser user = application.getUser();

        GTOrder order = user.getOrder(orderID);
        Log.e("asdas", ""+ order.getOrderID().toString());

        //ordreIDField.setText(order.getOrderID().toString());

        FetchableImageView cover = (FetchableImageView) findViewById(R.id.ticketCover);
        ImageView qrCodeView = (ImageView) findViewById(R.id.ticketQR);

        cover.setImage(order.getEvent().getCoverLink(), R.drawable.defaultcover);

        Log.e("QR********", "" );

        qrMaker = new GTQRMaker();
        Bitmap qr =  qrMaker.convertToBitmap(order.getTickets().get(0).getQRID(), 650, 650);


        qrCodeView.setImageBitmap(qr);

    }



}

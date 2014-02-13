package dk.greenticket.greenticket;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dk.greenticket.GTmodels.GTOrder;
import dk.greenticket.GTmodels.GTTicket;

/**
 * Created by lalan on 12/02/14.
 */
public class GTTicketFragment extends Fragment {

    public static final GTTicketFragment newInstance(GTTicket ticket, Integer ticketCount){
        GTTicketFragment f = new GTTicketFragment();
        Bundle bdl = new Bundle(4);
        bdl.putString("qr", ticket.getQRID());
        bdl.putString("type", ticket.getType());
        bdl.putInt("orderid", ticket.getOrderID());
        bdl.putInt("ticketCount", ticketCount);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String qr = getArguments().getString("qr");
        Integer ticketCount = getArguments().getInt("ticketCount");
        String type = getArguments().getString("type");
        Integer orderID = getArguments().getInt("orderid");
        View v = inflater.inflate(R.layout.fragment_gtticket, container, false);

        ImageView qrCodeView = (ImageView) v.findViewById(R.id.ticketQR);
        int size = container.findViewById(R.id.ticketInfoPager).getMeasuredWidth();
        GTQRMaker qrMaker = new GTQRMaker();
        Bitmap qrPic =  qrMaker.convertToBitmap(qr, size, size);


        qrCodeView.setImageBitmap(qrPic);

        TextView textInfoView = (TextView)v.findViewById(R.id.textView1);
        textInfoView.setText("#"+orderID.toString()+" - "+type+"#"+ticketCount);
        return v;
    }


}

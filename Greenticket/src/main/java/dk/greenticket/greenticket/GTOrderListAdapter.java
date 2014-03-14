package dk.greenticket.greenticket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import dk.greenticket.GTmodels.GTOrder;

/**
 * Created by lalan on 10/02/14.
 */
public class GTOrderListAdapter extends ArrayAdapter<GTOrder> {
    Context context;
    int layoutResourceId;
    List<GTOrder> orders = null;

    public GTOrderListAdapter(Context context, int layoutResourceId, List<GTOrder> orders) {
        super(context, layoutResourceId, orders);
        this.orders = orders;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        OrderHolder holder = null;


        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new OrderHolder();
            holder.orderID = (TextView) row.findViewById(R.id.orderID);
            holder.orderEventDate = (TextView) row.findViewById(R.id.orderEventDate);
            holder.orderEventOrg = (TextView) row.findViewById(R.id.orderEventOrg);
            holder.orderEventTitle = (TextView) row.findViewById(R.id.orderEventTitle);
            holder.orderCover = (FetchableImageView) row.findViewById(R.id.orderCover);
            holder.order = (RelativeLayout) row.findViewById(R.id.order);
        }else{
            holder = (OrderHolder) row.getTag();
        }

        GTOrder order = orders.get(position);
        holder.orderID.setText("#"+order.getOrderID().toString());
        holder.orderEventTitle.setText(order.getEvent().getTitle());
        Date eventDate = null;
        try {
            eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getEvent().getDate());
        } catch (ParseException e) {
            Log.e("GTOrderListAdapter *eventDate parser*", e.toString());
        }
        holder.orderEventDate.setText(new SimpleDateFormat("dd. MMM yyyy HH:mm").format(eventDate));
        holder.orderEventOrg.setText(order.getEvent().getOrganizer());

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        float width = size.x;
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int margin = (int) (10 * scale + 0.5f);
        width = (width/2) - margin;

        RotateAnimation anim = new RotateAnimation(0f, 90f, width, width);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(0);

        holder.orderCover.startAnimation(anim);



        holder.orderCover.setImage(order.getEvent().getCoverLink(), R.drawable.defaultcover);
        return row;
    }

    static class OrderHolder{
        TextView orderID;
        TextView orderEventTitle , orderEventDate, orderEventOrg;
        FetchableImageView orderCover;
        RelativeLayout order;
    }


}

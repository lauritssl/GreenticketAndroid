package dk.greenticket.greenticket;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        View row = convertView;
        OrderHolder holder = null;


        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new OrderHolder();
            holder.orderID = (TextView) row.findViewById(R.id.orderID);
        }else{
            holder = (OrderHolder) row.getTag();
        }

        GTOrder order = orders.get(position);
        holder.orderID.setText(order.getOrderID().toString());

        return row;
    }

    static class OrderHolder{
        TextView orderID;
    }
}

package dk.greenticket.greenticket;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import dk.greenticket.GTmodels.GTOrder;
import dk.greenticket.GTmodels.GTUser;

public class OrdersFragment extends ListFragment  {
    GTUser user;
    ArrayList<GTOrder> orders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_orders, container, false);

        GTApplication application = (GTApplication) getActivity().getApplication();
        final GTUser user = application.getUser();
        new Thread(new Runnable(){
            public void run(){
                user.loadOrders();
                UserOrderLoad(user);
            }
        }).start();

        return V;
    }

    /*@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        GTOrder item = (GTOrder) getListAdapter().getItem(position);
        Toast.makeText(getActivity().getApplicationContext(), item.getOrderID().toString(), Toast.LENGTH_LONG).show();
    }*/

    public void UserOrderLoad(final GTUser user){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                orders = user.getOrders();
                Log.e("GT:TicketFragment", "size " + orders.size());
                Log.e("GT:TicketFragment", "Gettingorder");
                GTOrderListAdapter adapter = new GTOrderListAdapter(getActivity(), R.layout.list_order_row, orders);
                setListAdapter(adapter);
            }
        });  
    }
}

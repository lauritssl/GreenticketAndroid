package dk.greenticket.greenticket;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import java.util.ArrayList;
import java.util.Collections;

import dk.greenticket.GTmodels.GTOrder;
import dk.greenticket.GTmodels.GTUser;

public class OrdersFragment extends ListFragment  {
    ArrayList<GTOrder> orders;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View V = inflater.inflate(R.layout.fragment_orders, container, false);

        GTApplication application = (GTApplication) getActivity().getApplication();
        final GTUser user = application.getUser();
        Log.e("user email", user.getEmail());
        new Thread(new Runnable(){
            public void run(){
                user.loadOrders();
                UserOrderLoad(user);
            }
        }).start();


        PullToRefreshListView listView = (PullToRefreshListView) V.findViewById(android.R.id.list);;
        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GTApplication application = (GTApplication) getActivity().getApplication();
                new RefreshList(user).execute();
                if(!application.isNetworkAvailable()){
                    Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
                }
            }
        });

        return V;
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker tracker = EasyTracker.getInstance(getActivity());
        tracker.set(Fields.SCREEN_NAME, "OrdersList");
        tracker.send(MapBuilder.createAppView().build());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        position --;
        GTOrder gtOrder = (GTOrder) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), ShowTicketsActivity.class);
        intent.putExtra("order", gtOrder.getOrderID());
        startActivity(intent);

    }

    public void UserOrderLoad(final GTUser user){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                orders = user.getOrders();
                Collections.reverse(orders);
                GTOrderListAdapter adapter = new GTOrderListAdapter(getActivity(), R.layout.list_order_row, orders);
                setListAdapter(adapter);
            }
        });  
    }


    private class RefreshList extends AsyncTask<Void, Void, String[]> {
        GTUser user;

        public RefreshList(GTUser user){
            this.user = user;
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            user.loadOrders();
            UserOrderLoad(user);

            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            ((PullToRefreshListView) getListView()).onRefreshComplete();

            super.onPostExecute(result);
        }
    }
}

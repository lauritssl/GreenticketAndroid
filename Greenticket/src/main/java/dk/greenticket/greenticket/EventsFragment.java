package dk.greenticket.greenticket;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import net.sourceforge.zbar.Symbol;

import java.util.ArrayList;
import java.util.Collections;

import dk.greenticket.GTmodels.GTEvent;
import dk.greenticket.GTmodels.GTUser;
import dk.greenticket.ZbarScanner.ZBarConstants;
import dk.greenticket.ZbarScanner.ZBarScannerActivity;

public class EventsFragment extends ListFragment {
    ArrayList<GTEvent> events;
    private static final int ZBAR_QR_SCANNER_REQUEST = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_events, container, false);
        GTApplication application = (GTApplication) getActivity().getApplication();
        final GTUser user = application.getUser();
        new Thread(new Runnable(){
            public void run(){

                user.loadEvents();
                UserEventsLoad(user);
            }
        }).start();

        PullToRefreshListView listView = (PullToRefreshListView) V.findViewById(android.R.id.list);;
        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GTApplication application = (GTApplication) getActivity().getApplication();
                new RefreshList(user).execute();
                if(!application.isNetworkAvailable()){
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
                }
            }
        });

        return V;
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker tracker = EasyTracker.getInstance(getActivity());
        tracker.set(Fields.SCREEN_NAME, "EventsList");
        tracker.send(MapBuilder.createAppView().build());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        /*position --;
        GTEvent gtEvent = (GTEvent) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), ??.class);
        intent.putExtra("event", gtEvent.getId());
        startActivity(intent);*/

        launchQRScanner(v);



    }

    public void UserEventsLoad(final GTUser user){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                events = user.getEvents();
                Collections.reverse(events);
                GTEventListAdapter adapter = new GTEventListAdapter(getActivity(), R.layout.list_event_row, events);
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
            user.loadEvents();
            UserEventsLoad(user);

            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            ((PullToRefreshListView) getListView()).onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    public void launchQRScanner(View v) {
        if (isCameraAvailable()) {
            Intent intent = new Intent(getActivity(), ZBarScannerActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
            startActivityForResult(intent, ZBAR_QR_SCANNER_REQUEST);
        } else {
            Toast.makeText(getActivity().getApplicationContext(),  getResources().getString(R.string.no_camera), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getActivity().getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


}

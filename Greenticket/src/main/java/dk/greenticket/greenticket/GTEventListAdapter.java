package dk.greenticket.greenticket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dk.greenticket.GTmodels.GTEvent;
import dk.greenticket.GTmodels.GTOrder;

/**
 * Created by lalan on 10/02/14.
 */
public class GTEventListAdapter extends ArrayAdapter<GTEvent> {
    Context context;
    int layoutResourceId;
    List<GTEvent> events = null;

    public GTEventListAdapter(Context context, int layoutResourceId, List<GTEvent> events) {
        super(context, layoutResourceId, events);
        this.events = events;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        EventHolder holder = null;


        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EventHolder();
            holder.eventDate = (TextView) row.findViewById(R.id.eventDate);
            holder.eventTitle = (TextView) row.findViewById(R.id.eventTitle);
            holder.eventCover = (FetchableImageView) row.findViewById(R.id.eventCover);
        }
        GTEvent event = events.get(position);
        Date eventDate = null;
        try {
            eventDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event.getDate());
        } catch (ParseException e) {
            Log.e("GTEventListAdapter *eventDate parser*", e.toString());
        }
        holder.eventDate.setText(new SimpleDateFormat("dd. MMM yyyy HH:mm").format(eventDate));

        holder.eventTitle.setText(event.getTitle());

        holder.eventCover.setImage(event.getCoverLink(), R.drawable.defaultcover);

        return row;
    }

    static class EventHolder{
        TextView eventDate , eventTitle;
        FetchableImageView eventCover;
    }


}

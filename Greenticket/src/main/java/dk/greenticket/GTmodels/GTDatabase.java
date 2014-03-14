package dk.greenticket.GTmodels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lalan on 19/02/14.
 */

public class GTDatabase{
    GTDatabaseHelper helper;
    public  GTDatabase(Context context){
        helper = new GTDatabaseHelper(context);
    }


    public long addTicket(GTTicket ticket) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GTDatabaseHelper.TICKETS_QR, ticket.getQRID());
        values.put(GTDatabaseHelper.TICKETS_CHECKED, ticket.getChecked() ? 1 : 0 );
        values.put(GTDatabaseHelper.TICKETS_TYPE, ticket.getType());
        values.put(GTDatabaseHelper.TICKETS_ORDERID, ticket.getOrderID());
        values.put(GTDatabaseHelper.TICKETS_EMAIL, ticket.getEmail());

        String[] args = {ticket.getQRID()};
        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_TICKETS, null,GTDatabaseHelper.TICKETS_QR+" = ?", args,null,null,null);

        if(c.getCount() <= 0){
            return db.insert(GTDatabaseHelper.TABLE_NAME_TICKETS, null, values);
        }else{
            return db.update(GTDatabaseHelper.TABLE_NAME_TICKETS, values,GTDatabaseHelper.TICKETS_QR+" = ?", args);
        }
    }

    public long addEvent(GTEvent event) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GTDatabaseHelper.EVENTS_ID, event.getId());
        values.put(GTDatabaseHelper.EVENTS_TITLE, event.getTitle());
        values.put(GTDatabaseHelper.EVENTS_ACTIVE, event.getActive() ? 1 : 0);
        values.put(GTDatabaseHelper.EVENTS_COVERLINK, event.getCoverLink());
        values.put(GTDatabaseHelper.EVENTS_DATE, event.getDate().toString());
        values.put(GTDatabaseHelper.EVENTS_ENDDATE, event.getEndDate().toString());
        values.put(GTDatabaseHelper.EVENTS_ORGANIZER, event.getOrganizer());

        String[] args = {Integer.toString(event.getId())};
        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_EVENTS, null,GTDatabaseHelper.EVENTS_ID+" = ?", args,null,null,null);

        if(c.getCount() <= 0){
            return db.insert(GTDatabaseHelper.TABLE_NAME_EVENTS, null, values);
        }else{
            return db.update(GTDatabaseHelper.TABLE_NAME_EVENTS, values,GTDatabaseHelper.EVENTS_ID+" = ?", args);
        }
    }

    public long addOrder(GTOrder order) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GTDatabaseHelper.ORDERS_ORDERID, order.getOrderID());
        values.put(GTDatabaseHelper.ORDERS_EMAIL, order.getEmail());
        values.put(GTDatabaseHelper.ORDERS_PAID, order.getPayed() ? 1 : 0);
        values.put(GTDatabaseHelper.ORDERS_BUYTIME, order.getBuyTime().toString());
        values.put(GTDatabaseHelper.ORDERS_EVENTID, order.getEvent().getId());

        String[] args = {Integer.toString(order.getOrderID())};
        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_ORDERS, null,GTDatabaseHelper.ORDERS_ORDERID+" = ?", args,null,null,null);

        if(c.getCount() <= 0){
            return db.insert(GTDatabaseHelper.TABLE_NAME_ORDERS, null, values);
        }else{
            return db.update(GTDatabaseHelper.TABLE_NAME_ORDERS, values, GTDatabaseHelper.ORDERS_ORDERID + " = ?", args);
        }
    }

    public long addAdminEvent(GTEvent event) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GTDatabaseHelper.ADMINEVENTS_ID, event.getId());
        values.put(GTDatabaseHelper.ADMINEVENTS_TITLE, event.getTitle());
        values.put(GTDatabaseHelper.ADMINEVENTS_ACTIVE, event.getActive() ? 1 : 0);
        values.put(GTDatabaseHelper.ADMINEVENTS_COVERLINK, event.getCoverLink());
        values.put(GTDatabaseHelper.ADMINEVENTS_DATE, event.getDate().toString());
        values.put(GTDatabaseHelper.ADMINEVENTS_ENDDATE, event.getEndDate().toString());
        values.put(GTDatabaseHelper.ADMINEVENTS_ORGANIZER, event.getOrganizer());

        String[] args = {Integer.toString(event.getId())};
        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_ADMINEVENTS, null,GTDatabaseHelper.ADMINEVENTS_ID+" = ?", args,null,null,null);

        if(c.getCount() <= 0){
            return db.insert(GTDatabaseHelper.TABLE_NAME_ADMINEVENTS, null, values);
        }else{
            return db.update(GTDatabaseHelper.TABLE_NAME_ADMINEVENTS, values,GTDatabaseHelper.ADMINEVENTS_ID+" = ?", args);
        }
    }

    public GTTicket getTicket(String qrID){
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] args = {qrID};
        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_TICKETS, null,GTDatabaseHelper.TICKETS_QR+" = ?", args,null,null,null);

        GTTicket ticket = null;
        while(c.moveToNext()){
            ticket = new GTTicket(c.getString(c.getColumnIndex(GTDatabaseHelper.TICKETS_QR)), c.getString(c.getColumnIndex(GTDatabaseHelper.TICKETS_TYPE)), c.getInt(c.getColumnIndex(GTDatabaseHelper.TICKETS_CHECKED)) > 0 ? true :false, c.getInt(c.getColumnIndex(GTDatabaseHelper.TICKETS_ORDERID)), c.getString(c.getColumnIndex(GTDatabaseHelper.TICKETS_EMAIL)));
        }

        return ticket;
    }

    public GTEvent getEvent(int eventID){
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] args = {Integer.toString(eventID)};
        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_EVENTS, null,GTDatabaseHelper.EVENTS_ID + " = ?", args, null, null, null);

        GTEvent event = null;
        while(c.moveToNext()){
            event = new GTEvent(c.getString(c.getColumnIndex(GTDatabaseHelper.EVENTS_TITLE)), c.getString(c.getColumnIndex(GTDatabaseHelper.EVENTS_COVERLINK)), c.getString(c.getColumnIndex(GTDatabaseHelper.EVENTS_DATE)), c.getString(c.getColumnIndex(GTDatabaseHelper.EVENTS_ENDDATE)), c.getInt(c.getColumnIndex(GTDatabaseHelper.EVENTS_ID)), c.getInt(c.getColumnIndex(GTDatabaseHelper.EVENTS_ACTIVE)) > 0 ? true :false, c.getString(c.getColumnIndex(GTDatabaseHelper.EVENTS_ORGANIZER)));
        }
        return event;
    }

    public GTOrder getOrder(int orderID){
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] args = {Integer.toString(orderID)};
        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_ORDERS, null,GTDatabaseHelper.ORDERS_ORDERID + " = ?", args, null, null, null);;

        GTOrder order = null;
        while(c.moveToNext()){
            order = new GTOrder(c.getString(c.getColumnIndex(GTDatabaseHelper.ORDERS_EMAIL)), c.getInt(c.getColumnIndex(GTDatabaseHelper.ORDERS_ORDERID)), c.getInt(c.getColumnIndex(GTDatabaseHelper.ORDERS_PAID)) > 0 ? true :false, getEvent(c.getInt(c.getColumnIndex(GTDatabaseHelper.ORDERS_EVENTID))), c.getString(c.getColumnIndex(GTDatabaseHelper.ORDERS_BUYTIME)));
            ArrayList<GTTicket> tickets = getTickets(order.getOrderID());

            for(int i = 0; i < tickets.size(); i++){
                GTTicket ticket = tickets.get(i);
                order.addTicket(ticket);
            }
        }
        return order;

    }

    public GTEvent getAdminEvent(int eventID){
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] args = {Integer.toString(eventID)};
        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_ADMINEVENTS, null,GTDatabaseHelper.ADMINEVENTS_ID + " = ?", args, null, null, null);

        GTEvent event = null;
        while(c.moveToNext()){
            event = new GTEvent(c.getString(c.getColumnIndex(GTDatabaseHelper.ADMINEVENTS_TITLE)), c.getString(c.getColumnIndex(GTDatabaseHelper.ADMINEVENTS_COVERLINK)), c.getString(c.getColumnIndex(GTDatabaseHelper.ADMINEVENTS_DATE)), c.getString(c.getColumnIndex(GTDatabaseHelper.ADMINEVENTS_ENDDATE)), c.getInt(c.getColumnIndex(GTDatabaseHelper.ADMINEVENTS_ID)), c.getInt(c.getColumnIndex(GTDatabaseHelper.ADMINEVENTS_ACTIVE)) > 0 ? true :false, c.getString(c.getColumnIndex(GTDatabaseHelper.ADMINEVENTS_ORGANIZER)));
        }
        return event;
    }

    public ArrayList<GTTicket> getTickets(int orderid){
        ArrayList<GTTicket> tickets = new ArrayList<GTTicket>();
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] args = {Integer.toString(orderid)};
        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_TICKETS, null,GTDatabaseHelper.TICKETS_ORDERID + " = ?", args, null, null, null);


        while(c.moveToNext()){
                GTTicket ticket = getTicket(c.getString(c.getColumnIndex(GTDatabaseHelper.TICKETS_QR)));

                tickets.add(ticket);
        }

        return tickets;
    }

    public ArrayList<GTOrder> getOrders(){
        ArrayList<GTOrder> orders = new ArrayList<GTOrder>();
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_ORDERS, null, null, null, null, null,null);

        while(c.moveToNext()){
            GTOrder order = getOrder(c.getInt(c.getColumnIndex(GTDatabaseHelper.ORDERS_ORDERID)));
            orders.add(order);
        }

        return orders;
    }

    public ArrayList<GTEvent> getAdminEvents(){
        ArrayList<GTEvent> adminEvents = new ArrayList<GTEvent>();
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.query(GTDatabaseHelper.TABLE_NAME_ADMINEVENTS, null, null, null, null, null,null);

        while(c.moveToNext()){
            GTEvent event = getAdminEvent(c.getInt(c.getColumnIndex(GTDatabaseHelper.ADMINEVENTS_ID)));
            adminEvents.add(event);
        }

        return adminEvents;
    }

    public void clear(){
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(GTDatabaseHelper.TABLE_NAME_TICKETS, null, null);
        db.delete(GTDatabaseHelper.TABLE_NAME_EVENTS, null, null);
        db.delete(GTDatabaseHelper.TABLE_NAME_ORDERS, null, null);
        db.delete(GTDatabaseHelper.TABLE_NAME_ADMINEVENTS, null, null);
        Log.i("SQL", "table clear");
    }

    static class GTDatabaseHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 5;
        private static final String DATABASE_NAME = "GT";

        private static final String TABLE_NAME_ORDERS = "Orders";
        private static final String TABLE_NAME_TICKETS = "Tickets";
        private static final String TABLE_NAME_EVENTS = "Events";
        private static final String TABLE_NAME_ADMINEVENTS = "AdminEvents";

        private static final String ORDERS_ORDERID = "id";
        private static final String ORDERS_EMAIL = "email";
        private static final String ORDERS_PAID = "paid";
        private static final String ORDERS_BUYTIME = "buyTime";
        private static final String ORDERS_EVENTID = "eventID";

        private static final String TICKETS_ID = "id";
        private static final String TICKETS_QR = "qr";
        private static final String TICKETS_CHECKED = "chekced";
        private static final String TICKETS_TYPE = "type";
        private static final String TICKETS_ORDERID = "orderID";
        private static final String TICKETS_EMAIL = "email";

        private static final String EVENTS_ID = "id";
        private static final String EVENTS_TITLE = "title";
        private static final String EVENTS_ACTIVE = "active";
        private static final String EVENTS_COVERLINK = "coverLink";
        private static final String EVENTS_DATE = "date";
        private static final String EVENTS_ENDDATE = "endDate";
        private static final String EVENTS_ORGANIZER = "organizer";

        private static final String ADMINEVENTS_ID = "id";
        private static final String ADMINEVENTS_TITLE = "title";
        private static final String ADMINEVENTS_ACTIVE = "active";
        private static final String ADMINEVENTS_COVERLINK = "coverLink";
        private static final String ADMINEVENTS_DATE = "date";
        private static final String ADMINEVENTS_ENDDATE = "endDate";
        private static final String ADMINEVENTS_ORGANIZER = "organizer";


        private static final String CREATE_TABLE_ORDERS = "CREATE TABLE "
                + TABLE_NAME_ORDERS + " (" + ORDERS_ORDERID + " INTEGER PRIMARY KEY, " + ORDERS_EMAIL
                + " VARCHAR(200), " + ORDERS_PAID + " INTEGER, " + ORDERS_BUYTIME
                + " VARCHAR(50), " + ORDERS_EVENTID +" INTEGER)";

        private static final String CREATE_TABLE_TICKETS = "CREATE TABLE "
                + TABLE_NAME_TICKETS + " ("+ TICKETS_ID + " INTEGER PRIMARY KEY, "+TICKETS_QR+" VARCHAR(200), "+ TICKETS_CHECKED+ " INTEGER, "
                + TICKETS_TYPE +" VARCHAR(300), " + TICKETS_ORDERID+ " INTEGER, "
                + TICKETS_EMAIL+" VARCHAR(200))";

        private static final String CREATE_TABLE_EVENTS = "CREATE TABLE "
                + TABLE_NAME_EVENTS + " ("+ EVENTS_ID+" INTEGER PRIMARY KEY, " + EVENTS_TITLE+" VARCHAR(500), "
                + EVENTS_ACTIVE + " INTEGER, " + EVENTS_COVERLINK + " VARCHAR(300), " + EVENTS_DATE + " VARCHAR(50), "
                + EVENTS_ENDDATE + " VARCHAR(50)," + EVENTS_ORGANIZER + " VARCHAR(300))";

        private static final String CREATE_TABLE_ADMINEVENTS = "CREATE TABLE "
                + TABLE_NAME_ADMINEVENTS + " ("+ ADMINEVENTS_ID+" INTEGER PRIMARY KEY, " + ADMINEVENTS_TITLE+" VARCHAR(500), "
                + ADMINEVENTS_ACTIVE + " INTEGER, " + ADMINEVENTS_COVERLINK + " VARCHAR(300), " + ADMINEVENTS_DATE + " VARCHAR(50), "
                + ADMINEVENTS_ENDDATE + " VARCHAR(50)," + ADMINEVENTS_ORGANIZER + " VARCHAR(300))";

        private Context context;

        public GTDatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            // creating required tables
            Log.i("SQL", "create begin");
            db.execSQL(CREATE_TABLE_EVENTS);
            db.execSQL(CREATE_TABLE_ORDERS);
            db.execSQL(CREATE_TABLE_TICKETS);
            db.execSQL(CREATE_TABLE_ADMINEVENTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ORDERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TICKETS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EVENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ADMINEVENTS);

            // create new tables
            onCreate(db);
        }

        public void close() {
            SQLiteDatabase db = this.getReadableDatabase();
            if (db != null && db.isOpen())
                db.close();
        }
    }


}


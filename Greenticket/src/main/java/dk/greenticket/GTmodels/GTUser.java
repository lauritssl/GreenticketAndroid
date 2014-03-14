package dk.greenticket.GTmodels;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.greenticket.greenticket.GTApplication;

/**
 * Created by lalan on 28/01/14.
 */
public class GTUser{
    private String firstname, lastname, email, fbID = "", imagePath;
    private String password;
    private boolean loggedIn;
    private ArrayList<GTEvent> events = new ArrayList<GTEvent>();
    private ArrayList<GTOrder> orders = new ArrayList<GTOrder>();
    private Context context;


   public GTUser(int facebookID, String email, Context context){
       this.email = email;
       this.fbID = new Integer(facebookID).toString();
       this.loggedIn = false;
       this.context = context;


   }

    public GTUser(String email, String firstname, String lastname,Context context){
        this.email = email;
        this.loggedIn = true;
        this.firstname = firstname;
        this.lastname = lastname;
        this.context = context;


    }

   public GTUser(String email, String password, Context context ){
       this.email = email;
       this.password = password;
       this.loggedIn = false;
       this.context = context;

   }

   public boolean logUserIn(){
       if (fbID.isEmpty()){
           GTConnect con = new GTConnect("login");
           List<NameValuePair> para = new ArrayList<NameValuePair>();
           para.add(new BasicNameValuePair("email",email));
           para.add(new BasicNameValuePair("password",password));
           JSONObject result = con.POST(para);
           try{
               if (result.getBoolean("success")){
                   this.loggedIn = true;
                   loadInfo(result);
               }else{
                   this.loggedIn = false;
               }
           } catch (JSONException e){
               e.printStackTrace();
           }

       }else{
           GTConnect con = new GTConnect("login/facebook");
           List<NameValuePair> para = new ArrayList<NameValuePair>();
           para.add(new BasicNameValuePair("fbID",fbID));
           para.add(new BasicNameValuePair("email",email));
           JSONObject result = con.POST(para);
           try{
               if (result.getBoolean("success")){
                   this.loggedIn = true;
                   loadInfo(result);
               }else{
                   this.loggedIn = false;
               }
           } catch (JSONException e){
               e.printStackTrace();
           }
       }
       return isLoggedIn();
   }

   private boolean loadInfo(){
       GTConnect con = new GTConnect("users/"+email);
       JSONObject result = con.GET();
       try{
           if (result.getBoolean("success")){
               JSONObject user = result.getJSONObject("user");
               firstname = user.getString("firstname");
               lastname = user.getString("lastname");
               imagePath = user.getString("photo");
               return true;
           }
       } catch (JSONException e){
           e.printStackTrace();
       }
       return false;
   }

    private boolean loadInfo(JSONObject result){
        try{
            JSONObject user = result.getJSONObject("message");
            firstname = user.getString("firstname");
            lastname = user.getString("lastname");
            imagePath = user.getString("photo");
            return true;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean loadOrders(){
        GTApplication application = (GTApplication) context.getApplicationContext();

        GTDatabase db = new GTDatabase(context);
        if(application.isNetworkAvailable()){
            db.clear();
            GTConnect con = new GTConnect("users/"+email+"/orders");
            JSONObject result = con.GET();
            try {

                if (result.getBoolean("success")){
                    JSONArray resultOrders = result.getJSONArray("orders");
                    for(int i = 0; i < resultOrders.length(); i++ ) {

                        JSONObject order = resultOrders.getJSONObject(i);
                        String email = order.getString("email");
                        Integer orderID = order.getInt("id");
                        String payedString = order.getString("payed");
                        Boolean payed = payedString.equalsIgnoreCase("1");
                        String buyTime = order.getString("buyTime");

                        JSONObject event = order.getJSONObject("event");
                        GTEvent gtevent;
                        if (event.has("title")){
                            String title =  event.getString("title");

                            String coverLink = event.getString("cover");

                            if (!coverLink.equalsIgnoreCase("null")){
                                coverLink = "https://greenticket.dk" + coverLink;
                            }
                            String orgName = "";

                            if (!event.isNull("organisation")){
                                orgName = event.getJSONObject("organisation").getString("name");

                            }
                            String date = event.getString("eventStart");
                            String endDate = event.getString("eventEnd");
                            Integer id = event.getInt("id");
                            String activeString = event.getString("active");
                            Boolean active = activeString.equalsIgnoreCase("1");


                            gtevent = new GTEvent(title,coverLink,date,endDate,id,active, orgName);
                            db.addEvent(gtevent);
                            GTOrder gtOrder = new GTOrder(email, orderID, payed, gtevent, buyTime);
                            db.addOrder(gtOrder);
                            //orders.add(gtOrder);

                            JSONArray ordersTickets = order.getJSONArray("tickets");

                            if (ordersTickets.length() > 0){
                                for(int j = 0; j < ordersTickets.length(); j++ ) {

                                    JSONObject ticket = ordersTickets.getJSONObject(j);
                                    String QRID = ticket.getString("qr");
                                    String type = ticket.getJSONObject("type").getString("name");
                                    String checkedString = ticket.getString("checked");
                                    Boolean checked = checkedString.equalsIgnoreCase("1");
                                    GTTicket gtTicket = new GTTicket(QRID, type, checked, orderID, email);
                                    db.addTicket(gtTicket);
                                    //gtOrder.addTicket(gtTicket);
                                }
                            }
                        }else{
                            Log.e("GTUser ", "No Event on order");
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        orders.clear();
        orders = db.getOrders();
        return true;
    }

    private boolean loadEvents(){
        GTConnect con = new GTConnect("users/"+email+"/events");
        JSONObject result = con.GET();
        try {
            if (result.getBoolean("success")){

                JSONArray resultEvents = result.getJSONArray("events");
                for(int i = 0; i < resultEvents.length(); i++ ) {
                    JSONObject event = resultEvents.getJSONObject(i);
                    String title = event.getString("title");
                    String coverLink = event.getString("cover");
                    if (!coverLink.equalsIgnoreCase("null")){
                        coverLink = "https://greenticket.dk" + coverLink;
                    }
                    String orgName = "";

                    if (event.getJSONObject("organisation").has("name")){
                        orgName = event.getJSONObject("organisation").getString("name");
                    }

                    String date = event.getString("eventStart");
                    String endDate = event.getString("eventEnd");
                    Integer id = event.getInt("id");
                    String activeString = event.getString("active");
                    Boolean active = activeString.equalsIgnoreCase("1");

                    GTEvent gtEvent = new GTEvent(title,coverLink,date,endDate,id,active, orgName);

                    events.add(gtEvent);
                }
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getFbID() {
        return fbID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ArrayList<GTEvent> getEvents() {
        return events;
    }

    public ArrayList<GTOrder> getOrders() {
        return orders;
    }

    public GTOrder getOrder(int id){
        for(int i = 0; i < orders.size(); i++){
            GTOrder order = orders.get(i);
            if (order.getOrderID() == id){
                return  order;
            }
        }
        return null;
    }
}

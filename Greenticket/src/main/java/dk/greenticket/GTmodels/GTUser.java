package dk.greenticket.GTmodels;

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

/**
 * Created by lalan on 28/01/14.
 */
public class GTUser{
    private String firstname, lastname, email, fbID, imagePath;
    private String password;
    private boolean loggedIn;
    private ArrayList<GTEvent> events;
    private ArrayList<GTOrder> orders;


   public GTUser(int facebookID, String email){
       this(email, null);
       this.fbID = new Integer(facebookID).toString();

   }

   public GTUser(String email, String password ){
       this.email = email;
       this.password = password;
       this.loggedIn = false;
       this.events = new ArrayList<GTEvent>();
       this.orders = new ArrayList<GTOrder>();

   }

   public boolean logUserIn(){
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
        GTConnect con = new GTConnect("users/"+email+"/orders");
        JSONObject result = con.GET();

        try {
            if (result.getBoolean("success")){
                JSONArray resultOrders = result.getJSONArray("orders");
                orders.clear();
                for(int i = 0; i < resultOrders.length(); i++ ) {

                    JSONObject order = resultOrders.getJSONObject(i);
                    String email = order.getString("email");
                    Integer orderID = order.getInt("id");
                    String payedString = order.getString("payed");
                    Boolean payed = payedString.equalsIgnoreCase("1");
                    Date buyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getString("buyTime"));

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
                        Log.e("TITLE + ORG", title+" - "+orgName);

                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event.getString("eventStart"));
                        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event.getString("eventEnd"));
                        Integer id = event.getInt("id");
                        String activeString = event.getString("active");
                        Boolean active = activeString.equalsIgnoreCase("1");


                        gtevent = new GTEvent(title,coverLink,date,endDate,id,active, orgName);
                        GTOrder gtOrder = new GTOrder(email, orderID, payed, gtevent, buyTime);
                        orders.add(gtOrder);

                        JSONArray ordersTickets = order.getJSONArray("tickets");
                        if (ordersTickets.length() > 0){
                            for(int j = 0; j < ordersTickets.length(); j++ ) {

                                JSONObject ticket = ordersTickets.getJSONObject(j);
                                String QRID = ticket.getString("qr");
                                String type = ticket.getJSONObject("type").getString("name");
                                String checkedString = ticket.getString("checked");
                                Boolean checked = checkedString.equalsIgnoreCase("1");
                                GTTicket gtTicket = new GTTicket(QRID, type, checked, orderID, email);
                                gtOrder.addTicket(gtTicket);
                            }
                        }

                    }else{
                        Log.e("GTUser ", "No Event on order");
                    }

                }
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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

                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event.getString("eventStart"));
                    Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(event.getString("eventEnd"));
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
        } catch (ParseException e) {
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

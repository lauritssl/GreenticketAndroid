package dk.greenticket.GTmodels;

import android.os.AsyncTask;
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
    private String SECRET =  "MqHtRec35Zr6lAAOFZtb";
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
       Log.e("Json result", result.toString());
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
               JSONObject user = result.getJSONObject("message");
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

    private boolean loadOrders(){
        GTConnect con = new GTConnect("users/"+email+"/orders");
        JSONObject result = con.GET();
        try {
            if (result.getBoolean("success")){
                JSONArray resultOrders = result.getJSONArray("orders");
                for(int i = 0; i < resultOrders.length(); i++ ) {
                    JSONObject order = resultOrders.getJSONObject(i);
                    String email = order.getString("email");
                    Integer orderID = order.getInt("id");
                    Boolean payed = new Boolean(order.getString("payed"));
                    Date buyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.getString("buyTime"));
                    GTEvent event = new GTEvent();
                    GTOrder gtOrder = new GTOrder(email, orderID, payed, event, buyTime);
                    orders.add(gtOrder);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean loadEvents(){
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

}

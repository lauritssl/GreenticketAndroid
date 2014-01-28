package dk.greenticket.GTmodels;

import java.util.ArrayList;

/**
 * Created by lalan on 28/01/14.
 */
public class GTUser {
    public String firstname, lastname, email, fbID;
    private String password;
    private String SECRET =  "MqHtRec35Zr6lAAOFZtb";
    public boolean loggedIn;
    public ArrayList<GTEvent> events;
    public ArrayList<GTOrder> orders;


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

   private boolean logUserIn(){

       return false;
   }

   private boolean loadInfo(){
       return false;
   }

    private boolean loadOrders(){
        return false;
    }

    private boolean loadEvents(){
        return false;
    }

}

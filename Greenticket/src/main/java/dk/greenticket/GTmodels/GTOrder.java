package dk.greenticket.GTmodels;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lalan on 28/01/14.
 */
public class GTOrder {
    public String email;
    public Integer orderID;
    public Boolean payed;
    public GTEvent event;
    public Date buyTime;
    public ArrayList<GTTicket> tickets;

    public GTOrder(){
        tickets = new ArrayList<GTTicket>();
    }

    public void addTicket(GTTicket ticket){
        tickets.add(ticket);
    }
}

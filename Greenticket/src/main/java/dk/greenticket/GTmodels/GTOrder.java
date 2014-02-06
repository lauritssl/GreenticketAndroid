package dk.greenticket.GTmodels;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lalan on 28/01/14.
 */
public class GTOrder {
    private String email;
    private Integer orderID;
    private Boolean payed;
    private GTEvent event;
    private Date buyTime;
    private ArrayList<GTTicket> tickets;

    public GTOrder(String email, Integer orderID, Boolean payed, GTEvent event, Date buyTime){
        tickets = new ArrayList<GTTicket>();
        this.email = email;
        this.orderID = orderID;
        this.payed = payed;
        this.event = event;
        this.buyTime = buyTime;
    }

    public void addTicket(GTTicket ticket){
        tickets.add(ticket);
    }

    public ArrayList<GTTicket> getTickets() {
        return tickets;
    }

    public String getEmail() { return email; }

    public Integer getOrderID() { return orderID; }

    public Boolean getPayed() {
        return payed;
    }

    public GTEvent getEvent() {
        return event;
    }

    public Date getBuyTime() {
        return buyTime;
    }

}

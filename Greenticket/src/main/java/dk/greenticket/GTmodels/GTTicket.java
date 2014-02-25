package dk.greenticket.GTmodels;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lalan on 28/01/14.
 */
public class GTTicket {
    private String QRID, type, email;
    private Boolean checked;
    private Integer orderID;

    public GTTicket(String QRID, String type, Boolean checked, Integer orderID, String email){
        this.QRID = QRID;
        this.type = type;
        this.checked = checked;
        this.orderID = orderID;
        this.email = email;
    }

    public GTTicket(String QRID){
        this.QRID = QRID;
    }

    private void updateChecked(){


    }

    public int checkTicketInEvent(GTEvent event){
        return 0;
    }


    public String getQRID() {
        return QRID;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getType() {
        return type;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getChecked() {
        return checked;
    }
}

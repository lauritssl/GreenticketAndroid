package dk.greenticket.GTmodels;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lalan on 28/01/14.
 */
public class GTTicket {
    private String QRID, type;
    private Boolean checked;
    private String SECRET =  "MqHtRec35Zr6lAAOFZtb";

    public GTTicket(String QRID, String type, Boolean checked){
        this.QRID = QRID;
        this.type = type;
        this.checked = checked;
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
}

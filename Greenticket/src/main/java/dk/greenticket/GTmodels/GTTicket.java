package dk.greenticket.GTmodels;

/**
 * Created by lalan on 28/01/14.
 */
public class GTTicket {
    public String QRID;
    public boolean cheched;
    private String SECRET =  "MqHtRec35Zr6lAAOFZtb";

    public GTTicket(String QRID){
        this.QRID = QRID;
    }

    private void updateChecked(){

    }

    public int checkTicketInEvent(GTEvent event){
        return 0;
    }
}

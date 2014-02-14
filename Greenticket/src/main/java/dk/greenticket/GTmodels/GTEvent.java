package dk.greenticket.GTmodels;

import android.util.Log;

import java.util.Date;

/**
 * Created by lalan on 28/01/14.
 */
public class GTEvent {
    private String title, coverLink, organizer;
    private Date date, endDate;
    private Integer id;
    private Boolean active;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public GTEvent(String title, String coverLink, Date date, Date endDate, Integer id, Boolean active, String orgName){
        this.title = title;
        this.coverLink = coverLink;
        this.date = date;
        this.endDate = endDate;
        this.id = id;
        this.active = active;
        this.organizer = orgName;
    }
}

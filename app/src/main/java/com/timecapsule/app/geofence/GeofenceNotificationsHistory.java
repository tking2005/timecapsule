package com.timecapsule.app.geofence;

import java.util.Date;

/**
 * Created by tarynking on 3/7/17.
 */


public class GeofenceNotificationsHistory {
    private Date notifiedAt;
    private String geofenceTransitionDetails;
    public Date getNotifiedAt() {
        return notifiedAt;
    }
    public void setNotifiedAt(Date notifiedAt) {
        this.notifiedAt = notifiedAt;
    }
    public String getGeofenceTransitionDetails() {
        return geofenceTransitionDetails;
    }
    public void setGeofenceTransitionDetails(String geofenceTransitionDetails) {
        this.geofenceTransitionDetails = geofenceTransitionDetails;
    }
}

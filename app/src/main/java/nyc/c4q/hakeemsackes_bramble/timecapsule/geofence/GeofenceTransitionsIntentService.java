package nyc.c4q.hakeemsackes_bramble.timecapsule.geofence;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nyc.c4q.hakeemsackes_bramble.timecapsule.GoogleMapsActivity;
import nyc.c4q.hakeemsackes_bramble.timecapsule.R;


/**
 * Created by tarynking on 3/7/17.
 */


public class GeofenceTransitionsIntentService extends IntentService {

    protected static final String TAG = "geofence-transitions-service";
    public static int GEOFENCE_NOTIFICATION_ID = 101;
    public static int PUSH_NOTIFICATION_ID = 102;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    List<GeofenceNotificationsHistory> oldNotifications = new ArrayList<>();
    public static List<Geofence> triggeredFences = new ArrayList<>();
    private static boolean isAlreadyNotified = false;

    public GeofenceTransitionsIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager geoNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        GeofencingEvent geoFenceEvent = GeofencingEvent.fromIntent(intent);
        if (geoFenceEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(this, geoFenceEvent.getErrorCode());
            return;
        }
        int geofenceTransition = geoFenceEvent.getGeofenceTransition();

        //TODO ADD MARKERS FROM TRIGGERED GEOFENCE
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggeringGeofences = geoFenceEvent.getTriggeringGeofences();

            for (int i = 0; i < triggeringGeofences.size(); i++) {
                Geofence trigger = triggeringGeofences.get(i);
                triggeredFences.add(trigger);
                trigger.getRequestId();
            }


            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );
            if (isNotified(geofenceTransitionDetails)) {

            } else {
                addNotificationToList(geofenceTransitionDetails);

                sendNotification(geofenceTransitionDetails);

            }
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            geoNotificationManager.cancel(GEOFENCE_NOTIFICATION_ID);
        }
    }

    private String getGeofenceTransitionDetails(
            Context context,
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        if (triggeringGeofencesIdsList.size() == 1)
            return triggeringGeofencesIdsString + " is nearby!";
        else
            return "These places are nearby: " + triggeringGeofencesIdsString;
    }

    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), GoogleMapsActivity.class);

        // Construct a task stack.


        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                PendingIntent.getActivity(this, GEOFENCE_NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.time_capsule_logo4)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.time_capsule_logo4))
                .setContentTitle("Check this out!")
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);


        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(PUSH_NOTIFICATION_ID, builder.build());
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType A transition type constant defined in Geofence
     * @return A String indicating the type of transition
     */
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }


    private void addNotificationToList(String geofenceTransitionDetails) {
        boolean prevNotified = false;
        for (GeofenceNotificationsHistory item : oldNotifications) {
            if (item.getGeofenceTransitionDetails().equals(geofenceTransitionDetails)) {
                item.setNotifiedAt(new Date());
                prevNotified = true;
                break;
            }
        }
        if (!prevNotified) {
            GeofenceNotificationsHistory newItem = new GeofenceNotificationsHistory();
            newItem.setNotifiedAt(new Date());
            newItem.setGeofenceTransitionDetails(geofenceTransitionDetails);
            oldNotifications.add(newItem);
        }
    }

    private boolean isNotified(String notificationIds) {
        for (GeofenceNotificationsHistory item : oldNotifications) {
            if (item.getGeofenceTransitionDetails().equals(notificationIds)) {
                Date now = new Date();
                if (now.getTime() - item.getNotifiedAt().getTime() < Constants.GEOFENCE_NOTIFICATION_TIME) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
}


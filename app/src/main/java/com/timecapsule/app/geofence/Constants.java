package com.timecapsule.app.geofence;

/**
 * Created by tarynking on 3/7/17.
 */


public class Constants {

    private Constants() {
    }

    public static final String PACKAGE_NAME = "com.timecapsule.app.geofence";

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    public static final int CONNECTION_FAIL_RESOLUTION_REQUEST = 9000 ;

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 300; // 1 mile, 1.6 km
    public static final String PREFERENCE_DISTANCE = "PREF_DISTANCE";

    public static final double GEOFENCE_NOTIFICATION_TIME = 5 * 60 * 1000;
    public static final int WEEKLY_NOTIFICATION_ID = 101;
    public static final long LOCATION_UPDATE_INTERVAL = 60 * 60 * 10000;
    public static final long ALARM_WEEKLY_INTERVAL = 1000 * 60 * 60 * 24 * 7;
}

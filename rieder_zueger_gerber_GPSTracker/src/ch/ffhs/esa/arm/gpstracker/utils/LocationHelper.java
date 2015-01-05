package ch.ffhs.esa.arm.gpstracker.utils;

import ch.ffhs.esa.arm.gpstracker.EditPreferences;
import ch.ffhs.esa.arm.gpstracker.TrackingPosition;
import ch.ffhs.esa.arm.gpstracker.sqlitedb.TrackingDbLayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

/**
 * Helper for location functionalities
 * @author roger
 *
 */
public class LocationHelper {
  private static final String LOG = "ch.ffhs.esa.arm.gpstracker.utils.LocationHelper"; 
  
  private LocationHelper() {
  }
  
  /**
   * Tries to get the current gps position from the device
   * returns null, if the position returned from the location service 
   * returns null
   * @return
   */
  public static Location getCurrentPosition(Context context) {
	  // TODO: try to get the actual GPS location from the device
	  GPSTracker gpsTracker = new GPSTracker(context);
	  Location location = gpsTracker.getLocation();
	  Log.e(LOG, "location: " + location);
	  return location;
  }
  
  /**
   * Determines if a tracking is active. If so, the last stored
   * Location ist returned. In all other cases the return value
   * is null
   * @return
   */
  public static Location getLastPositionFromActiveTracking(Context context) {
	Location location = null;
	// determine if a trackin is active.
	SharedPreferences preferences = context.getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
	boolean isTrackingActive = preferences.getBoolean("pref_key_tracking_active", false);
	if (isTrackingActive) {
	  // If so, load the last stored Location from the database
	  TrackingDbLayer trackingDbLayer = new TrackingDbLayer(context);
	  TrackingPosition trackingPosition = trackingDbLayer.getNewestTrackingPosition();
	  location = new Location("");
	  location.setLatitude(trackingPosition.getLatitude());
	  location.setLongitude(trackingPosition.getLongitude());
	}
	  return location;
  }
}

package ch.ffhs.esa.arm.gpstracker.utils;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
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
   * Tryies to get the current gps position from the device
   * returns null, if the position returned from the location service 
   * returns null
   * @return
   */
  public static Location getCurrentPosition(Context context) {
	  // TODO: try to get the actual GPS location from the device
	  LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	  Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	  if (location == null) {
	    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	  }
	  
	  Log.e(LOG, "location: " + location);
	  return location;
  }
  
  /**
   * Determines if a tracking is active. If so, the last stored
   * Location ist returned. In all other cases the return value
   * is null
   * @return
   */
  public static Location getLastPositionFromActiveTracking() {
	  // TODO: determine if a trackin is active.
	  // If so, load the last stored Location from the database
	  return null;
  }
  
  public static List<Location> getLocationsForTracking(long id) {
    // TODO: load all location entries for this tracking id
	return null;
  }
}

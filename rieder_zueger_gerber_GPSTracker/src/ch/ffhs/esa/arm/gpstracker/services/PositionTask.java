package ch.ffhs.esa.arm.gpstracker.services;

import java.util.concurrent.Callable;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.util.Log;
import ch.ffhs.esa.arm.gpstracker.Tracking;
import ch.ffhs.esa.arm.gpstracker.TrackingPosition;
import ch.ffhs.esa.arm.gpstracker.sqlitedb.DbHelper;
import ch.ffhs.esa.arm.gpstracker.sqlitedb.TrackingDbLayer;
import ch.ffhs.esa.arm.gpstracker.sqlitedb.TrackingTbl;
import ch.ffhs.esa.arm.gpstracker.utils.GPSTracker;
import ch.ffhs.esa.arm.gpstracker.utils.LocationHelper;
import ch.ffhs.esa.arm.gpstracker.utils.MessageType;
import ch.ffhs.esa.arm.gpstracker.utils.PhoneNumberHelper;
import ch.ffhs.esa.arm.gpstracker.utils.SMSSender;

public class PositionTask implements Callable {
  private static final String LOG = "ch.ffhs.esa.arm.gpstracker.services.PositionTask"; 
  private boolean notification;
  private Context context;
  private GPSTracker gPSTracker;
  
  public PositionTask (boolean notification, Context context, GPSTracker gPSTracker) {
    this.notification = notification;
    this.context = context;
    this.gPSTracker = gPSTracker;
  }

  @Override
  public Object call() throws Exception {
	 
	Location location = gPSTracker.getLocation();
		
	if (location != null) {
	  Log.d(LOG, "Position Service got location: long: " + location.getLongitude() + " , lat:" + location.getLatitude());
	  // store Position in DB
	  TrackingDbLayer trackingDbLayer = new TrackingDbLayer(context);
	  Tracking newestTracking = trackingDbLayer.getNewestTracking();
	  trackingDbLayer.insertTrackingPosition(newestTracking.getTrackingId(), new TrackingPosition(location));
	  
	  if (notification) {
		SMSSender smsSender = new SMSSender(PhoneNumberHelper.getTrackingSMSReceivers(context), MessageType.STATUS_UPDATE, location, context);
		smsSender.start();
	  }
	  
	  Log.d(LOG, "Saved and retrieved Position: " + location);
	  
	} else {
	    Log.e(LOG, "Position Service couldn't determine a location");
	}
	return null;
  }
}

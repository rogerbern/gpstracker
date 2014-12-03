package ch.ffhs.esa.arm.gpstracker.services;

import java.util.concurrent.Callable;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import ch.ffhs.esa.arm.gpstracker.utils.LocationHelper;
import ch.ffhs.esa.arm.gpstracker.utils.MessageType;
import ch.ffhs.esa.arm.gpstracker.utils.PhoneNumberHelper;
import ch.ffhs.esa.arm.gpstracker.utils.SMSSender;

public class PositionTask implements Callable {
  private static final String LOG = "ch.ffhs.esa.arm.gpstracker.services.PositionTask"; 
  private boolean notification;
  private Context context;
  
  public PositionTask (boolean notification, Context context) {
    this.notification = notification;
    this.context = context;
  }

  @Override
  public Object call() throws Exception {
	Location location = LocationHelper.getCurrentPosition(context);
	Log.e(LOG, "Position retrieved: " + location);
	if (location != null) {
	  Log.e(LOG, "Position Service got location: long: " + location.getLongitude() + " , lat:" + location.getLatitude());
	  // TODO: store Position in DB
	  if (notification) {
		SMSSender smsSender = new SMSSender(PhoneNumberHelper.getTrackingSMSReceivers(context), MessageType.STATUS_UPDATE, location, context);
		smsSender.start();
	  }	
	} else {
	    Log.e(LOG, "Position Service couldn't determine a location");
	}
	return null;
  }
}

package ch.ffhs.esa.arm.gpstracker.services;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Intent Service that cares about binding to the gpslocation service and handle requests to it
 * @author roger
 *
 */
// TODO: implement a queau to send the messages and work on them one after the other
public class TrackingCallerIntentService extends IntentService implements ServiceConnection {
  public static final String TRACKING_CALLER_MESSAGE_NAME = "msg";
  public static final String TRACKING_START = "start";
  public static final String TRACKING_PAUSE = "pause";
  public static final String TRACKING_STOP = "stop";
  public static final String TRACKING_CONFIGURATION_CHANGED = "configuration changed";
  private static final String LOG = "ch.ffhs.esa.arm.gpstracker.services.TrackingCallerIntentService";
  //private GPSServiceConnection gpsServiceConnection;
  private IGeoPositionServiceRemote geoPositionService;
  private String message;
  
  public TrackingCallerIntentService() {
	  super("TrackingCaller");
	  // gpsServiceConnection = new GPSServiceConnection();
	  //final Intent serviceIntent = new Intent(getApplicationContext(), GeoPositionServiceRemote.class);
	  //getApplicationContext().bindService(serviceIntent, gpsServiceConnection, Context.BIND_AUTO_CREATE);
  }

  @Override
  public void onHandleIntent(Intent intent) {
	message = intent.getStringExtra(TRACKING_CALLER_MESSAGE_NAME);
	final Intent serviceIntent = new Intent(this, GeoPositionServiceRemote.class);
  	bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
  }
  
  @Override
  public void onServiceConnected(ComponentName className, IBinder binder) {
	this.geoPositionService = IGeoPositionServiceRemote.Stub.asInterface(binder);
	Log.i(LOG, "GeoPositionService connected: " + this.geoPositionService.toString());
	try {
	  if (message != null) {
  	    if(message.equals(TRACKING_START)) {
		  Log.i(LOG, this.geoPositionService.start());
  	    } else if (message.equals(TRACKING_PAUSE)) {
  		    Log.i(LOG, this.geoPositionService.pause());
  	    } else if (message.equals(TRACKING_STOP)) {
  		    Log.i(LOG, this.geoPositionService.stop());
  	    } else if (message.equals(TRACKING_CONFIGURATION_CHANGED)) {
  	    	Log.i(LOG, this.geoPositionService.configChanged());
  	    } else {
  		    Log.e(LOG, "the msg object contained an unexpected value: " + message);
  	    }
  	  } else {
  	      Log.e(LOG, "geoPositionService can not be connected");
      }
    } catch(RemoteException e) {
	    Log.e(LOG, "The call to geoPositionService resultet in an exception");
	} finally {
	    getApplicationContext().unbindService(this);	
	}
  }

  @Override
  public void onServiceDisconnected(ComponentName name) {
	Log.i(LOG, "Connection to GeoPositionService is lost");	
  }
}

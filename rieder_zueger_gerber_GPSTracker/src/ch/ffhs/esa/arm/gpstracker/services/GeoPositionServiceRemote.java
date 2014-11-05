package ch.ffhs.esa.arm.gpstracker.services;

import ch.ffhs.esa.arm.gpstracker.EditPreferences;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Remote Service for geoposition tracking
 * @author roger
 *
 */
public class GeoPositionServiceRemote extends Service {
	private static final String LOG = "ch.ffhs.esa.arm.gpstracker.services.GeoPositionServiceRemote";
	private static final String DEFAULT_VALUE = "default value";
	public static final String BROADCAST_STRING = "ch.ffhs.esa.arm.gpstracker.preferenceschanged";
	private SharedPreferences preferences;
	private String intervall;
	private boolean notification;
	private boolean started;
	private boolean stopped;
	
	// Getter and setter
	protected String getIntervall() {
		return this.intervall;
	}
	
	protected void setIntervall(String intervall) {
		this.intervall = intervall;
		// TODO: change tracking, or better listen in the tracking thread to this
	}
	
	protected boolean getNotification() {
		return this.notification;
	}
	
	protected void setNotification(boolean notification) {
		this.notification = notification;
		// TODO: listen in the tracking thread for this
	}
	
	protected boolean getStarted() {
		return this.started;
	}
	
	protected void setStarted(boolean started) {
		this.started = started;
	}
	
	protected boolean getStopped() {
		return this.stopped;
	}
	
	protected void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
	
	@Override
	public void onCreate() {
		// TODO: database connection for storing gps data
		// read application settings
		preferences = getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
		//final IntentFilter intentFilter = new IntentFilter(BROADCAST_STRING);
		//preferencesChangedBroadcastReceiver = new PreferenceChangedBroadcastReceiver();
		//getApplicationContext().registerReceiver(preferencesChangedBroadcastReceiver, intentFilter);
		// TODO: examin if tracking should be started
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		// TODO: release database connection
		// TODO: stop tracking
		// TODO: Log event (there is a problem, if a tracking is actives)
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return gpsBinder;
	}
	
	private final IGeoPositionServiceRemote.Stub gpsBinder = new IGeoPositionServiceRemote.Stub() {
		@Override
		public String stop() throws RemoteException {
			setStopped(true);
			return "stopped";
		}
		
		@Override
		public String start() throws RemoteException {
			setStarted(true);
			return "started";
		}
		
		@Override
		public String pause() throws RemoteException {
			setStarted(false);
			return "paused";
		}
		
		@Override
		public String configChanged() throws RemoteException {
			// check the preferences for tracking relevant information
			preferences = getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
		    String intervall = preferences.getString("pref_key_gps_intervall", DEFAULT_VALUE);
		    if (!intervall.equals(DEFAULT_VALUE)) {
		    	if(!intervall.equals(getIntervall())) {
		    		setIntervall(intervall);
		    	}
		    }
		    Boolean notification = preferences.getBoolean("pref_key_tracking_activate_notification", false);
			if (notification != null) {
				if(notification != getNotification()) {
					setNotification(notification);
				}
			}
		    return "config changed";
		}
	}; 

}

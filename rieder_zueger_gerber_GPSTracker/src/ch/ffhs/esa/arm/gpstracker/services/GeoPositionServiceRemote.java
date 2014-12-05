package ch.ffhs.esa.arm.gpstracker.services;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ch.ffhs.esa.arm.gpstracker.EditPreferences;
import android.R;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
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
	private boolean started;
	private Date lastSaved;
	
	@Override
	public void onCreate() {
	  super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	  manage();
	  return(START_STICKY);
	}
	
	private <T> void manage() {
	  Log.i(LOG, "Position manager started");
	  Notification note = new Notification(0, null, System.currentTimeMillis());
	  note.flags |= Notification.FLAG_NO_CLEAR;
	  startForeground(1111, note);
	  
	  while (true) {
		preferences = getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
		this.started = preferences.getBoolean("pref_key_tracking_play", false);
		
		Log.i(LOG, "started: " + this.started);
		if (this.started) {
		  Log.i(LOG, "tracking is active");
		  this.intervall = preferences.getString("pref_key_gps_intervall", DEFAULT_VALUE);
		  Log.i(LOG, "interval read: " + this.intervall);
		  Log.i(LOG, "interval not equal default_value: " + !this.intervall.equals(DEFAULT_VALUE));
		  if (!this.intervall.equals(DEFAULT_VALUE)) {
			this.intervall = this.intervall.substring(0, this.intervall.indexOf(" min"));
			int intervallTime = Integer.parseInt(this.intervall) * 1000;
			Log.i(LOG, "itnerval int: "  + intervallTime);
			if (lastSaved == null) {
			  // TODO: get Date from DB
			  
			  if (lastSaved == null) {
			    // if no entry in DB the tracking should start immeadeatly
			    Calendar cal = Calendar.getInstance();
			    cal.set(1970, Calendar.DECEMBER, 01);
			    this.lastSaved = cal.getTime();
			  }
			}
			long nextTracking = lastSaved.getTime() + intervallTime;
			Log.i(LOG, "next Tracking in: " + nextTracking + " ms.");
			if (new Date().getTime() >= nextTracking) {
			  ExecutorService executor = Executors.newSingleThreadExecutor();
			  try {
				Log.i(LOG, "starting position thread");
				executor.invokeAll((Collection<? extends Callable<T>>) Arrays.asList(new PositionTask(preferences.getBoolean("pref_key_tracking_activate_notification", false), getApplicationContext())), new Date().getTime() + intervallTime, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				Log.e(LOG, "GeoPositionoService PositionThread crashed");
			} finally {
				executor.shutdown();	
			}
			}
		  }
		}
		try {
		  TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			Log.e(LOG, "GeoPositionService timeout crashed");
		}
	  }
	}
	
	@Override
	public void onDestroy() {
		// TODO: release database connection
		// TODO: stop tracking
		// TODO: Log event (there is a problem, if a tracking is actives)
	}

	@Override
	public IBinder onBind(Intent intent) {
	  return null;
	}
}

package ch.ffhs.esa.arm.gpstracker;

import ch.ffhs.esa.arm.gpstracker.services.TrackingCallerIntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;

/**
 * Preference Activity
 * Sets an application context for the preferences, so its accessible for background processes
 * @author roger
 *
 */
public class EditPreferences extends BasePreferenceActivity {
	public static final String SHARED_PREF_NAME = "ch.ffhs.esa.arm.preferences";
	public static final String LOG = "ch.ffhs.esa.arm.gsptracker.EditPreferences";
	private SharedPreferences.OnSharedPreferenceChangeListener preferenceChanged;
	// private IGeoPositionServiceRemote geoPositionService;
	private PreferenceManager preferenceManager;
	// Implement connector for the GeoPositionService
    /*private ServiceConnection gpsServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
		  geoPositionService = IGeoPositionServiceRemote.Stub.asInterface(binder);
		  Log.i(LOG, "GeoPositionService connected");
		}
		
		@Override
		public void onServiceDisconnected(ComponentName className) {
			Log.i(LOG, "GeoPositionService disconnected");
		}
	};*/
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  getListView().setScrollbarFadingEnabled(false);
  }
    
    @Override
    public void onResume() {
      // bind gpsServiceConnection
      //final Intent intent = new Intent(getApplicationContext(), GeoPositionServiceRemote.class);
  	  //getApplicationContext().bindService(intent, gpsServiceConnection, Context.BIND_AUTO_CREATE);
  	  // define listener for changed values in the preferences, because this could affect the tracking service
  	  preferenceChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			Intent msgIntent = new Intent(getApplicationContext(), TrackingCallerIntentService.class);
		    msgIntent.putExtra(TrackingCallerIntentService.TRACKING_CALLER_MESSAGE_NAME, TrackingCallerIntentService.TRACKING_CONFIGURATION_CHANGED);
		    startService(msgIntent);
	    }
	   };
	  preferenceManager = getPreferenceManager();
	  preferenceManager.setSharedPreferencesName(SHARED_PREF_NAME);
	  preferenceManager.setSharedPreferencesMode(MODE_MULTI_PROCESS);
	  addPreferencesFromResource(R.xml.preferences);
	  // tracking name should not be accessible on this screen
	  PreferenceCategory category = (PreferenceCategory) findPreference("pref_key_tracking_settings");
	  category.removePreference(findPreference("pref_key_tracking_name"));
	  preferenceManager.getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChanged);
  	  super.onResume();
    }
    
    @Override
    public void onStop() {
      // release ressources
      //getApplicationContext().unbindService(gpsServiceConnection);
      preferenceManager.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChanged);
      super.onStop();
    }
}

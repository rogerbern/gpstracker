package ch.ffhs.esa.arm.gpstracker;

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
	private PreferenceManager preferenceManager;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  getListView().setScrollbarFadingEnabled(false);
  }
    
    @Override
    public void onResume() {
	  preferenceManager = getPreferenceManager();
	  preferenceManager.setSharedPreferencesName(SHARED_PREF_NAME);
	  preferenceManager.setSharedPreferencesMode(MODE_MULTI_PROCESS);
	  addPreferencesFromResource(R.xml.preferences);
	  // tracking name should not be accessible on this screen
	  PreferenceCategory category = (PreferenceCategory) findPreference("pref_key_tracking_settings");
	  category.removePreference(findPreference("pref_key_tracking_name"));
	  category.removePreference(findPreference("pref_key_tracking_active"));
	  category.removePreference(findPreference("pref_key_tracking_play"));
  	  super.onResume();
    }
    
    @Override
    public void onStop() {
      // release ressources
      super.onStop();
    }
}

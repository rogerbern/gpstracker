package ch.ffhs.esa.arm.gpstracker;

import android.os.Bundle;

/**
 * 
 * @author roger
 *
 */
public class EditPreferences extends BasePreferenceActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  addPreferencesFromResource(R.xml.preferences);
	  getListView().setScrollbarFadingEnabled(false);
  }
}

package ch.ffhs.esa.arm.gpstracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author roger
 *
 */
public class MainActivity extends BaseActivity {
	private TextView menuItemUrgencySettingsText;
	private Button menuItemUrgencyCallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkUrgencySettings();
    }

    /**
     * Checks if urgency settings are made, if not it disables the 'urgencyCall' Button
     * and enables the help text. Otherwise it does the contrary.
     */
    private void checkUrgencySettings() {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
      String phoneCallReceifer = preferences.getString("pref_key_urgency_phonecall_receiver", "");
      this.menuItemUrgencyCallButton = (Button) findViewById(R.id.urgency_call_button);
      this.menuItemUrgencySettingsText = (TextView) findViewById(R.id.urgency_settings_text);
      if (phoneCallReceifer.length() == 0) {
      	// disable button
        this.menuItemUrgencyCallButton.setEnabled(false);
        // show help text
        this.menuItemUrgencySettingsText.setVisibility(View.VISIBLE);
      } else {
          // enable button
          this.menuItemUrgencyCallButton.setEnabled(true);
          // hide help text
          this.menuItemUrgencySettingsText.setVisibility(View.INVISIBLE);
      }
    }
    
    /**
     * ToDo
     * @param view
     */
    public void urgencyCall(View view) {
    	// make phonecall
    	// send sms
    }
}

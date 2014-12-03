package ch.ffhs.esa.arm.gpstracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

/**
 * Activity to configure a new tracking.
 * @author roger
 *
 */
public class TrackingNewActivity extends BaseActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_new);
    }
	
	@Override
	public void onResume() {
	  super.onResume();
	  
	  // load already stored preference values form the shared preferences and set them as
	  // values in the input fields of the form
      SharedPreferences preferences = getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
        
      Spinner gpsIntervallSpinner = (Spinner) findViewById(R.id.intervall_selector);
      String trackingIntervall = preferences.getString("pref_key_gps_intervall", "5 min");
        
      ArrayAdapter myAdap = (ArrayAdapter) gpsIntervallSpinner.getAdapter();
      int spinnerPosition = myAdap.getPosition(trackingIntervall);
      gpsIntervallSpinner.setSelection(spinnerPosition);

      EditText smsReceiverEditText1 = (EditText) findViewById(R.id.editText_sms_receiver_1);
      String smsReceiver1 = preferences.getString("pref_key_tracking_sms_receiver1", "");
      smsReceiverEditText1.setText(smsReceiver1);
        
      EditText smsReceiverEditText2 = (EditText) findViewById(R.id.editText_sms_receiver_2);
      String smsReceiver2 = preferences.getString("pref_key_tracking_sms_receiver2", "");
      smsReceiverEditText2.setText(smsReceiver2);
        
      EditText smsReceiverEditText3 = (EditText) findViewById(R.id.editText_sms_receiver_3);
      String smsReceiver3 = preferences.getString("pref_key_tracking_sms_receiver3", "");
      smsReceiverEditText3.setText(smsReceiver3);
        
      Switch activateNotificationSwitch = (Switch) findViewById(R.id.tracking_notification_activate);
      boolean activateNotification = preferences.getBoolean("pref_key_tracking_activate_notification", false);
      activateNotificationSwitch.setChecked(activateNotification);
	}
	
	public void save(View view) {
	  // load the preference manager in edit mode
	  SharedPreferences preferences = getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
	  SharedPreferences.Editor editor = preferences.edit();
	  
	  // load the form field values and store them in the appropriate preferences
	  EditText trackingName = (EditText) findViewById(R.id.editText_tracking_name);
	  String trackingNameValue = trackingName.getEditableText().toString();
	  editor.putString("pref_key_tracking_name", trackingNameValue);
		
	  Spinner gpsIntervallSpinner = (Spinner) findViewById(R.id.intervall_selector);
      String gpsIntervallValue = (String) gpsIntervallSpinner.getSelectedItem();
	  editor.putString("pref_key_gps_intervall", gpsIntervallValue);
		
	  EditText smsReceiverEditText1 = (EditText) findViewById(R.id.editText_sms_receiver_1);
	  String smsReceiverEditText1Value = smsReceiverEditText1.getEditableText().toString();
	  editor.putString("pref_key_tracking_sms_receiver1", smsReceiverEditText1Value);
		
	  EditText smsReceiverEditText2 = (EditText) findViewById(R.id.editText_sms_receiver_2);
	  String smsReceiverEditText2Value = smsReceiverEditText2.getEditableText().toString();
	  editor.putString("pref_key_tracking_sms_receiver2", smsReceiverEditText2Value);
		
	  EditText smsReceiverEditText3 = (EditText) findViewById(R.id.editText_sms_receiver_3);
	  String smsReceiverEditText3Value = smsReceiverEditText3.getEditableText().toString();
	  editor.putString("pref_key_tracking_sms_receiver3", smsReceiverEditText3Value);
		
	  Switch activateNotificationSwitch = (Switch) findViewById(R.id.tracking_notification_activate);
	  boolean activateNotificationValue = activateNotificationSwitch.isChecked();
	  editor.putBoolean("pref_key_tracking_activate_notification", activateNotificationValue);
		
	  // commit the changes to persistence
	  editor.commit();
	  NavigationComponent.setBooleanPreferencesValue(this, NavigationComponent.TRACKING_ACTIVE_KEY, true);
	  NavigationComponent.setBooleanPreferencesValue(this, NavigationComponent.TRACKING_PLAY_KEY, false);
	  // redirect to main activity
      Intent intent = new Intent(this, MainActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
	}
}

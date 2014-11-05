package ch.ffhs.esa.arm.gpstracker;

import java.util.Set;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author roger
 *
 */
public class MainActivity extends BaseActivity {
	private static final String LOG = "ch.ffhs.esa.arm.gpstracker.MainActivity";
	private TextView menuItemUrgencySettingsText;
	private Button menuItemUrgencyCallButton;
	private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
    protected void onResume() {
    	checkUrgencySettings();
    	this.locationClient.connect();
    	super.onResume();
    }
    
	@Override
	protected void onStop() {
		this.locationClient.disconnect();
		super.onStop();
	}
    
    /**
     * Checks if urgency settings are made, if not it disables the 'urgencyCall' Button
     * and enables the help text. Otherwise it does the contrary.
     */
    private void checkUrgencySettings() {
      String urgencyPhoneCallReceiver = PhoneNumberHelper.getUrgencyPhoneNumber(this);
      this.menuItemUrgencyCallButton = (Button) findViewById(R.id.urgency_call_button);
      this.menuItemUrgencySettingsText = (TextView) findViewById(R.id.urgency_settings_text);
      if (urgencyPhoneCallReceiver.length() == 0) {
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
     * Sends SMS to urgency receivers (if there are) and calls the urgency phonecall receiver.
     * @param view
     */
    public void urgencyCall(View view) {
      // load sms receiver phone numbers
      Set<String> smsReceivers = PhoneNumberHelper.getUrgencySMSReceivers(this);
      // if there is at least one phone number returned, create a new SMSSender Thread
      if (!smsReceivers.isEmpty()) {
        SMSSender smsSender = new SMSSender(smsReceivers, MessageType.URGENCY);
        smsSender.start();
      }
    	
      // make phonecall
    	Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+PhoneNumberHelper.getUrgencyPhoneNumber(this)));
    	startActivity(intent);
    }
}

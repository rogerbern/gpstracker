package ch.ffhs.esa.arm.gpstracker;

import ch.ffhs.esa.arm.gpstracker.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 
 * @author roger
 *
 */
public class LockActivity extends BaseActivity {
	private static final String PASSWORD = "pref_key_lock_pwd";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	// TODO: else not show a message,
    	if (isPasswordSet()) {
    		// lock navigation
    		Navigation.getInstance(this).setLock(true);
    		// redirect to main activity
    		Intent intent = new Intent(this, MainActivity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(intent);
    	} else {
    		// messageText = (TextView) findViewById(R.id.lock_introduction_text);
    	}
    	
    }
    
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	private boolean isPasswordSet() {
		SharedPreferences preferences = getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
		String password = preferences.getString(PASSWORD, "");
		if (password.isEmpty()) {
			return false;
		}
		return true;
	}
}

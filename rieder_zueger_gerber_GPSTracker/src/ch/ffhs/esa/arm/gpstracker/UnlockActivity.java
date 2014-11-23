package ch.ffhs.esa.arm.gpstracker;

import ch.ffhs.esa.arm.gpstracker.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * @author roger
 *
 */
public class UnlockActivity extends BaseActivity {
	private static final String PASSWORD = "pref_key_lock_pwd";
	private TextView errorText;
	private EditText passwordText;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
        errorText = (TextView) findViewById(R.id.unlock_error_text);
		errorText.setVisibility(View.INVISIBLE);
    }
    
    @Override
    protected void onResume() {
      super.onResume();
    }
    
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	public void enterPassword(View view) {
		// getPassword from settings
		SharedPreferences preferences = getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
		String password = preferences.getString(PASSWORD, "");
		// getPassword from user
		passwordText = (EditText) findViewById(R.id.editText_password);
		// determine if passwords are the same
		if (password.equals(passwordText.getText().toString())) {
			// TODO: if passwords match unlock menu
			Navigation.getInstance(this).setLock(false);
			// redirect to mainActivity
			Intent intent = new Intent(this, MainActivity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(intent);
		} else {
		  // if passwords does not match show error message
		  errorText = (TextView) findViewById(R.id.unlock_error_text);
		  errorText.setVisibility(View.VISIBLE);
		}
	}
}
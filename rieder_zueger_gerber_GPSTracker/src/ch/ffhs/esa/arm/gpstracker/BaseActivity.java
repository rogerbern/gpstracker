package ch.ffhs.esa.arm.gpstracker;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @author roger     
 *
 */
public class BaseActivity extends Activity {
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation_actions, menu);
        // Navigation.getInstance().calculateActionBar(menu);
        onPrepareOptionsMenu(menu);
        getActionBar().setHomeButtonEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }
	
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	Navigation.getInstance().calculateActionBar(menu);
    	return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	// Handle presses on the action bar items
        return Navigation.getInstance().onOptionsItemSelected(item, this);
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

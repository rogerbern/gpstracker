package ch.ffhs.esa.arm.gpstracker;

import ch.ffhs.esa.arm.gpstracker.services.GeoPositionServiceRemote;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/** 
 * 
 * @author roger 
 *
 */
public class BaseActivity extends Activity {
	private static final String LOG = "ch.ffhs.esa.arm.gpstracker.BaseActivity";
	private NavigationComponent navigation;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation_actions, menu);
        //onPrepareOptionsMenu(menu);
        navigation = new NavigationComponent(this, menu);
        getActionBar().setHomeButtonEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }
	
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	//Navigation.getInstance(this).calculateActionBar(menu);
    	navigation.paintNavigation();
    	return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	// Handle presses on the action bar items
    	return navigation.onOptionsItemSelected(item);
    }
    
    @Override
    public void onResume() {
      if (navigation != null) {
        navigation.paintNavigation();
      }
      if (!isMyServiceRunning(GeoPositionServiceRemote.class)) {
    	Intent serviceIntent = new Intent(this, GeoPositionServiceRemote.class);
    	this.startService(serviceIntent);
    	Log.i(LOG, "GeoPositionService started.");
      }
      super.onResume();
    }
    
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
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

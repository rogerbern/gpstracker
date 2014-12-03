package ch.ffhs.esa.arm.gpstracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

public class NavigationComponent {
  protected static final String TRACKING_ACTIVE_KEY = "pref_key_tracking_active";
  protected static final String TRACKING_PLAY_KEY = "pref_key_tracking_play";
  protected static final String LOCK_SETTINGS_KEY = "pref_key_lock_settings";
  private Activity context;
  private Menu menu;
  private MenuItem menuItemTaskPlay;
  private MenuItem menuItemTaskPause;
  private MenuItem menuItemTaskStopp;
  private MenuItem menuItemTaskNew;
  private MenuItem menuItemMapView;
  private MenuItem menuItemTaskList;
  private MenuItem menuItemUrgencySettings;
  private MenuItem menuItemHelp;
  private MenuItem menuItemLock;
  private MenuItem menuItemUnlock;
  
  public NavigationComponent(Activity activity, Menu menu) {
    this.context = activity;
    this.menu = menu;
  }
  
  /**
   * paints the actionbar icons depending on the actual state of the application.
   */
  public void paintNavigation() {
	boolean trackingActive = getBooleanPreferencesValue(TRACKING_ACTIVE_KEY);
	boolean trackingPlay = getBooleanPreferencesValue(TRACKING_PLAY_KEY);
	boolean lock = getBooleanPreferencesValue(LOCK_SETTINGS_KEY);
	  
	this.menuItemTaskPlay = menu.findItem(R.id.action_tracking_start);
    this.menuItemTaskPause = menu.findItem(R.id.action_tracking_pause);
    this.menuItemTaskStopp = menu.findItem(R.id.action_tracking_stop);
	this.menuItemTaskNew = menu.findItem(R.id.action_tracking_add);
	this.menuItemMapView = menu.findItem(R.id.action_map);
	this.menuItemTaskList = menu.findItem(R.id.action_tracking_list);
	this.menuItemUrgencySettings = menu.findItem(R.id.action_settings);
	this.menuItemHelp = menu.findItem(R.id.action_help);
	this.menuItemLock = menu.findItem(R.id.action_lock);
	this.menuItemUnlock = menu.findItem(R.id.action_unlock);
	  
	if(!lock) {
	  alterVisibility(true, this.menuItemMapView, this.menuItemUrgencySettings, this.menuItemLock);
	  alterVisibility(false, this.menuItemUnlock);
	  if(trackingActive) {
		alterVisibility(true, this.menuItemTaskStopp);
		alterVisibility(false, this.menuItemTaskNew);
		if(trackingPlay) {
		  alterVisibility(true, this.menuItemTaskPause);
		  alterVisibility(false, this.menuItemTaskPlay);
		} else {
		  alterVisibility(true, this.menuItemTaskPlay);
		  alterVisibility(false, this.menuItemTaskPause);
	    }
	  } else {
		alterVisibility(true, this.menuItemTaskNew);
		alterVisibility(false, this.menuItemTaskPause, this.menuItemTaskPlay, this.menuItemTaskStopp);
	  }
	  if (this.context instanceof TrackingNewActivity) {
        alterVisibility(false, this.menuItemTaskNew);
	  }
	} else {
	  alterVisibility(true, this.menuItemUnlock);
	  alterVisibility(false, this.menuItemLock, this.menuItemTaskPlay, this.menuItemTaskPause, this.menuItemTaskStopp, this.menuItemTaskNew, this.menuItemTaskList, this.menuItemMapView, this.menuItemUrgencySettings);
	}
  }
  
  /**
   * Handles Navigation on MenuOptionItem Select
   * @param item
   * @param activity
   * @return
   */
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
	  case android.R.id.home:
	    startActivity(MainActivity.class);
		return true;
      case R.id.action_tracking_start:
        setBooleanPreferencesValue(TRACKING_PLAY_KEY, true);
        paintNavigation();
        return true;
      case R.id.action_tracking_pause:
        setBooleanPreferencesValue(TRACKING_PLAY_KEY, false);
        paintNavigation();
        return true;
      case R.id.action_tracking_stop:
        // TODO: mark active tracking as closed on db
    	setBooleanPreferencesValue(TRACKING_ACTIVE_KEY, false);
        setBooleanPreferencesValue(TRACKING_PLAY_KEY, false);
        paintNavigation();
        return true;
      case R.id.action_tracking_add:
        startActivity(TrackingNewActivity.class);
        return true;
      case R.id.action_map:
        startActivity(MapViewActivity.class);
        return true;
      case R.id.action_tracking_list:
        startActivity(TrackingListActivity.class);
        return true;
      case R.id.action_settings:
        startActivity(EditPreferences.class);
        return true;
      case R.id.action_help:
        startActivity(HelpActivity.class);
        return true;
      case R.id.action_lock:
        startActivity(LockActivity.class);
        return true;
        // TODO: on all Activities (put in BaseActivity and do not forget the PreferenceActivity)
        // onResume() determine if lock is set, if so redirect to the MainActivity
      case R.id.action_unlock:
        startActivity(UnlockActivity.class);
        return true;
      default:
        return false;
    }
  }
  
  // Helpers
  /**
   * Helper Method that starts an activity
   * @param c Class to handle the activity
   * @param activity
   */
  private <T> void startActivity(Class<T> c) {
    Intent i = new Intent(context, c);
	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	context.startActivity(i);
  }
  
  /**
   * Helper method for altering visibility of ActionMenu Items
   * @param items
   */
  private void alterVisibility(boolean state, MenuItem ... items) {
    for (MenuItem item : items) {
	  item.setVisible(state);
	}
  }
  
  /**
   * Helper that persists a boolean value in a preferenece.
   * @param activity
   * @param key
   * @param value
   */
  protected static void setBooleanPreferencesValue(Activity context, String key, boolean value) {
    SharedPreferences preferences = context.getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
	SharedPreferences.Editor editor = preferences.edit();
	editor.putBoolean(key, value);
	editor.apply();
  }

  private void setBooleanPreferencesValue(String key, boolean value) {
    setBooleanPreferencesValue(this.context, key, value);
  }

  /**
   * Helper that returns the boolean value of a property.
   * @param key
   * @return
   */
  private boolean getBooleanPreferencesValue(String key) {
    SharedPreferences preferences = this.context.getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
	return preferences.getBoolean(key, false);
  }
}

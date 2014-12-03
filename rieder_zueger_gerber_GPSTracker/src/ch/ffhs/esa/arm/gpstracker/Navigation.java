package ch.ffhs.esa.arm.gpstracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Model for ActionBar Items.
 * Controls the appeareance of the ActionBar depending on context settings.
 * Singleton
 * @author roger
 *
 */
public class Navigation extends Application {
  private static final String TRACKING_ACTIVE_KEY = "pref_key_tracking_active";
  private static final String TRACKING_PLAY_KEY = "pref_key_tracking_play";
  private static final String LOCK_SETTINGS_KEY = "pref_key_lock_settings";
  private static Navigation instance = null;
  private boolean trackingActive; // ToDo: read from stored state
  private boolean trackingPlay; // ToDo: read from stored state
  private boolean lockActive; // ToDo: read from stored state
  private boolean initialized = false;
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
  private static Context context;
  
  // Singleton, so only private constructor
  private Navigation() {
  }
  
  /**
   * Returns an instance of the singleton Navigation
   * Handles the ActionBar
   * @return
   */
  public static Navigation getInstance(Activity activity) {
	  if (instance == null) {
		  instance = new Navigation();
		  context = activity;
	  }
	  return instance;
  }
  
  /**
   * Configures the actionBar
   * @param menu
   */
  public void calculateActionBar(Menu menu) {
	  this.trackingActive = getBooleanPreferencesValue(TRACKING_ACTIVE_KEY);
	  this.trackingPlay = getBooleanPreferencesValue(TRACKING_PLAY_KEY);
	  if (!this.initialized) {
	    initialize(menu);
	  }
	  if(!this.lockActive) {
	    processInactiveLock();
	  }
	  processTracking();
	  if (this.lockActive) {
		processActiveLock();
	  }
  }
  
  /**
   * Initializes the private Members with the MenuItems once.
   * @param menu
   */
  private void initialize(Menu menu) {
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
	this.initialized = true;
  }
  
  private void setBooleanPreferencesValue(Activity activity, String key, boolean value) {
	SharedPreferences preferences = activity.getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }
  
  private boolean getBooleanPreferencesValue(String key) {
    SharedPreferences preferences = this.context.getSharedPreferences(EditPreferences.SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
    return preferences.getBoolean(key, false);
  }
  
  /**
   * Handles Navigation on MenuOptionItem Select
   * @param item
   * @param activity
   * @return
   */
  public boolean onOptionsItemSelected(MenuItem item, Activity activity) {
	  switch (item.getItemId()) {
	  case android.R.id.home:
		  actionMainIntend(activity);
		  return true;
        case R.id.action_tracking_start:
          setBooleanPreferencesValue(activity, TRACKING_PLAY_KEY, true);
          setTrackingPlay(true);
          // TODO: write to settings
          return true;
        case R.id.action_tracking_pause:
          setBooleanPreferencesValue(activity, TRACKING_PLAY_KEY, false);
          setTrackingPlay(false);
          // TODO: write to settings
          return true;
        case R.id.action_tracking_stop:
          setBooleanPreferencesValue(activity, TRACKING_ACTIVE_KEY, false);
          setBooleanPreferencesValue(activity, TRACKING_PLAY_KEY, false);
          setTrackingPlay(false);
          setTrackingActive(false);
         // TODO: write to settings
          return true;
        case R.id.action_tracking_add:
          setBooleanPreferencesValue(activity, TRACKING_ACTIVE_KEY, true);
          actionNewIntend(activity);
          return true;
        case R.id.action_map:
          actionMapViewIntend(activity);
          return true;
        case R.id.action_tracking_list:
          actionTrackingListIntend(activity);
          return true;
        case R.id.action_settings:
        	actionPreferenceIntend(activity);
        	return true;
        case R.id.action_help:
        	actionHelpIntend(activity);
        	return true;
        case R.id.action_lock:
        	// Set the preference locked
        	setBooleanPreferencesValue(activity, LOCK_SETTINGS_KEY, true);
        	actionLockIntend(activity);
        	return true;
        	// TODO: on all Activities (put in BaseActivity and do not forget the PreferenceActivity)
        	// onResume() determine if lock is set, if so redirect to the MainActivity
        case R.id.action_unlock:
        	actionUnlockIntend(activity);
        	return true;
        default:
          return false;
    }
  }
  
  /**
   * Helper Method that starts an activity
   * @param c Class to handle the activity
   * @param activity
   */
  private <T> void startActivity(Class<T> c, Activity activity) {
	  Intent i = new Intent(activity, c);
	  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	  activity.startActivity(i);
  }
  
  public void actionMainIntend(Activity activity) {
	  startActivity(MainActivity.class, activity);
  }
  
  private void actionTrackingListIntend(Activity activity) {
    startActivity(TrackingListActivity.class, activity);
  }
  
  private void actionNewIntend(Activity activity) {
	startActivity(TrackingNewActivity.class, activity);
  }
  
  private void actionMapViewIntend(Activity activity) {
	startActivity(MapViewActivity.class, activity);
  }
  
  private void actionPreferenceIntend(Activity activity) {
	startActivity(EditPreferences.class, activity);
  }
  
  private void actionHelpIntend(Activity activity) {
	startActivity(HelpActivity.class, activity);
  }
  
  private void actionLockIntend(Activity activity) {
	  startActivity(LockActivity.class, activity);
  }
  
  private void actionUnlockIntend(Activity activity) {
	  startActivity(UnlockActivity.class, activity);
  }
  
  /**
   * Alter the state wether tracking is running or not
   * @param state
   */
  public void setTrackingPlay(boolean state) {
	  // ToDo: add error handling
	  this.trackingPlay = state;
	  processTrackingPlay();
  }
  
  /**
   * Alter the state wether a tracking is active or not
   * @param state
   */
  public void setTrackingActive(boolean state) {
	  // ToDo: add error handling
	  this.trackingActive = state;
	  processTracking();
  }
  
  /**
   * Alter the state wether the actionBar lock ist set or not
   * @param state
   */
  public void setLock(boolean state) {
	  // ToDo: add error handling
	  this.lockActive = state;
	  processLock();
  }
  
  private void processTracking() {
	  if (this.trackingActive) {
		  alterVisibility(this.trackingActive, menuItemTaskStopp);
		  alterVisibility(!this.trackingActive, menuItemTaskNew);
		  processTrackingPlay();
	  } else {
		  alterVisibility(this.trackingActive, menuItemTaskStopp, menuItemTaskPlay, menuItemTaskPause);
		  alterVisibility(!this.trackingActive, menuItemTaskNew);
	  }
  }
  
  private void processTrackingPlay() {
	  alterVisibility(!this.trackingPlay, menuItemTaskPlay);
	  alterVisibility(this.trackingPlay, menuItemTaskPause);
  }
  
  /**
   * Helper method that checks lock state and calls appropriate processing method 
   */
  public void processLock() {
	  if (lockActive) {
		processActiveLock();
	  } else {
		  processInactiveLock();
	  }
  }
  
  /**
   * Creates the menu for the TrackingNewActivity
   */
  public void proecessNewTrackingActivityMenu() {
	  alterVisibility(false, menuItemTaskPlay, menuItemTaskPause, menuItemTaskStopp, menuItemTaskNew, menuItemUrgencySettings);
  }
  
  /**
   * Handles the active lock state
   */
  private void processActiveLock() {
	  alterVisibility(false, menuItemTaskPlay, menuItemTaskPause, menuItemTaskStopp, menuItemTaskNew, menuItemMapView, menuItemTaskList, menuItemUrgencySettings, menuItemLock, menuItemHelp);
	  alterVisibility(true, menuItemUnlock);
  }
  
  /**
   * Handles the inactive lock state
   */
  private void processInactiveLock() {
	  alterVisibility(false, menuItemUnlock);
	  alterVisibility(true, menuItemTaskPlay, menuItemTaskPause, menuItemTaskStopp, menuItemTaskNew, menuItemMapView, menuItemTaskList, menuItemUrgencySettings, menuItemLock, menuItemHelp);
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
}

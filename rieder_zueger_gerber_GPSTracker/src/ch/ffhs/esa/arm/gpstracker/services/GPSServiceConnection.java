package ch.ffhs.esa.arm.gpstracker.services;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * Implementation of the ServiceConnection Interface for the geoPositionService.
 * @author roger
 *
 */
public class GPSServiceConnection implements ServiceConnection {
	private static final String LOG = "ch.ffhs.esa.arm.gpstracker.services";
	private IGeoPositionServiceRemote geoPositionService;
	
	@Override
	public void onServiceConnected(ComponentName className, IBinder binder) {
	  setGeoPositionService(IGeoPositionServiceRemote.Stub.asInterface(binder));
	  Log.i(LOG, "GeoPositionService connected");
	}
	
	@Override
	public void onServiceDisconnected(ComponentName className) {
		Log.i(LOG, "GeoPositionService disconnected");
	}

	public IGeoPositionServiceRemote getGeoPositionService() {
		return geoPositionService;
	}

	public void setGeoPositionService(IGeoPositionServiceRemote geoPositionService) {
		this.geoPositionService = geoPositionService;
	}
}

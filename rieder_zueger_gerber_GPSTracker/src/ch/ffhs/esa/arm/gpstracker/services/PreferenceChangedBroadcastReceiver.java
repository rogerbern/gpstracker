package ch.ffhs.esa.arm.gpstracker.services;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class PreferenceChangedBroadcastReceiver extends BroadcastReceiver {
	private static final String LOG = "ch.ffhs.esa.arm.gpstracker.services.PreferenceChangedBroadcasterReceiver";
	private IGeoPositionServiceRemote geoPositionService;

	private ServiceConnection gpsServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
		  geoPositionService = IGeoPositionServiceRemote.Stub.asInterface(binder);
		  Log.i(LOG, "GeoPositionService connected");
		}
		
		@Override
		public void onServiceDisconnected(ComponentName className) {
			Log.i(LOG, "GeoPositionService disconnected");
		}
	};
	
	@Override
	public void onReceive(Context context, Intent arg1) {
		final Intent intent = new Intent(context, GeoPositionServiceRemote.class);
    	context.bindService(intent, gpsServiceConnection, Context.BIND_AUTO_CREATE);
    	if (geoPositionService != null) {
    		try {
				Log.i(LOG, geoPositionService.start());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	}
	}

}

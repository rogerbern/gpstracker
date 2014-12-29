package ch.ffhs.esa.arm.gpstracker;
  
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 
 * @author andreas
 */

public class TrackingListAdapter extends ArrayAdapter<TrackingItem> {

	private List<TrackingItem> items;
	private int layoutResourceId;
	private Context context;

	public TrackingListAdapter(Context context, int layoutResourceId, List<TrackingItem> items) {
		super(context, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TrackingItemHolder holder = new TrackingItemHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View row = inflater.inflate(layoutResourceId, parent, false);

		holder.trackingItem = items.get(position);
		
		holder.removeButton = (ImageButton)row.findViewById(R.id.trackingItem_removeItemButton);
		holder.removeButton.setTag(holder.trackingItem);
		
		holder.showMapButton = (ImageButton)row.findViewById(R.id.trackingItem_showMapButton);
		holder.showMapButton.setTag(holder.trackingItem);
		
		holder.name = (TextView)row.findViewById(R.id.tracking_name);
		row.setTag(holder);
		holder.name.setText(holder.trackingItem.getName());
		
		return row;
	}

	public static class TrackingItemHolder {
		TrackingItem trackingItem;
		TextView name;
		ImageButton showMapButton;
		ImageButton removeButton;
	}
}

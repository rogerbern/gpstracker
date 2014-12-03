package ch.ffhs.esa.arm.gpstracker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 
 * @author roger
 * @changed marc (table creation deactivated)
 *
 */
public class TrackingListActivity extends BaseActivity {
	private static String TRACKING_TITLE = "Name";
	private static String VIEWMAP_TITLE = "View Map";
	private static String DELETE_TITLE = "Delete";
	private static String VIEW_TEXT = "view";
	private static String DELETE_TEXT = "delete";
	String trackingNames[] = {"Gurten", "Stockhorn", "Niesen"};
	TableLayout tl;
	TableRow tr;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_list);
        
        //tl = (TableLayout) findViewById(R.id.tracking_table_tl);
        //addHeaders();
        //addData();
    }
	
	@Override
	public void onResume() {
	  super.onResume();
	  // TODO: laden der Trackingdaten aus der Datenbank und darstellung in einer ListView
	}
	
	private void addHeaders() {
		tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		tr.addView(configureHeaderCell(TRACKING_TITLE));
		tr.addView(configureHeaderCell(VIEWMAP_TITLE));
		tr.addView(configureHeaderCell(DELETE_TITLE));
        
		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	}
	
	private TextView configureHeaderCell(String title) {
		TextView tw = new TextView(this);
		tw.setText(title);
		tw.setTextColor(Color.GRAY);
		tw.setTypeface(Typeface.DEFAULT);
		tw.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)); 
		tw.setPadding(5, 5, 5, 5);
		
		return tw;
	}
	
	private TextView configureDataCell(String title) {
		TextView tw = new TextView(this);
		tw.setText(title);
		tw.setTextColor(Color.BLACK);
		tw.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		tw.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)); 
		tw.setPadding(5, 5, 5, 0);
		
		return tw;
	}
	
	private void addData() {
		for (String element : trackingNames) {
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			tr.addView(configureDataCell(element));
			tr.addView(configureDataCell(VIEW_TEXT));
			tr.addView(configureDataCell(DELETE_TEXT));
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}
}

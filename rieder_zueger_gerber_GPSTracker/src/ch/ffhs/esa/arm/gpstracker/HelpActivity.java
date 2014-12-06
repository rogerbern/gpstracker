package ch.ffhs.esa.arm.gpstracker;
 
import java.text.DateFormat;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import ch.ffhs.esa.arm.gpstracker.sqlitedb.TrackingTbl;

/** 
 * 
 * @author marc
 * Help-Themen werden dargestellt
 */
public class HelpActivity extends Activity {
	TextView help_txt_1;
	TextView help_txt_2;
	TextView help_txt_3;
	TextView help_txt_4;
	TextView help_txt_5;
	TextView help_txt_6;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        hideHelpTxt();
    }
    
    // Hide all Text on first Load, only Buttons are shown
    public void hideHelpTxt(){
    	help_txt_1 = (TextView) findViewById(R.id.help_txt_1);
        help_txt_2 = (TextView) findViewById(R.id.help_txt_2);
        help_txt_3 = (TextView) findViewById(R.id.help_txt_3);
        help_txt_4 = (TextView) findViewById(R.id.help_txt_4);
        help_txt_5 = (TextView) findViewById(R.id.help_txt_5);
        help_txt_6 = (TextView) findViewById(R.id.help_txt_6);
        
        // hide until its title is clicked
        help_txt_1.setVisibility(View.GONE);
        help_txt_2.setVisibility(View.GONE);
        help_txt_3.setVisibility(View.GONE);
        help_txt_4.setVisibility(View.GONE);
        help_txt_5.setVisibility(View.GONE);
        help_txt_6.setVisibility(View.GONE);
    }
    

    /**
    * onClick handler
    * Identifies wich Button is clicked
    */
    public void toggle_contents(View v){
      switch (v.getId()){
      case R.id.help_title_1:
    	  showOrHide(help_txt_1);
    	  break;
      case R.id.help_title_2:
    	  showOrHide(help_txt_2);
    	  break;
      case R.id.help_title_3:
    	  showOrHide(help_txt_3);
    	  break;
      case R.id.help_title_4:
    	  showOrHide(help_txt_4);
    	  break;
      case R.id.help_title_5:
    	  showOrHide(help_txt_5);
    	  break;
      case R.id.help_title_6:
    	  showOrHide(help_txt_6);
    	  break;
      }
    }
    
    // Hide or Show Help-Text
    public void showOrHide(TextView tv){
    	if(tv.isShown()){
            slide_up(this, tv);
            tv.setVisibility(View.GONE);
          }
          else{
            tv.setVisibility(View.VISIBLE);
            slide_down(this, tv);
          }
    }

    // Load the animation to show the Text
    public static void slide_down(Context ctx, View v){
    	  Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
    	  if(a != null){
    	     a.reset();
    	     if(v != null){
    	      v.clearAnimation();
    	      v.startAnimation(a);
    	     }
    	  }
    }
    
    // Not used for the moment
    public static void slide_up(Context ctx, View v){
  	  Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
  	  if(a != null){
  	     a.reset();
  	     if(v != null){
  	      v.clearAnimation();
  	      //v.startAnimation(a);
  	     }
  	  }
  }
    
}

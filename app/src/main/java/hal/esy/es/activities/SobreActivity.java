package hal.esy.es.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import hal.esy.es.layouts.SobreLayout;


public class SobreActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SobreLayout layout = new SobreLayout(this);
		this.setContentView(layout);
	}
}

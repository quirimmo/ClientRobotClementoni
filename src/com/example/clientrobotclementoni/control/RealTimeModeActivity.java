package com.example.clientrobotclementoni.control;

import com.example.clientrobotclementoni.R;
import com.example.clientrobotclementoni.R.id;
import com.example.clientrobotclementoni.R.layout;
import com.example.clientrobotclementoni.R.menu;
import com.example.clientrobotclementoni.service.robot.RobotProxy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RealTimeModeActivity extends Activity {
	
	
	protected Button buttonRealTimeBackward, buttonRealTimeForward, buttonRealTimeLeft, buttonRealTimeRight;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_real_time_mode);
		
		this.initView();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.real_time_mode, menu);
		return true;
	}
	
	
	private void initView(){
		
		this.buttonRealTimeBackward= (Button) this.findViewById(R.id.buttonRealTimeBackward);
		this.buttonRealTimeForward= (Button) this.findViewById(R.id.buttonRealTimeForward);
		this.buttonRealTimeLeft= (Button) this.findViewById(R.id.buttonRealTimeLeft);
		this.buttonRealTimeRight= (Button) this.findViewById(R.id.buttonRealTimeRight);
		
		this.buttonRealTimeBackward.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		this.buttonRealTimeForward.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RobotProxy.getInstance().moveForward();
			}
		});
		this.buttonRealTimeLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		this.buttonRealTimeRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

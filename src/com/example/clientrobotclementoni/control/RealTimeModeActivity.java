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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RealTimeModeActivity extends Activity {
	
	
	protected Button buttonRealTimeBackward, buttonRealTimeForward, buttonRealTimeLeft, buttonRealTimeRight, buttonRealTimePlaySound;
	protected Spinner spinnerRealTimeSpeed, spinnerRealTimeLedMode, spinnerRealTimeSound;
	protected int realTimeSpeed= 3;
	protected int realTimeLedMode= 0;
	protected int realTimeSound= 0;
	
	

	
	
	
	private void initView(){
		
		this.buttonRealTimeBackward= (Button) this.findViewById(R.id.buttonRealTimeBackward);
		this.buttonRealTimeForward= (Button) this.findViewById(R.id.buttonRealTimeForward);
		this.buttonRealTimeLeft= (Button) this.findViewById(R.id.buttonRealTimeLeft);
		this.buttonRealTimeRight= (Button) this.findViewById(R.id.buttonRealTimeRight);
		
		
		//FORWARD
		this.buttonRealTimeForward.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					RobotProxy.getInstance().moveForward(realTimeSpeed);
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					RobotProxy.getInstance().stopMove();
					return true;
				}
				return true;
			}
		});

		//BACKWARD
		this.buttonRealTimeBackward.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if(event.getAction()==MotionEvent.ACTION_DOWN){
					RobotProxy.getInstance().moveBackward(realTimeSpeed);
					return true;
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					RobotProxy.getInstance().stopMove();
					return true;
				}
				return true;
			}
		});

		//LEFT
		this.buttonRealTimeLeft.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if(event.getAction()==MotionEvent.ACTION_DOWN){
					RobotProxy.getInstance().moveLeft(realTimeSpeed);
					return true;
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					RobotProxy.getInstance().stopMove();
					return true;
				}
				return true;
			}
		});

		//RIGHT
		this.buttonRealTimeRight.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if(event.getAction()==MotionEvent.ACTION_DOWN){
					RobotProxy.getInstance().moveRight(realTimeSpeed);
					return true;
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					RobotProxy.getInstance().stopMove();
					return true;
				}
				return true;
			}
		});
		

		this.spinnerRealTimeSpeed= (Spinner) this.findViewById(R.id.spinnerRealTimeSpeed);
		ArrayAdapter<String> adapterSpinnerSpeed= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, new String[]{"0","1","2","3"});
		adapterSpinnerSpeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinnerRealTimeSpeed.setAdapter(adapterSpinnerSpeed);
		this.spinnerRealTimeSpeed.setSelection(3);
		this.spinnerRealTimeSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	realTimeSpeed= Integer.parseInt(spinnerRealTimeSpeed.getSelectedItem().toString());
			    }
			    @Override
			    public void onNothingSelected(AdapterView<?> parentView) {
			    	realTimeSpeed= Integer.parseInt(spinnerRealTimeSpeed.getSelectedItem().toString());
			    }
		});
		
		
		
		
		
		this.spinnerRealTimeLedMode= (Spinner) this.findViewById(R.id.spinnerRealTimeLedMode);
		ArrayAdapter<String> adapterSpinnerLedMode= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, new String[]{"0","1","2","3"});
		adapterSpinnerLedMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinnerRealTimeLedMode.setAdapter(adapterSpinnerLedMode);
		this.spinnerRealTimeLedMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	realTimeLedMode= Integer.parseInt(spinnerRealTimeLedMode.getSelectedItem().toString());
//			    	RobotProxy.getInstance().switchLedMode(realTimeLedMode);
			    }
			    @Override
			    public void onNothingSelected(AdapterView<?> parentView) {
			    	realTimeLedMode= Integer.parseInt(spinnerRealTimeLedMode.getSelectedItem().toString());
			    }
		});
		
		
		
		
		
		
		this.spinnerRealTimeSound= (Spinner) this.findViewById(R.id.spinnerRealTimeSound);
		ArrayAdapter<String> adapterSpinnerSound= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, new String[]{"1", "2","3","4","5",
                "6", "7","8","9","10",
                "11","12","13","14","15",
                "16","17","18","19","20",
                "21","22","23","24","25",
                "26","27","28","29","30"});
		adapterSpinnerSound.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinnerRealTimeSound.setAdapter(adapterSpinnerSound);
		this.spinnerRealTimeSound.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	realTimeSound= Integer.parseInt(spinnerRealTimeSound.getSelectedItem().toString())-1;
			    }
			    @Override
			    public void onNothingSelected(AdapterView<?> parentView) {
			    	realTimeSound= Integer.parseInt(spinnerRealTimeSound.getSelectedItem().toString())-1;
			    }
		});
		
		this.buttonRealTimePlaySound= (Button) this.findViewById(R.id.buttonRealTimePlaySound);
		this.buttonRealTimePlaySound.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//RobotProxy.getInstance().playSound(realTimeSound);
			}
		});
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.real_time_mode, menu);
		return true;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_real_time_mode);
		
		this.initView();
		
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

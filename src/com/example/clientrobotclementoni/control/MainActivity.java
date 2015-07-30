package com.example.clientrobotclementoni.control;


import com.example.clientrobotclementoni.R;
import com.example.clientrobotclementoni.service.RobotProxy;
import com.example.clientrobotclementoni.service.RobotProxy.DIRECTIONS;
import com.example.clientrobotclementoni.utility.DeviceUtility;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	protected Button buttonOpenBluetoothConnection, buttonCloseBluetoothConnection, buttonConnectToRobot, buttonTest;
	 static boolean send_thread = false;
		
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.initRobotApplication();
		
	}
	
	
	private void initRobotApplication(){
		
		if(RobotProxy.getInstance().checkBluetoothSupport(this) == null){
			this.finish();
			return;
		}
		else{
			RobotProxy.getInstance().enableBluetooth();
			this.setupMainPageWidgets();
		}
		
	}
	
	
	private void setupMainPageWidgets(){
		
		this.buttonOpenBluetoothConnection= (Button) this.findViewById(R.id.buttonOpenBluetoothConnection);
		this.buttonCloseBluetoothConnection= (Button) this.findViewById(R.id.buttonCloseBluetoothConnection);
		this.buttonConnectToRobot= (Button) this.findViewById(R.id.buttonConnectToRobot);
		this.buttonTest= (Button) this.findViewById(R.id.buttonTest);
		final MainActivity instance= this;
		
		this.buttonOpenBluetoothConnection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RobotProxy.getInstance().scanningCheck(instance, false);
			}
		});
		this.buttonConnectToRobot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RobotProxy.getInstance().connect(instance);
			}
		});

		
		this.buttonTest.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					send_thread = true;
					return true;
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					send_thread = false;
					return true;
				}
				

				
				return true;
				
			}
		});	
		
		this.buttonCloseBluetoothConnection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RobotProxy.getInstance().disconnect(instance);
			}
		});
		
		Thread loggingThread = new Thread(new Runnable(){
		     public void run(){
		    	 while(1==1){
		    		 if(RobotProxy.getInstance().isConnected()){
			    		 if(send_thread){
				        	 RobotProxy.getInstance().sendCommand(instance, RobotProxy.getInstance().createMotionControl(DIRECTIONS.UP, 0));
				         }
				         else{
				        	 RobotProxy.getInstance().sendCommand(instance, RobotProxy.getInstance().createMotionControl(DIRECTIONS.STOP, 4));
				         }
			    	 }

			    	 try {
			        	 Thread.sleep(150);
			    	 } catch (InterruptedException e) {
			    		 // TODO Auto-generated catch block
			    		 e.printStackTrace();
			    	 }
		    	 }
		     }
		});
		loggingThread.start();
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}

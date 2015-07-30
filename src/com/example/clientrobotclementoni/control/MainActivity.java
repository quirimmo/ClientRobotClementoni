package com.example.clientrobotclementoni.control;


import com.example.clientrobotclementoni.R;
import com.example.clientrobotclementoni.service.robot.RobotProxy;
import com.example.clientrobotclementoni.utility.DeviceUtility;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	protected Button buttonOpenBluetoothConnection, buttonCloseBluetoothConnection, buttonConnectToRobot, buttonRealTimeMode,
					buttonAutoconnectScanning;
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
	
	
	
	protected Thread motionThread;
	
	private void setupMainPageWidgets(){
		
		this.buttonOpenBluetoothConnection= (Button) this.findViewById(R.id.buttonOpenBluetoothConnection);
		this.buttonCloseBluetoothConnection= (Button) this.findViewById(R.id.buttonCloseBluetoothConnection);
		this.buttonConnectToRobot= (Button) this.findViewById(R.id.buttonConnectToRobot);
		this.buttonAutoconnectScanning= (Button) this.findViewById(R.id.buttonAutoconnectScanning);
		this.buttonRealTimeMode= (Button) this.findViewById(R.id.buttonRealTimeMode);
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

		this.buttonAutoconnectScanning.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RobotProxy.getInstance().scanningCheck(instance, true);
			}
		});

		this.buttonCloseBluetoothConnection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RobotProxy.getInstance().disconnect();
			}
		});
		
		this.buttonRealTimeMode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(instance, RealTimeModeActivity.class);
				startActivity(myIntent);
			}
		});
		
		
		
		
		/*
		this.buttonTest.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, final MotionEvent event) {
				
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					RobotProxy.getInstance().getWriteCharacteristic().setValue("12D2S3");
					RobotProxy.getInstance().getBluetoothLowEnergyClass().writeCharacteristic(RobotProxy.getInstance().getWriteCharacteristic());
				}
				if(event.getAction() == MotionEvent.ACTION_UP){
					RobotProxy.getInstance().getWriteCharacteristic().setValue("12D5S3");
					RobotProxy.getInstance().getBluetoothLowEnergyClass().writeCharacteristic(RobotProxy.getInstance().getWriteCharacteristic());
				}
				
				return true;
				
			}
		});	
		*/
		

		/*
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
		*/
		
		
		
		
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

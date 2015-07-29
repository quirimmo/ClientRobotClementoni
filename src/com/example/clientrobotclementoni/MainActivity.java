package com.example.clientrobotclementoni;


import com.example.clientrobotclementoni.LeDeviceListAdapter;
import com.example.clientrobotclementoni.BluetoothLeClass;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected Button buttonOpenBluetoothConnection;
	
	protected BluetoothLeClass mBLE;
    protected BluetoothDevice ble_device=null; 
    protected LeDeviceListAdapter mLeDeviceListAdapter;
    protected int selIndex=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initRobotApplication();
		
	}
	
	
	private void initRobotApplication(){
		this.buttonOpenBluetoothConnection= (Button) this.findViewById(R.id.buttonOpenBluetoothConnection);
		final MainActivity instance= this;
		this.buttonOpenBluetoothConnection.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "OPENING SOCKET CONNECTION", Toast.LENGTH_SHORT).show();
				
				mBLE = new BluetoothLeClass(instance);
		        if (!mBLE.initialize()) {
		            Toast.makeText(getApplicationContext(), "ERROR CAN'T OPEN SOCKET CONNECTION", Toast.LENGTH_SHORT).show();
					
		            finish();
		        }
		        
		        BluetoothLeClass.OnConnectListener mConnectListener = new BluetoothLeClass.OnConnectListener() {
		            @Override
		            public void onConnect(BluetoothGatt gatt) {
		            	Toast.makeText(getApplicationContext(), "CONNECTED TO GATT", Toast.LENGTH_SHORT).show();
		            }
		        };
		        
		        mBLE.setOnConnectListener(mConnectListener);
		        
		        mLeDeviceListAdapter = new LeDeviceListAdapter(instance);
		        
		        ble_device = mLeDeviceListAdapter.getDevice(selIndex);
		        
		        if (ble_device != null) {
		            mBLE.connect(ble_device.getAddress());
		        }
		        
		        
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

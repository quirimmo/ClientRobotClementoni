package com.example.clientrobotclementoni.service;

import java.util.ArrayList;
import java.util.List;

import com.example.clientrobotclementoni.R;
import com.example.clientrobotclementoni.model.RobotConfiguration;
import com.example.clientrobotclementoni.service.bluetooth.BluetoothLeClass;
import com.example.clientrobotclementoni.utility.DeviceUtility;
import com.example.clientrobotclementoni.utility.Utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;

public class RobotProxy {
	
	private static RobotProxy instance= null;
	
	protected boolean connected= false, found= false, scanning= false;
	
	protected Handler handler= new Handler();
	
	protected BluetoothAdapter bluetoothAdapter;
    protected BluetoothLeClass bluetoothLowEnergyClass;
	
	protected String address;
	protected List<BluetoothDevice> devices= new ArrayList<BluetoothDevice>();
	
	
	private RobotProxy(){
		
	}
	
	public static RobotProxy getInstance(){
		if(instance==null)
			instance= new RobotProxy();
		return instance;
	}

	
	
	
	
	public BluetoothAdapter checkBluetoothSupport(Activity activity){
		
		final BluetoothManager bluetoothManager = (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
		this.bluetoothAdapter = bluetoothManager.getAdapter();
        if (this.bluetoothAdapter == null) {
        	DeviceUtility.log(activity, activity.getResources().getString(R.string.error_bluetooth_not_supported));
            return null;
        }
		
		return bluetoothAdapter;
	}
	
	
	public void enableBluetooth(){
    	if(!bluetoothAdapter.isEnabled()){
    		bluetoothAdapter.enable();
		}
	}
	
	

	
	
	@SuppressWarnings("deprecation")
	public void startScanning(final Activity activity, final boolean autoConnect){
		bluetoothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
	        @Override
	        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
	        	if( device.getName().equals("Hyber Robot") ){
	        		devices.add(device);
	        		found= true;
	        		DeviceUtility.log(activity, "Robot Found");
	        		if(autoConnect)
	        			connect(activity);
	        	}
	        }
	    });
	}
	
	@SuppressWarnings("deprecation")
	public void stopScanning(final Activity activity){
		bluetoothAdapter.stopLeScan(new BluetoothAdapter.LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        		DeviceUtility.log(activity, "Scanning Stopped");
			}
		});
	}
	
	
	
	
	public boolean scanningCheck(final Activity activity, final boolean autoConnect){
		this.scanning= true;
		
		this.handler= new Handler();
		this.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scanning = false;
                stopScanning(activity);
            }
        }, RobotConfiguration.SCANNING_DURATION);
		
		
		this.startScanning(activity, autoConnect);
		
		return this.found;
	}
	
	
	
	public void connect(final Activity activity){
		
		
		if(this.devices.isEmpty()){
			DeviceUtility.log(activity, "No BLE Device");
			return;
		}
		
		if(this.scanning){
			this.stopScanning(activity);
			this.scanning= false;
		}
	
		
		this.bluetoothLowEnergyClass= new BluetoothLeClass(activity);
		if (!this.bluetoothLowEnergyClass.initialize()) {
			DeviceUtility.log(activity, "Unable to initialize Bluetooth");
            activity.finish();
            return;
        }
		this.bluetoothLowEnergyClass.setOnConnectListener(new BluetoothLeClass.OnConnectListener() {
			@Override
			public void onConnect(BluetoothGatt gatt) {
				DeviceUtility.log(activity, "CONNECTED TO ROBOT!!!!");
				connected= true;
			}
		}); 
		this.bluetoothLowEnergyClass.setOnDisconnectListener(new BluetoothLeClass.OnDisconnectListener() {
			@Override
			public void onDisconnect(BluetoothGatt gatt) {
				DeviceUtility.log(activity, "DISCONNECTED FROM ROBOT!!!!");
			}
		}); 
		this.bluetoothLowEnergyClass.setOnServiceDiscoverListener(new BluetoothLeClass.OnServiceDiscoverListener() {
			@Override
			public void onServiceDiscover(BluetoothGatt gatt) {
				DeviceUtility.log(activity, "CAZZATA");
			}
		});

		
		
		
		this.bluetoothLowEnergyClass.connect(devices.get(0).getAddress());
	}
	
	public void connect2(Activity activity){
		this.bluetoothLowEnergyClass.connect(devices.get(0).getAddress());
		DeviceUtility.printDeviceInformations(devices.get(0), activity);
	}
	
	
	BluetoothGattCharacteristic TEST_gattCharacteristic=null;
	public void test(Activity activity){
		if(TEST_gattCharacteristic!=null){
			String full2= createMotionControl(DIRECTIONS.UP, 8);
			TEST_gattCharacteristic.setValue(full2);
            this.bluetoothLowEnergyClass.writeCharacteristic(TEST_gattCharacteristic);
            DeviceUtility.log(activity, "=Running...");
		}else{
			List<BluetoothGattService> lis= this.bluetoothLowEnergyClass.getSupportedGattServices();
			DeviceUtility.log(activity, "SERVICES NUMBER: " + lis.size());
			for(BluetoothGattService service: lis){
				DeviceUtility.log(activity, "=============================================================================");
				DeviceUtility.log(activity, service.getUuid().toString());
				for (final BluetoothGattCharacteristic gattCharacteristic: service.getCharacteristics() ) {
					if(gattCharacteristic.getUuid().toString().equals("0000fff5-0000-1000-8000-00805f9b34fb")){
						TEST_gattCharacteristic = gattCharacteristic;
	//					DeviceUtility.log(activity, "ENTRATO");
	//					String temp ="78";
	//					String BTSendData = temp+0+'E';
	//					gattCharacteristic.setValue(BTSendData);
	//					this.bluetoothLowEnergyClass.writeCharacteristic(gattCharacteristic);
					/*	int R_Back_Data  = 1;  
						String R_Data  = 'D'+String.valueOf(R_Back_Data);
	                    String G_Data  = 'S'+String.valueOf(1-1);
	                    int Cmd_Type = 1;
	                    String full= "12" + R_Data + G_Data;
	                    String full2= createMotionControl(DIRECTIONS.UP, 8);
	                    gattCharacteristic.setValue(full2);
	                    this.bluetoothLowEnergyClass.writeCharacteristic(gattCharacteristic);*/
					}
					DeviceUtility.log(activity, gattCharacteristic.getUuid().toString());
					DeviceUtility.log(activity, "ROBA: " + Utils.getCharPropertie(gattCharacteristic.getProperties()));
					DeviceUtility.log(activity, "------------------------------------");
				}
			}
		}
		
		
	}
	
	public void sendCommand(Activity activity, String command){
		if(TEST_gattCharacteristic==null){
			test(activity);
		}
		if(TEST_gattCharacteristic==null){
			return;
		}
		TEST_gattCharacteristic.setValue(command);
        this.bluetoothLowEnergyClass.writeCharacteristic(TEST_gattCharacteristic);
	}
	
	
	public enum DIRECTIONS {
		UP(0),
		DOWN(1),
		LEFT(2),
		RIGHT(3),
		STOP(5);
		
		private int v;
		DIRECTIONS(int i ){
			v=i;
		}
		
		int getValue(){
			return v;
		}
		
	};
	
	
	
	public String createMotionControl(DIRECTIONS direction, int speed){
		return "12" + "D" + direction.getValue() + "S" + speed;
	}
	
	
	
	public void disconnect(Activity activity){
		this.bluetoothLowEnergyClass.disconnect();
		DeviceUtility.log(activity, "Disconnected");
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	
	
}

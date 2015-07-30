package com.example.clientrobotclementoni.service.robot;

import java.util.ArrayList;
import java.util.List;

import com.example.clientrobotclementoni.R;
import com.example.clientrobotclementoni.model.RobotConfiguration;
import com.example.clientrobotclementoni.service.bluetooth.BluetoothLeClass;
import com.example.clientrobotclementoni.service.robot.RobotCommand.MovementType;
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
	
	protected List<BluetoothGattService> bluetoothServices= new ArrayList<BluetoothGattService>();
	protected BluetoothGattCharacteristic writeCharacteristic;
	
	
	
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
        	DeviceUtility.log(activity.getResources().getString(R.string.error_bluetooth_not_supported));
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
	        		DeviceUtility.log("Robot Found");
	        		if(autoConnect)
	        			connect(activity);
	        	}
	        }
	    });
	}
	
	@SuppressWarnings("deprecation")
	public void stopScanning(){
		bluetoothAdapter.stopLeScan(new BluetoothAdapter.LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        		DeviceUtility.log("ON LE SCAN CALLBACK");
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
                stopScanning();
            }
        }, RobotConfiguration.SCANNING_DURATION);
		
		
		this.startScanning(activity, autoConnect);
		
		return this.found;
	}
	
	
	
	public void connect(final Activity activity){
		
		
		if(this.devices.isEmpty()){
			DeviceUtility.log("No BLE Device");
			return;
		}
		
		if(this.scanning){
			this.stopScanning();
			this.scanning= false;
		}
	
		
		this.bluetoothLowEnergyClass= new BluetoothLeClass(activity);
		if (!this.bluetoothLowEnergyClass.initialize()) {
			DeviceUtility.log("Unable to initialize Bluetooth");
            activity.finish();
            return;
        }
		
		this.attachListeners();
		
		this.bluetoothLowEnergyClass.connect(devices.get(0).getAddress());
	}
	
	
	
	
	private void attachListeners(){
		
		this.bluetoothLowEnergyClass.setOnConnectListener(new BluetoothLeClass.OnConnectListener() {
			@Override
			public void onConnect(BluetoothGatt gatt) {
				DeviceUtility.log("CONNECTED TO ROBOT!!!!");
				connected= true;
			}
		}); 
		this.bluetoothLowEnergyClass.setOnDisconnectListener(new BluetoothLeClass.OnDisconnectListener() {
			@Override
			public void onDisconnect(BluetoothGatt gatt) {
				DeviceUtility.log("DISCONNECTED FROM ROBOT!!!!");
				connected= false;
			}
		}); 
		this.bluetoothLowEnergyClass.setOnServiceDiscoverListener(new BluetoothLeClass.OnServiceDiscoverListener() {
			@Override
			public void onServiceDiscover(BluetoothGatt gatt) {
				bluetoothServices=gatt.getServices();
				DeviceUtility.log("DISCOVERED " + bluetoothServices.size() + " SERVICES");
				for(BluetoothGattService service: bluetoothServices){
					DeviceUtility.printService(service);
					for (final BluetoothGattCharacteristic gattCharacteristic: service.getCharacteristics() ) {
						DeviceUtility.printGattCharacteristic(gattCharacteristic);
						if(gattCharacteristic.getUuid().toString().equals(RobotConfiguration.UUID_WRITE_DATA))
							writeCharacteristic= gattCharacteristic;
					}
				}
			}
		});
		
	}
	
	
	public void sendCommand(String command){
		this.writeCharacteristic.setValue(command);
		this.bluetoothLowEnergyClass.writeCharacteristic(this.writeCharacteristic);
	}
	
	
	
	public void moveForward(int speed){
		this.sendCommand(RobotCommand.generateMovementCommand(MovementType.UP, speed).toString());
	}
	public void moveForward(){
		this.sendCommand(RobotCommand.generateMovementCommand(MovementType.UP, 3).toString());
	}
	
	
	
	public BluetoothGattCharacteristic getWriteCharacteristic() {
		return writeCharacteristic;
	}

	public void setWriteCharacteristic(BluetoothGattCharacteristic writeCharacteristic) {
		this.writeCharacteristic = writeCharacteristic;
	}

	public void disconnect(){
		this.bluetoothLowEnergyClass.disconnect();
		DeviceUtility.log("Disconnected");
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public BluetoothLeClass getBluetoothLowEnergyClass() {
		return bluetoothLowEnergyClass;
	}

	public void setBluetoothLowEnergyClass(BluetoothLeClass bluetoothLowEnergyClass) {
		this.bluetoothLowEnergyClass = bluetoothLowEnergyClass;
	}
	
	
}

package com.example.clientrobotclementoni.utility;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

public class DeviceUtility {
	
	
	public static void log(String tag, String msg){
		Log.d(tag, msg);
		System.out.println(tag + " - " + msg);
	}
	public static void log(String msg){
		log("RobotClementoni", msg);
	}
	
	
	public static void printDeviceInformations(BluetoothDevice device){
		log("NAME: " + device.getName() + " - ADDRESS: " + device.getAddress());
	}
	
	
	
	public static void printGattCharacteristic(BluetoothGattCharacteristic gattCharacteristic){
		DeviceUtility.log("CHARACTERISTIC UUID: " + gattCharacteristic.getUuid().toString());
		DeviceUtility.log("PROPERTY: " + Utils.getCharPropertie(gattCharacteristic.getProperties()));
		DeviceUtility.log("------------------------------------");
	}
	
	public static void printService(BluetoothGattService service){
		DeviceUtility.log("========================================================");
		DeviceUtility.log("SERVICE UUID: " + service.getUuid().toString());
		DeviceUtility.log("SERVICE CHARACTERISTIC: " + service.getCharacteristics().size());
	}
	
	
}

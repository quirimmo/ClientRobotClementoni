package com.example.clientrobotclementoni.utility;

import com.example.clientrobotclementoni.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass.Device;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class DeviceUtility {
	
	
	public static void log(Context activity, String tag, String msg){
		Log.d(tag, msg);
		System.out.println(tag + " - " + msg);
	}
	public static void log(Context activity, String msg){
		log(activity, "RobotClementoni", msg);
	}
	
	
	public static void printDeviceInformations(BluetoothDevice device, Context activity){
		log(activity, "NAME: " + device.getName() + " - ADDRESS: " + device.getAddress());
	}
	
	
}

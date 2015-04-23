package com.ceres.jailmon.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class IpUtil {/*
	 * 获取Android设备的IP地址(针对有线联网方式)
	 */
	public static String getIp() {  
		String ipaddress = null;
		try{
			for (Enumeration<NetworkInterface> en = NetworkInterface
				     .getNetworkInterfaces(); en.hasMoreElements();) {
				    NetworkInterface intf = en.nextElement();
				    if (intf.getName().toLowerCase().equals("eth0") || intf.getName().toLowerCase().equals("wlan0")) { 
				    	for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
				    		InetAddress inetAddress = enumIpAddr.nextElement();
				    		if (!inetAddress.isLoopbackAddress()) {
				    		    ipaddress = inetAddress.getHostAddress().toString();
				    			if(!ipaddress.contains("::")){//ipV6的地址
				    				return ipaddress;
				    			}
				    		}
				    	}
				    } else {
				    	continue;
				    }
				}
		}catch(SocketException e){
			e.printStackTrace();
		}
		return ipaddress;
}
}

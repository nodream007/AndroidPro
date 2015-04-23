package com.ceres.jailmon.data;

import com.mm.android.avnetsdk.param.AV_HANDLE;

public class MonitorInfo {
	public String id;
	public String ip;
	public int port;
	public String user;
	public String passwd;
	public String channel;
	public boolean available; 
	public AV_HANDLE dhhandle;
	public boolean logined;
	
	public int loginID = -1;
	public int playID = -1;
	
	@Override
	public String toString() {
		return id;
	}
}

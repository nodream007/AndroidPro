package com.ceres.jailmon;

import com.ceres.jailmon.data.StringResult;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings.Secure;
import android.util.Log;

/**
 * 监听网络变化
 * 
 * @author zhounan1
 * 
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
	
	private static final String TAG = "NetworkConnectChangedReceiver";
	
	private Context mContext;
	
	protected Handler m_handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				break;
			}
		}
	};

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		// 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
		// 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
		// 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo wifi = manager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			Log.i(TAG, "网络状态改变:" + wifi.isConnected());
			NetworkInfo info = intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			if (info != null) {
				Log.e(TAG, "info.getTypeName()" + info.getTypeName());
				Log.e(TAG, "getSubtypeName()" + info.getSubtypeName());
				Log.e(TAG, "getState()" + info.getState());
				Log.e(TAG, "getDetailedState()"
						+ info.getDetailedState().name());
				Log.e(TAG, "getDetailedState()" + info.getExtraInfo());
				Log.e(TAG, "getType()" + info.getType());
				Log.e(TAG, "IP:" + getLocalIpAddress());
				String user = context.getSharedPreferences("LoginActivity", 0).getString("USER", "admin");
				if(wifi.isConnected()) {
					getIPChangeResult(user, getLocalIpAddress(), getDeviceID());
				}
			}
		}
	}

	@SuppressLint("DefaultLocale")
	private String getLocalIpAddress() {
		WifiManager wifiManager = (WifiManager) mContext
				.getSystemService("wifi");
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		// 获取32位整型IP地址
		int ipAddress = wifiInfo.getIpAddress();
		// 返回整型地址转换成“*.*.*.*”地址
		return String.format("%d.%d.%d.%d", (ipAddress & 0xff),
				(ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
				(ipAddress >> 24 & 0xff));
	}

	/**
	 * ip
	 * @param user
	 * @param ip
	 *            本机ip
	 */
	public void getIPChangeResult(final String user, final String ip, final String macaddr) {

		new Thread() {
			public void run() {
				Message msg = new Message();
				ApiClient api = new ApiClient();
				try {
					StringResult info = api.getIPChangeResult(user, ip, macaddr);
					msg.what = 1;
					msg.obj = info;
				} catch (AppException e) {
					e.printStackTrace();
				}
				m_handler.sendMessage(msg);
			}
		}.start();
	}
	
	String getDeviceID() {
		String tmp = Secure.getString(mContext.getContentResolver(),
				Secure.ANDROID_ID);

		return tmp.substring(0, tmp.length() - 1);
	}
}

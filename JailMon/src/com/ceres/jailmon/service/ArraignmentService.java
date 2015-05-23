package com.ceres.jailmon.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import com.ceres.jailmon.OutInfoActivity;
import com.ceres.jailmon.R;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * 提讯功能Service
 * 
 * @author zhounan1
 * 
 */
@SuppressLint("HandlerLeak")
public class ArraignmentService extends Service {

	private static final String TAG = "ArraignmentService";

	public static final String ACTION = "com.ceres.jailmon.service.arraignmentservice";

	private NotificationManager mNotifiManager;

	private DatagramSocket socket;

	// private Notification mNotification;

	public final static int NOTIFICATION_ID = 24352;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ConnentOver:
				Log.e(TAG, "网络服务端链接成功");
				break;
			case SendOver:
				Log.e(TAG, "发送字符成功");
				break;
			case DataComeIn:
				showArraignmentNotification(msg.getData().getString("info"));
				break;

			}
		}
	};

	final static int ConnentOver = 1;
	final static int SendOver = 2;
	final static int DataComeIn = 3;

	private int port = 7000;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "ArraignmentService onCreate");
		super.onCreate();
		mNotifiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		ReceiveServerSocketData();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}

	/**
	 * 接收服务器推送过来提讯信息
	 */
	public void ReceiveServerSocketData() {
		new Thread() {
			public void run() {

				try {
					// 实例化的端口号要和发送时的socket一致，否则收不到data
					socket = new DatagramSocket(port);
					byte data[] = new byte[4 * 1024];
					while (true) {
						// 参数一:要接受的data 参数二：data的长度
						DatagramPacket packet = new DatagramPacket(data,
								data.length);
						socket.receive(packet);
						// 把接收到的data转换为String字符串
						String result = new String(packet.getData(),
								packet.getOffset(), packet.getLength());
						Log.i(TAG, result);
						Message m = new Message();
						m.what = DataComeIn;
						Bundle bundle = new Bundle();
						bundle.putString("info", result);
						m.setData(bundle);
						mHandler.sendMessage(m);
					}

					// socket.close();// 不使用了记得要关闭
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();
	}

	/**
	 * 在通知栏显示推送过来的提讯信息
	 */
	@SuppressWarnings("deprecation")
	private void showArraignmentNotification(String receive) {
		Notification notification = new Notification(
				R.drawable.stat_sys_call_record, receive,
				System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE;
		Intent i = new Intent(ArraignmentService.this, OutInfoActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(this,
				R.string.app_name, i, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, getString(R.string.app_name),
				receive, contentIntent);
		mNotifiManager.notify(NOTIFICATION_ID, notification);
	}
}

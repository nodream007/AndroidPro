/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import org.MediaPlayer.PlayM4.Player;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.ceres.jailmon.adapter.CustomAdapter_MonitorInfo;
import com.ceres.jailmon.data.MonitorInfo;
import com.ceres.jailmon.data.MonitorInfoList;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_CLIENTINFO;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_IPPARACFG_V40;
import com.hikvision.netsdk.NET_DVR_NETCFG_V30;
import com.hikvision.netsdk.PlaybackCallBack;
import com.hikvision.netsdk.RealPlayCallBack;

public class MonitorActivity extends BaseActivity implements Callback {

	final static int LOGIN_MONITOR_UPDATE = 100;

	private ListView m_listViewMonitors;
	private LinearLayout m_layoutMain, m_layoutContent;
	private AnimationUtil m_aniset;

	private Player m_oPlayerSDK = null;

	private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;

	// private int m_iLogID = -1; // return by NET_DVR_Login_v30
	private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
	private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime
	private byte m_byGetFlag = 1; // 1-get net cfg, 0-set net cfg
	private int m_iPort = -1; // play port
	private NET_DVR_NETCFG_V30 NetCfg = new NET_DVR_NETCFG_V30(); // netcfg
																	// struct

	private MonitorInfoList m_milist;

	private final String TAG = "JAILMON";

	private SurfaceView m_osurfaceView = null;

	private boolean bUpdate = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!initeSdk()) {
			this.finish();
			return;
		}

		m_aniset = new AnimationUtil(this);

		setContentView(R.layout.monitor);

		m_layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
		m_layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

		m_listViewMonitors = (ListView) findViewById(R.id.lstMain);

		Button btnBack = (Button) findViewById(R.id.buttonBack);

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int nCount;
				if (m_milist == null || (nCount = m_milist.m_list.size()) == 0)
					return;
				MonitorInfo mi;
				boolean flag = true;
				for (int i = 0; i < nCount; i++) {
					mi = m_milist.m_list.get(i);
					if (mi.loginID == -1) {
						flag = true;
					} else {
						flag = flag && mi.available;
					}
				}
				if (!flag) {
					Toast.makeText(m_AppContext, "正在初始化监控设备，请稍后返回",
							Toast.LENGTH_SHORT).show();
				} else {
					stopPlay();
					Cleanup();
					finish();
				}
			}

		});

		m_listViewMonitors.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				int sel = (int) arg2;

				MonitorInfo mi = m_milist.m_list.get(sel);

				if (mi.available) {
					stop();
					preview(mi);
				}
			}

		});

		m_osurfaceView = (SurfaceView) findViewById(R.id.Sur_Player1);
		m_osurfaceView.getHolder().addCallback(this);

		loadMonitorInfo();
	}

	// @Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i(TAG, "surface is created" + m_iPort);
		if (-1 == m_iPort) {
			return;
		}
		Surface surface = holder.getSurface();
		if (null != m_oPlayerSDK && true == surface.isValid()) {
			if (false == m_oPlayerSDK.setVideoWindow(m_iPort, 0, holder)) {
				Log.e(TAG, "Player setVideoWindow failed!");
			}
		}
	}

	// @Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	// @Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i(TAG, "Player setVideoWindow release!" + m_iPort);
		if (-1 == m_iPort) {
			return;
		}
		if (null != m_oPlayerSDK && true == holder.getSurface().isValid()) {
			if (false == m_oPlayerSDK.setVideoWindow(m_iPort, 0, null)) {
				Log.e(TAG, "Player setVideoWindow failed!");
			}
		}
	}

	CustomAdapter_MonitorInfo m_adapterMonitor;

	private Handler m_handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == API_GET_MONITOR_INFO_LIST_OK) {
				m_milist = (MonitorInfoList) msg.obj;

				if (m_milist != null) {
					m_adapterMonitor = new CustomAdapter_MonitorInfo(
							MonitorActivity.this, m_milist,
							R.drawable.list_icon_monitor);

					m_listViewMonitors.setAdapter(m_adapterMonitor);

					m_layoutMain.startAnimation(m_aniset.m_aniFadeIn);
					m_layoutContent.startAnimation(m_aniset.m_aniFadeIn);

					loginAllMonitor(m_handler);
				}
			} else if (msg.what == API_GET_FAIL) {
				closeProcessDialog();
				AppException exception = (AppException) msg.obj;
				exception.makeToast(MonitorActivity.this);
			} else if (msg.what == LOGIN_MONITOR_UPDATE) {
				if (m_adapterMonitor != null)
					m_adapterMonitor.notifyDataSetChanged();
			}
		}
	};

	private void loadMonitorInfo() {

		// m_layoutConent.setVisibility(View.GONE);

		getMonitorInfoList(m_handler);
	}

	void loginAllMonitor(final Handler handler) {

		new Thread() {
			public void run() {

				int nCount;

				if (m_milist == null || (nCount = m_milist.m_list.size()) == 0)
					return;

				MonitorInfo mi;

				for (int i = 0; i < nCount; i++) {
					mi = m_milist.m_list.get(i);

					if (login(mi)) {
						mi.available = true;
						bUpdate = true;
					}

					mi.available = true;
					bUpdate = true;

					if (i % 5 == 0 || i == nCount - 1) {
						if (bUpdate) {
							Message msg = new Message();
							msg.what = LOGIN_MONITOR_UPDATE;
							handler.sendMessage(msg);
							bUpdate = false;
						}
					}
				}
			}
		}.start();
	}

	/**
	 * @fn initeSdk
	 * @author huyf
	 * @brief SDK init
	 * @param NULL
	 *            [in]
	 * @param NULL
	 *            [out]
	 * @return true - success;false - fail
	 */
	private boolean initeSdk() {
		// get an instance and init net sdk
		/*
		 * m_oHCNetSDK = new HCNetSDK(); if (null == m_oHCNetSDK) { Log.e(TAG,
		 * "m_oHCNetSDK new is failed!"); return false; }
		 */

		if (!HCNetSDK.getInstance().NET_DVR_Init()) {
			Log.e(TAG, "HCNetSDK init is failed!");
			return false;
		}
		HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/sdcard/sdklog/", true);

		// init player
		m_oPlayerSDK = Player.getInstance();
		if (m_oPlayerSDK == null) {
			Log.e(TAG, "PlayCtrl getInstance failed!");
			return false;
		}

		return true;
	}

	/*
	 * private void playback() { try { if (m_iLogID < 0) { Log.e(TAG,
	 * "please login on device first"); return; }
	 * 
	 * if (m_iPlaybackID < 0) { if (m_iPlayID >= 0) { Log.i(TAG,
	 * "Please stop preview first"); return; } PlaybackCallBack
	 * fPlaybackCallBack =
	 * getPlayerbackPlayerCbf(m_osurfaceViews[m_current_sv]); if
	 * (fPlaybackCallBack == null) { Log.e(TAG,
	 * "fPlaybackCallBack object is failed!"); return; } NET_DVR_TIME struBegin
	 * = new NET_DVR_TIME(); NET_DVR_TIME struEnd = new NET_DVR_TIME();
	 * 
	 * struBegin.dwYear = 2012; struBegin.dwMonth = 6; struBegin.dwDay = 14;
	 * struBegin.dwHour = 9; struBegin.dwMinute = 0; struBegin.dwSecond = 0;
	 * 
	 * struEnd.dwYear = 2012; struEnd.dwMonth = 6; struEnd.dwDay = 14;
	 * struEnd.dwHour = 17; struEnd.dwMinute = 10; struEnd.dwSecond = 0;
	 * 
	 * m_iPlaybackID = m_oHCNetSDK.NET_DVR_PlayBackByTime(m_iLogID, 1,
	 * struBegin, struEnd); if (m_iPlaybackID >= 0) { if
	 * (!m_oHCNetSDK.NET_DVR_SetPlayDataCallBack(m_iPlaybackID,
	 * fPlaybackCallBack)) { Log.e(TAG, "Set playback callback failed!");
	 * return; } NET_DVR_PLAYBACK_INFO struPlaybackInfo = null; if
	 * (!m_oHCNetSDK.NET_DVR_PlayBackControl_V40(m_iPlaybackID,
	 * HCNetSDK.NET_DVR_PLAYSTART, null, 0, struPlaybackInfo)) { Log.e(TAG,
	 * "net sdk playback start failed!"); return; } //
	 * m_oPlaybackBtn.setText("Stop"); } else { Log.i(TAG,
	 * "NET_DVR_PlayBackByTime failed, error code: " +
	 * m_oHCNetSDK.NET_DVR_GetLastError()); } } else { if
	 * (!m_oHCNetSDK.NET_DVR_StopPlayBack(m_iPlaybackID)) { Log.e(TAG,
	 * "net sdk stop playback failed"); } // player stop play if
	 * (!m_oPlayerSDK.stop(m_iPort)) { Log.e(TAG, "player_stop is failed!"); }
	 * if (!m_oPlayerSDK.closeStream(m_iPort)) { Log.e(TAG,
	 * "closeStream is failed!"); } if (!m_oPlayerSDK.freePort(m_iPort)) {
	 * Log.e(TAG, "freePort is failed!" + m_iPort); } m_iPort = -1; //
	 * m_oPlaybackBtn.setText("Playback"); m_iPlaybackID = -1; } } catch
	 * (Exception err) { Log.e(TAG, "error: " + err.toString()); }
	 * 
	 * };
	 */

	boolean login(MonitorInfo mi) {
		try {
			if (mi.loginID < 0) {
				// login on the device
				mi.loginID = loginDevice(mi.ip, mi.port, mi.user, mi.passwd);
				if (mi.loginID < 0) {
					Log.e(TAG, "This device logins failed!");
					return false;
				}
				// get instance of exception callback and set
				ExceptionCallBack oexceptionCbf = getExceptiongCbf();
				if (oexceptionCbf == null) {
					Log.e(TAG, "ExceptionCallBack object is failed!");
					return false;
				}

				if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(
						oexceptionCbf)) {
					Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
					return false;
				}

				// m_oLoginBtn.setText("Logout");
				Log.i(TAG,
						"Login sucess ****************************1***************************");

				return true;
			}
		} catch (Exception err) {
			Log.e(TAG, "error: " + err.toString());
		}

		return false;
	};

	/*
	 * public void logout() { if (m_iLogID >= 0) { if
	 * (!m_oHCNetSDK.NET_DVR_Logout_V30(m_iLogID)) { Log.e(TAG,
	 * " NET_DVR_Logout is failed!"); return; } m_iLogID = -1; } }
	 */

	// Preview listener
	void preview(MonitorInfo mi) {
		try {
			if (mi.loginID < 0) {
				Log.e(TAG, "please login on device first");
				return;
			}
			if (m_iPlayID < 0) {
				if (m_iPlaybackID >= 0) {
					Log.i(TAG, "Please stop palyback first");
					return;
				}
				// RealPlayCallBack fRealDataCallBack =
				// (m_osurfaceViews[m_current_sv] );
				RealPlayCallBack fRealDataCallBack = null;
				fRealDataCallBack = getRealPlayerCbf(m_osurfaceView);

				if (fRealDataCallBack == null) {
					Log.e(TAG, "fRealDataCallBack object is failed!");
					return;
				}

				NET_DVR_IPPARACFG_V40 struIPPara = new NET_DVR_IPPARACFG_V40();
				HCNetSDK.getInstance().NET_DVR_GetDVRConfig(mi.loginID,
						HCNetSDK.NET_DVR_GET_IPPARACFG_V40, 0, struIPPara);

				int iFirstChannelNo = -1;// get start channel no
				if (struIPPara.dwAChanNum > 0) {
					iFirstChannelNo = 1;
				} else {
					iFirstChannelNo = struIPPara.dwStartDChan;
				}
				if (iFirstChannelNo <= 0) {
					iFirstChannelNo = 1;
				}

				Log.i(TAG, "iFirstChannelNo:" + iFirstChannelNo);

				NET_DVR_CLIENTINFO ClientInfo = new NET_DVR_CLIENTINFO();

				int channel = 1;

				if (mi.channel != null)
					channel = Integer.parseInt(mi.channel);

				ClientInfo.lChannel = channel; // start channel no + preview
												// channel
				ClientInfo.lLinkMode = 1 << 31 + 3; // bit 31 -- 0,main
													// stream;1,sub stream
				// bit 0~30 -- link type,0-TCP;1-UDP;2-multicast;3-RTP
				ClientInfo.sMultiCastIP = null;

				// net sdk start preview
				m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V30(
						mi.loginID, ClientInfo, fRealDataCallBack, true);
				if (m_iPlayID < 0) {
					Log.e(TAG, "NET_DVR_RealPlay is failed!Err:"
							+ HCNetSDK.getInstance().NET_DVR_GetLastError());
					return;
				}

				Log.i(TAG,
						"NetSdk Play sucess ***********************3***************************");

				// m_oPreviewBtn.setText("Stop");
			}
		} catch (Exception err) {
			Log.e(TAG, "error: " + err.toString());
		}
	};

	public void stop() {
		if (m_iPlayID >= 0) {
			stopPlay();
			m_iPlayID = -1;
			// m_oPreviewBtn.setText("Preview");
		}
	}

	// configuration listener
	/*
	 * private void paramcfg() { try { paramCfg(m_iLogID); } catch (Exception
	 * err) { Log.e(TAG, "error: " + err.toString()); } };
	 */

	/**
	 * @fn stopPlay
	 * @author huyf
	 * @brief stop preview
	 * @param NULL
	 *            [in]
	 * @param NULL
	 *            [out]
	 * @return NULL
	 */
	private void stopPlay() {

		if (m_iPlayID < 0) {
			Log.e(TAG, "m_iPlayID < 0");
			return;
		}

		// net sdk stop preview
		if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
			Log.e(TAG, "StopRealPlay is failed!Err:"
					+ HCNetSDK.getInstance().NET_DVR_GetLastError());
			return;
		}

		// player stop play
		if (!m_oPlayerSDK.stop(m_iPort)) {
			Log.e(TAG, "stop is failed!");
			return;
		}

		m_oPlayerSDK.stopSound();

		if (!m_oPlayerSDK.closeStream(m_iPort)) {
			Log.e(TAG, "closeStream is failed!");
			return;
		}
		if (!m_oPlayerSDK.freePort(m_iPort)) {
			Log.e(TAG, "freePort is failed!" + m_iPort);
			return;
		}
		m_iPort = -1;
		// set id invalid
		// m_iPlayID = -1;
	}

	/**
	 * @fn loginDevice
	 * @author huyf
	 * @brief login on device
	 * @param NULL
	 *            [in]
	 * @param NULL
	 *            [out]
	 * @return login ID
	 */
	private int loginDevice(String strIP, int nPort, String strUser,
			String strPasswd) {
		// get instance
		m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
		if (null == m_oNetDvrDeviceInfoV30) {
			Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
			return -1;
		}
		/*
		 * String strIP = m_oIPAddr.getText().toString(); int nPort =
		 * Integer.parseInt(m_oPort.getText().toString()); String strUser =
		 * m_oUser.getText().toString(); String strPsd =
		 * m_oPsd.getText().toString(); // call NET_DVR_Login_v30 to login on,
		 * port 8000 as default
		 */
		/*
		 * String strIP = "192.168.1.90"; int nPort = 8000; String strUser =
		 * "admin"; String strPasswd = "12345";
		 */

		int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort,
				strUser, strPasswd, m_oNetDvrDeviceInfoV30);
		if (iLogID < 0) {
			Log.e(TAG, "NET_DVR_Login is failed!Err:"
					+ HCNetSDK.getInstance().NET_DVR_GetLastError());
			return -1;
		}

		Log.i(TAG, "NET_DVR_Login is Successful!");

		return iLogID;
	}

	/**
	 * @fn paramCfg
	 * @author huyf
	 * @brief configuration
	 * @param iUserID
	 *            - login ID [in]
	 * @param NULL
	 *            [out]
	 * @return NULL
	 */
	private void paramCfg(final int iUserID) {
		// whether have logined on
		if (iUserID < 0) {
			Log.e(TAG, "iUserID < 0");
			return;
		}

		if (m_byGetFlag == 1) {
			if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID,
					HCNetSDK.NET_DVR_GET_NETCFG_V30, 0, NetCfg)) {
				Log.e(TAG, "get net cfg faied!" + " err: "
						+ HCNetSDK.getInstance().NET_DVR_GetLastError());
				// m_oDNSServer1.setText("get net cfg failed");
			} else {
				Log.i(TAG, "get net cfg succ!");
				String strIP = new String(NetCfg.struDnsServer1IpAddr.sIpV4);
				// m_oDNSServer1.setText(strIP.trim());
				// m_oParamCfgBtn.setText("Set Netcfg");
				m_byGetFlag = 0;
			}
		} else {
			byte[] byIP = new byte[10];// utem_oDNSServer1.getText().toString().getBytes();
			NetCfg.struDnsServer1IpAddr.sIpV4 = new byte[16];
			System.arraycopy(byIP, 0, NetCfg.struDnsServer1IpAddr.sIpV4, 0,
					byIP.length);
			if (!HCNetSDK.getInstance().NET_DVR_SetDVRConfig(iUserID,
					HCNetSDK.NET_DVR_SET_NETCFG_V30, 0, NetCfg)) {
				Log.e(TAG, "Set net cfg faied!" + " err: "
						+ HCNetSDK.getInstance().NET_DVR_GetLastError());
			} else {
				Log.i(TAG, "Set net cfg succ!");
				// m_oParamCfgBtn.setText("Get Netcfg");
				m_byGetFlag = 1;
			}
		}
	}

	/**
	 * @fn getExceptiongCbf
	 * @author huyf
	 * @brief process exception
	 * @param NULL
	 *            [in]
	 * @param NULL
	 *            [out]
	 * @return exception instance
	 */
	private ExceptionCallBack getExceptiongCbf() {
		ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
			public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
				;// you can add process here
			}
		};
		return oExceptionCbf;
	}

	/**
	 * @fn getRealPlayerCbf
	 * @author huyf
	 * @brief get realplay callback instance
	 * @param NULL
	 *            [in]
	 * @param NULL
	 *            [out]
	 * @return callback instance
	 */
	private RealPlayCallBack getRealPlayerCbf(final SurfaceView sv) {
		RealPlayCallBack cbf = new RealPlayCallBack() {
			SurfaceView m_sv = sv;

			public void fRealDataCallBack(int iRealHandle, int iDataType,
					byte[] pDataBuffer, int iDataSize) {
				// player channel 1
				processRealData(iRealHandle, iDataType, pDataBuffer, iDataSize,
						Player.STREAM_REALTIME, m_sv);
			}
		};
		return cbf;
	}

	/**
	 * @fn getPlayerbackPlayerCbf
	 * @author Jerry
	 * @brief get Playback instance
	 * @param NULL
	 *            [in]
	 * @param NULL
	 *            [out]
	 * @return callback instance
	 */
	private PlaybackCallBack getPlayerbackPlayerCbf(final SurfaceView sv) {
		PlaybackCallBack cbf = new PlaybackCallBack() {

			SurfaceView m_sv = sv;

			@Override
			public void fPlayDataCallBack(int iPlaybackHandle, int iDataType,
					byte[] pDataBuffer, int iDataSize) {
				// player channel 1
				processRealData(1, iDataType, pDataBuffer, iDataSize,
						Player.STREAM_FILE, m_sv);
			}
		};
		return cbf;
	}

	/**
	 * @fn processRealData
	 * @author huyf
	 * @brief process real data
	 * @param iPlayViewNo
	 *            - player channel [in]
	 * @param iDataType
	 *            - data type [in]
	 * @param pDataBuffer
	 *            - data buffer [in]
	 * @param iDataSize
	 *            - data size [in]
	 * @param iStreamMode
	 *            - stream mode [in]
	 * @param NULL
	 *            [out]
	 * @return NULL
	 */
	public void processRealData(int iPlayViewNo, int iDataType,
			byte[] pDataBuffer, int iDataSize, int iStreamMode, SurfaceView sv) {
		long beginTime = 0;
		long endTime = 0;
		int nID = 0;
		// Log.i(TAG, "iPlayViewNo:" + iPlayViewNo + "iDataType:" + iDataType +
		// "iDataSize:" + iDataSize);
		try {
			switch (iDataType) {
			case HCNetSDK.NET_DVR_SYSHEAD:

				m_iPort = m_oPlayerSDK.getPort();
				Log.v("JailMon",
						String.format("Windows %d: getPort %d", nID, m_iPort));

				if (m_iPort == -1) {
					Log.e(TAG, "getPort is failed!");
					break;
				}

				if (iDataSize > 0) {

					if (!m_oPlayerSDK.setStreamOpenMode(m_iPort, iStreamMode)) {
						Log.v("JailMon", "setStreamOpenMode");
						break;
					}

					if (!m_oPlayerSDK.setSecretKey(m_iPort, 1,
							"ge_security_3477".getBytes(), 128)) {
						Log.e("JailMon", "setSecretKey failed");
						break;
					}

					if (!m_oPlayerSDK.openStream(m_iPort, pDataBuffer,
							iDataSize, 2 * 1024 * 1024)) // open stream
					{
						Log.e("JailMon", "openStream failed");
						break;
					}

					SurfaceHolder holder = sv.getHolder();

					if (!m_oPlayerSDK.play(m_iPort, holder)) {
						Log.e("JailMon", "play failed");
						break;
					}

					m_oPlayerSDK.playSound(m_iPort);
				}
				break;
			case HCNetSDK.NET_DVR_STREAMDATA:
			case HCNetSDK.NET_DVR_STD_AUDIODATA:
			case HCNetSDK.NET_DVR_STD_VIDEODATA:
				if (!m_oPlayerSDK.inputData(m_iPort, pDataBuffer, iDataSize)) {
					Log.e(TAG,
							"inputData failed with: "
									+ m_oPlayerSDK.getLastError(m_iPort));
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			Log.e(TAG, "processRealData Exception!err:" + e.toString());
		}
	}

	/**
	 * @fn Cleanup
	 * @author huyf
	 * @brief cleanup
	 * @param NULL
	 *            [in]
	 * @param NULL
	 *            [out]
	 * @return NULL
	 */
	public void Cleanup() {
		// release player resource
		m_oPlayerSDK.freePort(m_iPort);
		m_iPort = -1;

		// release net SDK resource
		HCNetSDK.getInstance().NET_DVR_Cleanup();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			int nCount;

			if (m_milist == null || (nCount = m_milist.m_list.size()) == 0)
				return false;

			MonitorInfo mi;
			boolean flag = true;
			for (int i = 0; i < nCount; i++) {
				mi = m_milist.m_list.get(i);
				flag = flag && mi.available;
			}
			if (!flag) {
				Toast.makeText(m_AppContext, "正在初始化监控设备，请稍后返回",
						Toast.LENGTH_SHORT).show();
			} else {
				stopPlay();
				Cleanup();
				// android.os.Process.killProcess(android.os.Process.myPid());
				finish();
			}
			return false;
		default:
			break;
		}

		return true;
	}
}

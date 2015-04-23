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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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

public class MonitorActivity4Chan extends BaseActivity implements Callback {

	final static int LOGIN_MONITOR_UPDATE = 100;

	private ListView m_listViewMonitors;
	private LinearLayout m_layoutMain, m_layoutContent;
	private AnimationUtil m_aniset;

	private Player m_oPlayerSDK = null;
	private HCNetSDK m_oHCNetSDK = null;

	private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;

	//private int m_iLogID = -1; // return by NET_DVR_Login_v30
	//private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
	private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime
	private byte m_byGetFlag = 1; // 1-get net cfg, 0-set net cfg
	//private int m_iPort = -1; // play port
	private NET_DVR_NETCFG_V30 NetCfg = new NET_DVR_NETCFG_V30(); // netcfg
																	// struct

	private MonitorInfoList m_milist;

	private final String TAG = "JAILMON";

	//private SurfaceView m_osurfaceView = null;
	private SurfaceView[] m_osurfaceViews = new SurfaceView[4];
	private SurfaceHolder[] m_holder = new SurfaceHolder[4];
	private int[] m_iPlayID = { -1, -1, -1, -1 };
	private int[] m_iPort = {-1, -1, -1, -1}; // play port
	private LinearLayout[] m_layouts = new LinearLayout[4];
	
	int m_current_sv = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!initeSdk()) {
			this.finish();
			return;
		}

		m_aniset = new AnimationUtil(this);

		setContentView(R.layout.monitor4chan);

		m_layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
		m_layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

		m_listViewMonitors = (ListView) findViewById(R.id.lstMain);

		Button btnBack = (Button) findViewById(R.id.buttonBack);

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		/*
		 * String[] strItems = { "监控一", "监控二", "监控三", "监控四", "监控五", "监控六" };
		 * 
		 * CustomAdapter_MainList adapter = new CustomAdapter_MainList(
		 * MonitorActivity.this, strItems, R.drawable.list_icon_monitor, 6);
		 * 
		 * m_listViewMonitors.setAdapter(adapter);
		 */

		m_listViewMonitors.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				// login();
				int sel = (int)arg2;
				
				MonitorInfo mi = m_milist.m_list.get(sel);
				
				if( mi.available )
				{
					stop();
					preview(mi);
				}

				// loadShiftInfo( arg2 );
			}

		});

		m_layoutMain.startAnimation(m_aniset.m_aniFadeIn);
		m_layoutContent.startAnimation(m_aniset.m_aniFadeIn);

		//m_osurfaceView = (SurfaceView) findViewById(R.id.Sur_Player1);
		//m_osurfaceView.getHolder().addCallback(this);
		
		
		initSV();
		

		loadMonitorInfo();
	}

	
	void initSV()
	{
		int[] res = { R.id.Sur_Player1, R.id.Sur_Player2, R.id.Sur_Player3, R.id.Sur_Player4 };
		int[] layres = { R.id.layoutMV1, R.id.layoutMV2, R.id.layoutMV3, R.id.layoutMV4 };
		
		for( int i=0; i<m_osurfaceViews.length; i++ )
		{
			m_osurfaceViews[i] = (SurfaceView) findViewById(res[i]);
			m_osurfaceViews[i].getHolder().addCallback(this);
			
			m_layouts[i] = (LinearLayout) findViewById(layres[i] );
			
			m_holder[i] = m_osurfaceViews[i].getHolder();
			
			m_layouts[i].setOnClickListener( new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					for( int i=0; i<m_layouts.length; i++ )
					{
						if( arg0 == m_layouts[i] )
							setCurrentSV(i);
					}
				}});
			
			setCurrentSV(0);
		}
	}
	
	void setCurrentSV( int n )
	{
		if( m_current_sv != -1 )
			m_layouts[m_current_sv].setBackgroundResource(R.drawable.monitor_video);
		
		m_current_sv = n;
		m_layouts[m_current_sv].setBackgroundResource(R.drawable.monitor_video_current);
		
	}
	
	// @Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		int port = 0;
		
		for( int i=0; i<4; i++)
		{
			if( m_holder[i] == holder )
			{
				port = m_iPort[i];
				break;
			}
		}
		
		Log.i(TAG, "surface is created" + m_iPort);
		if (-1 == port) {
			return;
		}
		Surface surface = holder.getSurface();
		if (null != m_oPlayerSDK && true == surface.isValid()) {
			if (false == m_oPlayerSDK.setVideoWindow(port, 0, holder)) {
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
		int port = 0;
		
		for( int i=0; i<4; i++)
		{
			if( m_holder[i] == holder )
			{
				port = m_iPort[i];
				break;
			}
		}
		
		Log.i(TAG, "Player setVideoWindow release!" + m_iPort);
		if (-1 == port) {
			return;
		}
		if (null != m_oPlayerSDK && true == holder.getSurface().isValid()) {
			if (false == m_oPlayerSDK.setVideoWindow(port, 0, null)) {
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
							MonitorActivity4Chan.this, m_milist,
							R.drawable.list_icon_monitor);

					m_listViewMonitors.setAdapter(m_adapterMonitor);
					
					loginAllMonitor(m_handler);
				}
			} else if (msg.what == API_GET_FAIL) {
				closeProcessDialog();
				AppException exception = (AppException) msg.obj;
				exception.makeToast(MonitorActivity4Chan.this);
			} else if( msg.what == LOGIN_MONITOR_UPDATE )
			{
				if( m_adapterMonitor != null )
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
				
				boolean bUpdate = false;
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
		m_oHCNetSDK = new HCNetSDK();
		if (null == m_oHCNetSDK) {
			Log.e(TAG, "m_oHCNetSDK new is failed!");
			return false;
		}

		if (!m_oHCNetSDK.NET_DVR_Init()) {
			Log.e(TAG, "HCNetSDK init is failed!");
			return false;
		}
		m_oHCNetSDK.NET_DVR_SetLogToFile(3, "/sdcard/sdklog/", true);

		// init player
		m_oPlayerSDK = Player.getInstance();
		if (m_oPlayerSDK == null) {
			Log.e(TAG, "PlayCtrl getInstance failed!");
			return false;
		}

		return true;
	}

	/*private void playback() {
		try {
			if (m_iLogID < 0) {
				Log.e(TAG, "please login on device first");
				return;
			}

			if (m_iPlaybackID < 0) {
				if (m_iPlayID >= 0) {
					Log.i(TAG, "Please stop preview first");
					return;
				}
				PlaybackCallBack fPlaybackCallBack = getPlayerbackPlayerCbf(m_osurfaceViews[m_current_sv]);
				if (fPlaybackCallBack == null) {
					Log.e(TAG, "fPlaybackCallBack object is failed!");
					return;
				}
				NET_DVR_TIME struBegin = new NET_DVR_TIME();
				NET_DVR_TIME struEnd = new NET_DVR_TIME();

				struBegin.dwYear = 2012;
				struBegin.dwMonth = 6;
				struBegin.dwDay = 14;
				struBegin.dwHour = 9;
				struBegin.dwMinute = 0;
				struBegin.dwSecond = 0;

				struEnd.dwYear = 2012;
				struEnd.dwMonth = 6;
				struEnd.dwDay = 14;
				struEnd.dwHour = 17;
				struEnd.dwMinute = 10;
				struEnd.dwSecond = 0;

				m_iPlaybackID = m_oHCNetSDK.NET_DVR_PlayBackByTime(m_iLogID, 1,
						struBegin, struEnd);
				if (m_iPlaybackID >= 0) {
					if (!m_oHCNetSDK.NET_DVR_SetPlayDataCallBack(m_iPlaybackID,
							fPlaybackCallBack)) {
						Log.e(TAG, "Set playback callback failed!");
						return;
					}
					NET_DVR_PLAYBACK_INFO struPlaybackInfo = null;
					if (!m_oHCNetSDK.NET_DVR_PlayBackControl_V40(m_iPlaybackID,
							HCNetSDK.NET_DVR_PLAYSTART, null, 0,
							struPlaybackInfo)) {
						Log.e(TAG, "net sdk playback start failed!");
						return;
					}
					// m_oPlaybackBtn.setText("Stop");
				} else {
					Log.i(TAG, "NET_DVR_PlayBackByTime failed, error code: "
							+ m_oHCNetSDK.NET_DVR_GetLastError());
				}
			} else {
				if (!m_oHCNetSDK.NET_DVR_StopPlayBack(m_iPlaybackID)) {
					Log.e(TAG, "net sdk stop playback failed");
				}
				// player stop play
				if (!m_oPlayerSDK.stop(m_iPort)) {
					Log.e(TAG, "player_stop is failed!");
				}
				if (!m_oPlayerSDK.closeStream(m_iPort)) {
					Log.e(TAG, "closeStream is failed!");
				}
				if (!m_oPlayerSDK.freePort(m_iPort)) {
					Log.e(TAG, "freePort is failed!" + m_iPort);
				}
				m_iPort = -1;
				// m_oPlaybackBtn.setText("Playback");
				m_iPlaybackID = -1;
			}
		} catch (Exception err) {
			Log.e(TAG, "error: " + err.toString());
		}

	};*/

	boolean login(MonitorInfo mi) {
		try {
			if (mi.loginID < 0) {
				// login on the device
				mi.loginID = loginDevice(mi.ip, mi.port, mi.user, mi.passwd);
				if (mi.loginID  < 0) {
					Log.e(TAG, "This device logins failed!");
					return false;
				}
				// get instance of exception callback and set
				ExceptionCallBack oexceptionCbf = getExceptiongCbf();
				if (oexceptionCbf == null) {
					Log.e(TAG, "ExceptionCallBack object is failed!");
					return false;
				}

				if (!m_oHCNetSDK.NET_DVR_SetExceptionCallBack(oexceptionCbf)) {
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

	/*public void logout() {
		if (m_iLogID >= 0) {
			if (!m_oHCNetSDK.NET_DVR_Logout_V30(m_iLogID)) {
				Log.e(TAG, " NET_DVR_Logout is failed!");
				return;
			}
			m_iLogID = -1;
		}
	}*/

	// Preview listener
	void preview(MonitorInfo mi) {
		try {
			if (mi.loginID< 0) {
				Log.e(TAG, "please login on device first");
				return;
			}
			int playID = m_iPlayID[m_current_sv];
			if (playID < 0) {
				if (m_iPlaybackID >= 0) {
					Log.i(TAG, "Please stop palyback first");
					return;
				}
				//RealPlayCallBack fRealDataCallBack = getRealPlayerCbf(m_osurfaceViews[m_current_sv] );
				RealPlayCallBack fRealDataCallBack = null;
				if( m_current_sv == 0 )
					fRealDataCallBack = getRealPlayerCbf1(m_osurfaceViews[m_current_sv] );
				else if( m_current_sv == 1 )
					fRealDataCallBack = getRealPlayerCbf2(m_osurfaceViews[m_current_sv] );
				else if( m_current_sv == 2 )
					fRealDataCallBack = getRealPlayerCbf2(m_osurfaceViews[m_current_sv] );
				else if( m_current_sv == 3 )
					fRealDataCallBack = getRealPlayerCbf2(m_osurfaceViews[m_current_sv] );
					
				if (fRealDataCallBack == null) {
					Log.e(TAG, "fRealDataCallBack object is failed!");
					return;
				}

				NET_DVR_IPPARACFG_V40 struIPPara = new NET_DVR_IPPARACFG_V40();
				m_oHCNetSDK.NET_DVR_GetDVRConfig(mi.loginID,
						HCNetSDK.NET_DVR_GET_IPPARACFG_V40, 0, struIPPara);

				int iFirstChannelNo = -1;// get start channel no
				if (struIPPara.dwAChanNum > 0) {
					iFirstChannelNo = 1;
				} else {
					iFirstChannelNo = struIPPara.dwStartDChan;
				}

				Log.i(TAG, "iFirstChannelNo:" + iFirstChannelNo);

				NET_DVR_CLIENTINFO ClientInfo = new NET_DVR_CLIENTINFO();
				int channel = Integer.parseInt(mi.channel);
				ClientInfo.lChannel = channel; // start channel no + preview channel
				ClientInfo.lLinkMode = 1 << 31 + 3; // bit 31 -- 0,main
													// stream;1,sub stream
				// bit 0~30 -- link type,0-TCP;1-UDP;2-multicast;3-RTP
				ClientInfo.sMultiCastIP = null;

				// net sdk start preview
				m_iPlayID[m_current_sv] = m_oHCNetSDK.NET_DVR_RealPlay_V30(mi.loginID,
						ClientInfo, fRealDataCallBack, true);
				if (m_iPlayID[m_current_sv] < 0) {
					Log.e(TAG,
							"NET_DVR_RealPlay is failed!Err:"
									+ m_oHCNetSDK.NET_DVR_GetLastError());
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
		if (m_iPlayID[m_current_sv] >= 0) {
			stopPlay(m_iPlayID[m_current_sv]);
			m_iPlayID[m_current_sv] = m_iPlayID[m_current_sv]-1;
			// m_oPreviewBtn.setText("Preview");
		}
	}

	// configuration listener
	/*private void paramcfg() {
		try {
			paramCfg(m_iLogID);
		} catch (Exception err) {
			Log.e(TAG, "error: " + err.toString());
		}
	};*/

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
	private void stopPlay( int nID ) {
		
		int playID = m_iPlayID[nID];
		
		if (playID < 0) {
			Log.e(TAG, "m_iPlayID < 0");
			return;
		}

		// net sdk stop preview
		if (!m_oHCNetSDK.NET_DVR_StopRealPlay(playID)) {
			Log.e(TAG,
					"StopRealPlay is failed!Err:"
							+ m_oHCNetSDK.NET_DVR_GetLastError());
			return;
		}

		// player stop play
		if (!m_oPlayerSDK.stop(m_iPort[nID])) {
			Log.e(TAG, "stop is failed!");
			return;
		}

		m_oPlayerSDK.stopSound();

		if (!m_oPlayerSDK.closeStream(m_iPort[nID])) {
			Log.e(TAG, "closeStream is failed!");
			return;
		}
		if (!m_oPlayerSDK.freePort(m_iPort[nID])) {
			Log.e(TAG, "freePort is failed!" + m_iPort);
			return;
		}
		m_iPort[nID] = -1;
		// set id invalid
		//m_iPlayID = -1;
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

		int iLogID = m_oHCNetSDK.NET_DVR_Login_V30(strIP, nPort, strUser,
				strPasswd, m_oNetDvrDeviceInfoV30);
		if (iLogID < 0) {
			Log.e(TAG,
					"NET_DVR_Login is failed!Err:"
							+ m_oHCNetSDK.NET_DVR_GetLastError());
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
			if (!m_oHCNetSDK.NET_DVR_GetDVRConfig(iUserID,
					HCNetSDK.NET_DVR_GET_NETCFG_V30, 0, NetCfg)) {
				Log.e(TAG,
						"get net cfg faied!" + " err: "
								+ m_oHCNetSDK.NET_DVR_GetLastError());
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
			if (!m_oHCNetSDK.NET_DVR_SetDVRConfig(iUserID,
					HCNetSDK.NET_DVR_SET_NETCFG_V30, 0, NetCfg)) {
				Log.e(TAG,
						"Set net cfg faied!" + " err: "
								+ m_oHCNetSDK.NET_DVR_GetLastError());
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
	private RealPlayCallBack getRealPlayerCbf1(final SurfaceView sv) {
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
	
	private RealPlayCallBack getRealPlayerCbf2(final SurfaceView sv) {
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
	
	private RealPlayCallBack getRealPlayerCbf3(final SurfaceView sv) {
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
	
	private RealPlayCallBack getRealPlayerCbf4(final SurfaceView sv) {
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
				for( int i=0; i<4; i++ )
				{
					if( m_osurfaceViews[i] == sv )
					{
						nID = i;
						break;
					}
				}
				
				if (m_iPort[nID] >= 0) {
					break;
				}
				
				m_iPort[nID] = m_oPlayerSDK.getPort();
				Log.v( "JailMon",  String.format("Windows %d: getPort %d", nID, m_iPort[nID]));
				
				if (m_iPort[nID] == -1) {
					Log.e(TAG, "getPort is failed!");
					break;
				}
				
				// m_oPlayerSDK.setVideoWindow(m_iPort[nID], 0, sv.getHolder());
				 
				if (iDataSize > 0) {

					if (!m_oPlayerSDK.setStreamOpenMode(m_iPort[nID], iStreamMode)) // set
																				// stream
																				// mode
					{
						Log.v("JailMon", String.format("setStreamOpenMode failed (Port %d)", m_iPort[nID]) );
						break;
					}
					else
					{					
						Log.v( "JailMon",  String.format("setStreamOpenMode OK (Port %d)",m_iPort[nID]));
					}
					
					if (!m_oPlayerSDK.setSecretKey(m_iPort[nID], 1,
							"ge_security_3477".getBytes(), 128)) {
						Log.e(TAG, "setSecretKey failed");
						break;
					}
					
					if (!m_oPlayerSDK.openStream(m_iPort[nID], pDataBuffer,
							iDataSize, 2 * 1024 * 1024)) // open stream
					{
						Log.e( "JailMon", String.format("openStream failed (Port %d)", m_iPort[nID]) );
						break;
					}
					else
					{
						Log.v( "JailMon",  String.format("openStream OK (Port %d)",m_iPort[nID]));
					}
					
					SurfaceHolder holder = sv.getHolder();
					
					if (!m_oPlayerSDK.play(m_iPort[nID], holder)) {
						Log.e( "JailMon", String.format("play failed %d", m_iPort[nID]) );
						break;
					}
					else
					{
						Log.v( "JailMon",  String.format("play OK (Port %d, Surface %s)",m_iPort[nID], sv.toString()));
					}

					m_oPlayerSDK.playSound(m_iPort[nID]);
				}
				break;
			case HCNetSDK.NET_DVR_STREAMDATA:
			case HCNetSDK.NET_DVR_STD_AUDIODATA:
			case HCNetSDK.NET_DVR_STD_VIDEODATA:
				if (!m_oPlayerSDK.inputData(m_iPort[nID], pDataBuffer, iDataSize)) {
					Log.e(TAG,
							"inputData failed with: "
									+ m_oPlayerSDK.getLastError(m_iPort[nID]));
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
		
		for( int i=0; i<4; i++ )
		{

		m_oPlayerSDK.freePort(m_iPort[i]);
		m_iPort[i] = -1;
		}

		// release net SDK resource
		m_oHCNetSDK.NET_DVR_Cleanup();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			for( int i=0; i<4; i++)
			{
				stopPlay(m_iPlayID[i]);
			}
			Cleanup();
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		default:
			break;
		}

		return true;
	}
}

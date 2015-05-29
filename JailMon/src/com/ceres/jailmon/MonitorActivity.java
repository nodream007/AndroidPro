/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import java.util.Calendar;
import java.util.Date;

import org.MediaPlayer.PlayM4.Player;
import org.MediaPlayer.PlayM4.Player.MPSystemTime;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceres.jailmon.adapter.CustomAdapter_MonitorInfo;
import com.ceres.jailmon.component.TimeBar;
import com.ceres.jailmon.component.TimeBar.TimePickedCallBack;
import com.ceres.jailmon.data.MonitorInfo;
import com.ceres.jailmon.data.MonitorInfoList;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_CLIENTINFO;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_IPPARACFG_V40;
import com.hikvision.netsdk.NET_DVR_NETCFG_V30;
import com.hikvision.netsdk.NET_DVR_PLAYBACK_INFO;
import com.hikvision.netsdk.NET_DVR_TIME;
import com.hikvision.netsdk.PlaybackCallBack;
import com.hikvision.netsdk.PlaybackControlCommand;
import com.hikvision.netsdk.RealPlayCallBack;

public class MonitorActivity extends BaseActivity implements Callback,
		OnClickListener {

	private final String TAG = "JAILMON";

	final static int LOGIN_MONITOR_UPDATE = 100;
	
	final static int UPDATE_TIME_BAR = 99;

	private ListView m_listViewMonitors;
	private LinearLayout m_layoutMain, m_layoutContent;
	private AnimationUtil m_aniset;

	// 悬浮组件部分
	private LinearLayout m_realPlaySuspension;
	private TextView m_playBack;
	private LinearLayout m_palyHDll;
	private LinearLayout m_palySDll;
	private TextView m_palyHD;
	private TextView m_palyHDText;
	private TextView m_palySD;
	private TextView m_palySDText;

	private LinearLayout m_playBackSuspension;
	private TextView m_preview;
	private TextView m_date;
	
	private int m_year, m_monthOfYear, m_dayOfMonth, m_hourOfDay, m_minute, m_second;
	
	private TimeBar timeBar;
	private LinearLayout m_timeBar;
	/** 时间回调函数 */
	private TimePickedCallBack mTimePickCallback;

	private Player m_oPlayerSDK = null;

	private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;

	// private int m_iLogID = -1; // return by NET_DVR_Login_v30
	private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
	private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime
	private byte m_byGetFlag = 1; // 1-get net cfg, 0-set net cfg
	private int m_iPort = -1; // play port
	private int m_playMode;
	private static final int m_playHD = 0; // 高清
	private static final int m_playSD = 1; // 标清
	private NET_DVR_NETCFG_V30 NetCfg = new NET_DVR_NETCFG_V30(); // netcfg
																	// struct

	private int m_PlayBackId = -1;
	
	private MonitorInfoList m_milist;

	private MonitorInfo m_nowMonitorInfo;

	private SurfaceView m_osurfaceView = null;

	private boolean bUpdate = false;

	private CustomAdapter_MonitorInfo m_adapterMonitor;
	
	private Thread updateTimeBarThread;
	
	private boolean isflag = true;

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
			} else if (msg.what == UPDATE_TIME_BAR) {
				MPSystemTime m = new MPSystemTime();
				m = (MPSystemTime) msg.obj;
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, m.year);
				cal.set(Calendar.MONTH, m.month - 1);
				cal.set(Calendar.DATE, m.day);
				
				cal.set(Calendar.HOUR_OF_DAY, m.hour);
				cal.set(Calendar.MINUTE, m.min);
				cal.set(Calendar.SECOND, m.sec);
				
				timeBar.setCurrentTime(cal.getTime());
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!initeSdk()) {
			this.finish();
			return;
		}

		m_aniset = new AnimationUtil(this);

		setContentView(R.layout.monitor);

		initView();

		loadMonitorInfo();
	}

	@Override
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

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
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
				isflag = false;
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
	@Override
	protected void onDestroy() {
		isflag = false;
		super.onDestroy();
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.monitor_playback_img:
			stopPlay();
			startPlayBack();
//			Calendar cal = Calendar.getInstance();
//			cal.add(Calendar.DATE, -1);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.SECOND, 0);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.MILLISECOND, 0);
//			NET_DVR_TIME struBegin = new NET_DVR_TIME();
//			NET_DVR_TIME struEnd = new NET_DVR_TIME();
//			struBegin.dwYear = cal.get(Calendar.YEAR);    //获取年
//			struBegin.dwMonth = cal.get(Calendar.MONTH) + 1;
//			struBegin.dwDay = cal.get(Calendar.DAY_OF_MONTH);
//			struBegin.dwHour = cal.get(Calendar.HOUR);
//			struBegin.dwMinute = cal.get(Calendar.MINUTE);
//			struBegin.dwSecond = cal.get(Calendar.SECOND) + 1;
//
//			struEnd.dwYear = cal.get(Calendar.YEAR);
//			struEnd.dwMonth = cal.get(Calendar.MONTH) + 1;
//			struEnd.dwDay = cal.get(Calendar.DAY_OF_MONTH) + 1;
//			struEnd.dwHour = 23;
//			struEnd.dwMinute = 59;
//			struEnd.dwSecond = 59;
//			PlayBack("192.0.1.39", 8000, "admin", "12345", 3, struBegin, struEnd);
			break;
		case R.id.monitor_hd_ll:
			stopPlay();
			m_playMode = m_playHD;
			preview(m_nowMonitorInfo, m_playHD);
			break;
		case R.id.monitor_sd_ll:
			stopPlay();
			m_playMode = m_playSD;
			preview(m_nowMonitorInfo, m_playSD);
			break;
		case R.id.monitor_preview_img:
			stopPlayBack();
			m_playMode = m_playHD;
			preview(m_nowMonitorInfo, m_playHD);
			break;
		case R.id.monitor_date_img:
			/**
			 * 实例化一个DatePickerDialog的对象
			 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类
			 * ，当用户选择好日期点击done会调用里面的onDateSet方法
			 */
			DatePickerDialog datePickerDialog = new DatePickerDialog(
					this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							m_year = year;
							m_monthOfYear = monthOfYear;
							m_dayOfMonth = dayOfMonth;
							m_hourOfDay = 0;
							m_minute = 0;
							m_second = 0;
							startPlayBack();
						}
					}, m_year, m_monthOfYear, m_dayOfMonth);

			datePickerDialog.show();
			break;
		}

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

	/**
	 * 初始化页面
	 */
	private void initView() {
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
					isflag = false;
					stopPlay();
					stopPlayBack();
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
				m_nowMonitorInfo = mi;
				if (mi.available) {
					stopPlay();
					m_playMode = m_playHD;
					preview(mi, m_playHD);
				}
				m_adapterMonitor.setPosition(arg2);
			}

		});

		m_osurfaceView = (SurfaceView) findViewById(R.id.Sur_Player1);
		m_osurfaceView.getHolder().addCallback(this);

		// 实时预览界面右上方悬浮组件
		m_realPlaySuspension = (LinearLayout) findViewById(R.id.monitor_realpaly_suspension);
		m_playBack = (TextView) findViewById(R.id.monitor_playback_img);
		m_playBack.setOnClickListener(this);
		m_palyHDll = (LinearLayout) findViewById(R.id.monitor_hd_ll);
		m_palyHDll.setOnClickListener(this);
		m_palySDll = (LinearLayout) findViewById(R.id.monitor_sd_ll);
		m_palySDll.setOnClickListener(this);
		m_palyHD = (TextView) findViewById(R.id.monitor_hd_img);
		m_palyHDText = (TextView) findViewById(R.id.monitor_hd_text);
		m_palySD = (TextView) findViewById(R.id.monitor_sd_img);
		m_palySDText = (TextView) findViewById(R.id.monitor_sd_text);

		m_playBackSuspension = (LinearLayout) findViewById(R.id.monitor_palyback_suspension);
		m_preview = (TextView) findViewById(R.id.monitor_preview_img);
		m_preview.setOnClickListener(this);
		m_date = (TextView) findViewById(R.id.monitor_date_img);
		m_date.setOnClickListener(this);
		initTimeBar();
	}

	/**
	 * 初始化时间条
	 */
	private void initTimeBar() {
		Calendar calendar = Calendar.getInstance();
		m_year = calendar.get(Calendar.YEAR);
		m_monthOfYear = calendar.get(Calendar.MONTH) + 1;
		m_dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) - 1;// 默认播放前一天的回放
		m_hourOfDay = 0;
		m_minute = 0;
		m_second = 0;
		
		timeBar = (TimeBar) findViewById(R.id.timebar);
		m_timeBar = (LinearLayout) findViewById(R.id.monitor_timebar_ll);
		mTimePickCallback = new TimePickedCallBack() {
	
			@Override
			public void onTimePickedCallback(Calendar currentTime) {
				m_year = currentTime.get(Calendar.YEAR);    //获取年
				m_monthOfYear = currentTime.get(Calendar.MONTH) + 1;
				m_dayOfMonth = currentTime.get(Calendar.DAY_OF_MONTH);
				m_hourOfDay = currentTime.get(Calendar.HOUR);
				m_minute = currentTime.get(Calendar.MINUTE);
				m_second = currentTime.get(Calendar.SECOND);
				startPlayBack();
			}
	
			@Override
			public void onMoveTimeCallback(long milliseconds) {
	
			}
		};
		timeBar.setTimeBarCallback(mTimePickCallback);
	}

	/**
	 * 更新视图组件
	 * 
	 * @param type
	 *            1--实时预览；2--监控回放
	 */
	public void updateUI(int type) {
		switch (type) {
		// 实时预览
		case 1:
			m_realPlaySuspension.setVisibility(View.VISIBLE);
			m_playBackSuspension.setVisibility(View.GONE);
			m_timeBar.setVisibility(View.GONE);
			if (m_playMode == m_playHD) {
				m_palyHD.setBackgroundResource(R.drawable.monitor_hd_select);
				m_palySD.setBackgroundResource(R.drawable.monitor_sd);
				m_palyHDText
						.setTextColor(getResources().getColor(R.color.blue));
				m_palySDText.setTextColor(getResources()
						.getColor(R.color.white));
			} else if (m_playMode == m_playSD) {
				m_palyHD.setBackgroundResource(R.drawable.monitor_hd);
				m_palySD.setBackgroundResource(R.drawable.monitor_sd_select);
				m_palyHDText.setTextColor(getResources()
						.getColor(R.color.white));
				m_palySDText
						.setTextColor(getResources().getColor(R.color.blue));
			}
			break;
		case 2:
			m_realPlaySuspension.setVisibility(View.GONE);
			m_playBackSuspension.setVisibility(View.VISIBLE);
			m_timeBar.setVisibility(View.VISIBLE);
			break;
		}
	}
	/**
	 * 
	 */
	public void updateTimeBar(final Handler handler) {
		updateTimeBarThread = new Thread() {
			public void run() {
				if (m_oPlayerSDK != null) {
					while (isflag) {
						MPSystemTime m = new MPSystemTime();
						m_oPlayerSDK.getSystemTime(m_iPort, m);
						Message msg = new Message();
						msg.what = UPDATE_TIME_BAR;
						msg.obj = m;
						handler.sendMessage(msg);
						
					}
				}
			}
		};
		updateTimeBarThread.start();
	}
//	Runnable updateTimeBar = new Runnable(){  
//		  public void run(){  
//		    //以上代码略  
//		    //延迟1000毫秒，执行这个线程的[color=red]run[/color]方法  
//			  m_handler.postDelayed(updateTimeBar,1000);  
//		  }  
//		};
//	private class myAsync extends AsyncTask<Void, Integer, Void> {
//
//		@Override
//		protected Void doInBackground(Void... arg0) {
//			if (m_oPlayerSDK != null) {
//				while (isflag) {
//					MPSystemTime m = new MPSystemTime();
//					m_oPlayerSDK.getSystemTime(m_iPort, m);
//				}
//			}
//			return null;
//		}
//		
//	}

	private boolean login(MonitorInfo mi) {
		try {
			if (mi.loginID < 0) {
				// login on the device
				mi.loginID = loginDevice(mi.ip, mi.port, mi.user, mi.passwd);
				// mi.loginID = loginDevice("192.0.1.18", 8000, "admin",
				// "12345");
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
						"Login sucess ********************************************************");

				return true;
			}
		} catch (Exception err) {
			Log.e(TAG, "error: " + err.toString());
		}

		return false;
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

	private void loadMonitorInfo() {

		// m_layoutConent.setVisibility(View.GONE);

		getMonitorInfoList(m_handler);
	}

	private void loginAllMonitor(final Handler handler) {

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

	/**
	 * 监控预览
	 * 
	 * @param mi
	 */
	private void preview(MonitorInfo mi, int playMode) {
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
				ClientInfo.lLinkMode = playMode << 31 + 3; // bit 31 -- 0,main
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
				updateUI(1);
			}
		} catch (Exception err) {
			Log.e(TAG, "error: " + err.toString());
		}
	};

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
		m_iPlayID = -1;
	}

	/**
	 * 开始监控回放
	 */
	private void startPlayBack() {
		stopPlayBack();
		NET_DVR_TIME struBegin = new NET_DVR_TIME();
		NET_DVR_TIME struEnd = new NET_DVR_TIME();
		struBegin.dwYear = m_year;    //获取年
		struBegin.dwMonth = m_monthOfYear;
		struBegin.dwDay = m_dayOfMonth;
		struBegin.dwHour = m_hourOfDay;
		struBegin.dwMinute = m_minute;
		struBegin.dwSecond = m_second;
		
		struEnd.dwYear = m_year;    //获取年
		struEnd.dwMonth = m_monthOfYear;
		struEnd.dwDay = m_dayOfMonth +1; // 默认播放两个自然日的回放
		struEnd.dwHour = 23;
		struEnd.dwMinute = 59;
		struEnd.dwSecond = 59;
		PlayBack("192.0.1.39", 8000, "admin", "12345", 3, struBegin, struEnd);
	}

	/**
	 * 监控回放
	 * 
	 * @param strIP
	 * @param nPort
	 * @param strUser
	 * @param strPasswd
	 * @param whichOne
	 */
	private void PlayBack(String strIP, int nPort, String strUser,
			String strPasswd, int whichOne, NET_DVR_TIME struBegin,
			NET_DVR_TIME struEnd) {
		try {
			
			if (m_PlayBackId < 0) {
				m_PlayBackId = loginDevice(strIP, nPort, strUser, strPasswd);
			}

			if (m_iPlaybackID < 0) {
				if (m_iPlayID >= 0) {
					Log.i(TAG, "Please stop preview first");
					return;
				}
				PlaybackCallBack fPlaybackCallBack = getPlayerbackPlayerCbf(m_osurfaceView);
				if (fPlaybackCallBack == null) {
					Log.e(TAG, "fPlaybackCallBack object is failed!");
					return;
				}

				

				m_iPlaybackID = HCNetSDK.getInstance().NET_DVR_PlayBackByTime(
						m_PlayBackId, whichOne, struBegin, struEnd);
				if (m_iPlaybackID >= 0) {
					if (!HCNetSDK.getInstance().NET_DVR_SetPlayDataCallBack(
							m_iPlaybackID, fPlaybackCallBack)) {
						Log.e(TAG, "Set playback callback failed!");
						return;
					}
					NET_DVR_PLAYBACK_INFO struPlaybackInfo = null;
					if (!HCNetSDK.getInstance().NET_DVR_PlayBackControl_V40(
							m_iPlaybackID,
							PlaybackControlCommand.NET_DVR_PLAYSTART, null, 0,
							struPlaybackInfo)) {
						Log.e(TAG, "net sdk playback start failed!");
						return;
					}
					updateUI(2);
					updateTimeBar(m_handler);
				} else {
					Log.i(TAG, "NET_DVR_PlayBackByTime failed, error code: "
							+ HCNetSDK.getInstance().NET_DVR_GetLastError());
				}
			}
		} catch (Exception err) {
			Log.e(TAG, "error: " + err.toString());
		}

	}

	private void stopPlayBack() {
		if (!HCNetSDK.getInstance().NET_DVR_StopPlayBack(m_iPlaybackID)) {
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
		m_iPlaybackID = -1;
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
//					Log.e(TAG,
//							"inputData failed with: "
//									+ m_oPlayerSDK.getLastError(m_iPort));
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
}

package com.dahua.netsdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ceres.jailmon.R;
	import com.mm.android.avnetsdk.AVNetSDK;
import com.mm.android.avnetsdk.param.AV_HANDLE;
import com.mm.android.avnetsdk.param.AV_IN_Login;
import com.mm.android.avnetsdk.param.AV_IN_RealPlay;

	import com.mm.android.avnetsdk.param.AV_IN_PTZ;
import com.mm.android.avnetsdk.param.AV_MediaInfo;
import com.mm.android.avnetsdk.param.AV_OUT_Login;
import com.mm.android.avnetsdk.param.AV_OUT_PTZ;
import com.mm.android.avnetsdk.param.AV_OUT_RealPlay;
import com.mm.android.avnetsdk.param.AV_PTZ_Type;
import com.mm.android.avnetsdk.param.AV_PlayPosInfo;
import com.mm.android.avnetsdk.param.AV_Time;
import com.mm.android.avnetsdk.param.ConnectStatusListener;
import com.mm.android.avnetsdk.param.IAV_DataListener;
import com.mm.android.avnetsdk.param.IAV_NetWorkListener;
import com.mm.android.avnetsdk.param.IAV_PlayerEventListener;
import com.mm.android.avnetsdk.param.RecordFileInfo;
import com.mm.android.avplaysdk.IViewListener;
import com.mm.android.avplaysdk.PlayEvent;
import com.mm.android.avplaysdk.render.BasicGLSurfaceView;


	import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

	public class TestActivity  extends Activity implements IViewListener {
		private EditText login_ip, login_port, login_name, login_password;	//登录时设备IP，端口，用户名，密码
		private Button login_button, logout_button, start_button, stop_button;	//登陆、登出、开始监视、停止监视按钮
		//private GridView cloudGrid = null; // 云台控制视图
		private Spinner spinner =null;		//通道选择列表
		private SharedPreferences sharedPreferences;
		private boolean netSDKIsInit = false; // NetSDK是否初始化成功标志
		private AV_HANDLE log_handle = null; // 登陆句柄
		private AV_HANDLE realPlay = null; // 实时监测句柄
		private AV_IN_Login refInParam = null; // 登陆输入参数
		private AV_OUT_Login refOutParam = null; // 登陆输出参数
		private AV_IN_RealPlay playINParam = null; // 实时监视输入参数
		private AV_OUT_RealPlay playOutParam = null; // 实时监视输出参数
		private AV_IN_PTZ cloudInParam=null;	//云台控制输入参数
		private AV_OUT_PTZ cloudOutParam=null;	//云台控制输出参数
		private BasicGLSurfaceView bsView = null; // 播放的视图
		private Activity mActivity;
		private Thread mThread = null;	//连接网络的线程
		
		private ArrayAdapter<String> spinnerAdapter;	
		private int error = 0;
		private int mChannelCount=0;	//连接设备的通道数
		private ArrayList<String> mChannelList = new ArrayList<String>();
		/** 云控制菜单图片 **/
		private final static int[] cloudImage = { R.drawable.goup,
				R.drawable.goright, R.drawable.zoomout, R.drawable.point1,
				R.drawable.circle1, R.drawable.godown, R.drawable.goleft,
				R.drawable.zoomin, R.drawable.point2, R.drawable.circle2 };
		private Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				if(msg.arg1==1){
					if (log_handle != null){
						spinner.setSelection(0);
						spinner.setAdapter(spinnerAdapter);
						
					}
				
					else {
						error = AVNetSDK.AV_GetLastError();
						//String code=refOutParam.emErrorCode.name();
						Log.d("jhe", error + "");
					}
				}
				super.handleMessage(msg);
			}
			
		};

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.fuck);
			init();
			ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < cloudImage.length; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage", cloudImage[i]);
				data.add(map);
			}
			
			/**
			 * 登陆按钮的监听
			 */
			login_button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					doLogin(); // 需要将UI线程与网络连接线程分开
					
				}

			});
			/**
			 * 登出按钮的监听
			 */
			logout_button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (realPlay != null) {
						AVNetSDK.AV_StopRealPlay(realPlay);
						realPlay = null;
					}
					if (log_handle != null) {
						AVNetSDK.AV_Logout(log_handle);
						log_handle = null;
					}
				}
			});
			/**
			 * 开始实时监视的监听
			 */
			start_button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					//构造实时监视输入参数
					playINParam = new AV_IN_RealPlay();
					playINParam.nChannelID = spinner.getSelectedItemPosition(); // 测试零号通道
					playINParam.nSubType = 1;
					playINParam.playView = bsView;
					playINParam.dataListener = new IAV_DataListener() {

						@Override
						public int onData(AV_HANDLE arg0, byte[] arg1, int arg2, int arg3,
								AV_MediaInfo arg4, Object arg5) {
							// TODO Auto-generated method stub
							return 0;
						}
					};
					
					playINParam.netWorkListener = new IAV_NetWorkListener() {

						@Override
						public int onConnectStatus(AV_HANDLE arg0, boolean arg1,
								AV_HANDLE arg2, Object arg3) {
							// TODO Auto-generated method stub
							return 0;
						}
					};
					playINParam.playerEventListener = new IAV_PlayerEventListener() {

						@Override
						public void onResolutionChange(AV_HANDLE arg0, int arg1, int arg2) {
							// TODO Auto-generated method stub

						}

						

						@Override
						public int onPlayPos(AV_HANDLE arg0, AV_PlayPosInfo arg1,
								Object arg2) {
							// TODO Auto-generated method stub
							return 0;
						}

						@Override
						public void onNotSupportedEncode(AV_HANDLE arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onFrameRateChange(AV_HANDLE arg0, int arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onFrameLost(AV_HANDLE arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void playBackFinish(AV_HANDLE arg0) {
							// TODO Auto-generated method stub
							
						}



						@Override
						public void onPlayEvent(AV_HANDLE arg0, PlayEvent arg1) {
							// TODO Auto-generated method stub
							
						}



						@Override
						public void onRecordInfo(Object arg0, AV_Time arg1,
								AV_Time arg2, List<RecordFileInfo> arg3) {
							// TODO Auto-generated method stub
							
						}






					};
					//构造实时监视输出参数
					playOutParam = new AV_OUT_RealPlay();
					if (log_handle != null) // 登陆成功才能播放
						realPlay = AVNetSDK.AV_RealPlay(log_handle, playINParam,
								playOutParam);
					else {
						//Tool.showMsg(mActivity, "请先登陆！");

					}
				}
			});
			/**
			 * 停止实时监视的监听
			 */
			stop_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(realPlay!=null)
					AVNetSDK.AV_StopRealPlay(realPlay); // 停止实时监视
				}
			});
			
		}
		/**
		 * 初始化UI控件
		 */
		private void init() {
			mActivity = this;
			
			netSDKIsInit = AVNetSDK.AV_Startup(mActivity
					.getPackageName());						//初始化网络SDK
			sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
			login_ip = (EditText) findViewById(R.id.edit_ip);
			login_port = (EditText) findViewById(R.id.edit_port);
			login_name = (EditText) findViewById(R.id.username_edit);
			login_password = (EditText) findViewById(R.id.password_edit);
			login_button = (Button) findViewById(R.id.signin_button); // 登入按钮
			logout_button = (Button) findViewById(R.id.logout_button); // 登出按钮
			bsView = (BasicGLSurfaceView) findViewById(R.id.screen);
			start_button = (Button) findViewById(R.id.start_button); // 开始监视按钮
			stop_button = (Button) findViewById(R.id.stop_button); // 停止监视按钮
			//cloudGrid = (GridView) findViewById(R.id.cloud_one);
			bsView.init(TestActivity.this); // 播放视图初始化
			spinner=(Spinner) findViewById(R.id.spinner);
			login_ip.setText(sharedPreferences.getString("ip", ""));
			login_port.setText(sharedPreferences.getString("port", ""));
			login_name.setText(sharedPreferences.getString("username", ""));
			
		}
		/**
		 * 登陆按钮的操作
		 */
		private void doLogin() {
			String ip = login_ip.getText().toString();
			int port = Integer.parseInt(login_port.getText().toString());
			String username = login_name.getText().toString();
			String password = login_password.getText().toString();

			Editor editor = sharedPreferences.edit();// 获取编辑器
			editor.putString("ip", ip);
			editor.putString("port", login_port.getText().toString());
			editor.putString("username", username);
			editor.putString("password", password);
			editor.commit();// 提交修改
			//构造登陆输入参数
			refInParam = new AV_IN_Login();
			refInParam.strDevIP = ip;
			refInParam.nDevPort = port;
			refInParam.strUsername = username;
			refInParam.strPassword = password;
			refInParam.bReconnect = false;
			refInParam.connStatusListener = new ConnectStatusListener() {

				@Override
				public int onConnectStatus(AV_HANDLE arg0, boolean arg1,
						String arg2, int arg3, Object arg4) {
					// TODO Auto-generated method stub
					return 0;
				}
			};
			//构造登陆输出参数
			refOutParam = new AV_OUT_Login();
			

			//登陆时开启线程用于连接到网络，防止UI线程阻塞
			mThread = new Thread(new Runnable() {

				@Override
				public void run() {
					if (netSDKIsInit) { // 如果NetSDK初始化成功才登陆
						log_handle = AVNetSDK.AV_Login(refInParam, refOutParam);// 登录失败返回null，调用AV_GetLastError来获取具体的错误信息
						Log.d("jhe", refOutParam.strDeviceType+":"+refOutParam.nAnalogChnNum+":"+refOutParam.nChannelCount+":"+refOutParam.nDigitalChnNum+":"+refOutParam.nProtocolVersion);
						mChannelCount=refOutParam.nChannelCount;
						for(int i =0;i<mChannelCount;i++)
						{
							String channel = "通道 " +String.format("%02d", i+1);
							mChannelList.add(i,channel);
							
						}
						spinnerAdapter=new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, mChannelList);
						spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						//spinner.setSelection(0);
						Message msg=new Message();	//创建handler消息
						msg.arg1=1;
						handler.sendMessage(msg);	//发消息
						
					}
				}

			});
			mThread.start();
		}

		@Override
		protected void onDestroy() {
			if (realPlay != null) {
				AVNetSDK.AV_StopRealPlay(realPlay); // 停止实时监视
				realPlay = null;
			}
			if (log_handle != null) {
				AVNetSDK.AV_Logout(log_handle);
				log_handle = null;
			}
			AVNetSDK.AV_Cleanup(); // 清理网络SDK
			bsView.uninit();	//反初始化播放视图
			super.onDestroy();
		}

		@Override
		public void onViewMessage(int arg0, SurfaceView arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				//Tool.showMsg(mActivity, "退出...");
				AVNetSDK.AV_StopRealPlay(realPlay); // 停止实时监视
				realPlay = null;
				AVNetSDK.AV_Logout(log_handle);
				log_handle = null;
				//AVNetSDK.AV_Cleanup(); // 清理网络SDK
				finish();
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}

	}
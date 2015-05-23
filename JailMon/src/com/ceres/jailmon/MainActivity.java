/*
 * Copyright (C) 2013-2014 南京矽联信息技术有限公司
 * 
 * Programmed by Jie Zhuang <jiezhuang@163.com>
 */

package com.ceres.jailmon;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.ceres.jailmon.data.SignInResult;
import com.ceres.jailmon.service.ArraignmentService;

/**
 * Activity for Main Menu, which contains all entries of functions.
 */
public class MainActivity extends BaseActivity implements OnClickListener {

	// Inner class to implement a OnClickListener.
	class Button_OnClickListener implements OnClickListener {

		Class<?> m_classActivity;

		Button_OnClickListener(Class<?> classActivity) {
			m_classActivity = classActivity;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, m_classActivity);

			startActivity(intent);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Check version, 1 (Tablet) or 2 (Touch Device)
		boolean bVer2 = (m_AppContext.getVerNum() == 2);
		Display mDisplay = getWindowManager().getDefaultDisplay();
		int W = mDisplay.getWidth();
		int H = mDisplay.getHeight();
		// Use corresponding layout according to version.
		if (bVer2)
			setContentView(R.layout.main2);
		else
			setContentView(R.layout.main);
		TextView backIndexText = (TextView) findViewById(R.id.cancel_index);
		if (W == 1024 && H == 552) {
			backIndexText.setVisibility(View.GONE);
		}
		backIndexText.setOnClickListener(this);
		int[] m_buttonID = { R.id.button01, R.id.button02, R.id.button03,
				R.id.button04, R.id.button05, R.id.button06, R.id.button07,
				R.id.button08, R.id.button09, R.id.button10, R.id.button11,
				R.id.button12 };
		int[] m_buttomDrawableID = {R.drawable.mm_01_up_gray,R.drawable.mm_02_up_gray,
				R.drawable.mm_03_up_gray,R.drawable.mm_04_up_gray,R.drawable.mm_05_down,
				R.drawable.mm_06_up_gray,R.drawable.mm_07_up_gray,R.drawable.mm_08_up_gray,
				R.drawable.mm_09_up_gray,R.drawable.mm_10_up_gray,R.drawable.mm_11_up_gray,
				R.drawable.mm_12_up_gray
		};
		Class<?>[] m_classActivity = {
				ShiftInfoActivity.class,
				bVer2 ? PrisonerInfoGridActivity.class
						: PrisonerInfoActivity.class,
				m_AppContext.getMonitorType() == 0 ? MonitorActivity.class
						: MonitorDHActivity.class, RecordActivity.class,
				bVer2 ? SecurityRBActivity.class : SecurityActivity.class,
				MedicineActivity.class, PatrolMapActivity.class,
				bVer2 ? ThreeFixedTabActivity.class : CallingActivity.class,
				OutInfoActivity.class, PurchaseActivity.class,
				PowerInfoActivity.class, SettingActivity.class, };//LearningActivity

		Button[] button = new Button[m_buttonID.length];

		// Find all buttons and set the click handler.
		for (int i = 0; i < button.length; i++) {
			button[i] = (Button) this.findViewById(m_buttonID[i]);
			button[i].setOnClickListener(new Button_OnClickListener(m_classActivity[i]));
			//button[i].setOnClickListener(null);
		}

		// Invoke SIGN_IN API to notify device ID to server .
//		String root = getIntent().getStringExtra("root");
//	  	String rootArray[] = null;
//	  	if (root.contains(",")) {
//	  		rootArray = root.trim().split(",");
//		}
//	  	for(String rootStr:rootArray){
//	  		if("1".equals(rootStr)){
//	  			button[0].setBackgroundResource(R.drawable.mm_01_up);	
//	  			button[0].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[0]));
//	  		}else if("2".equals(rootStr)){
//	  			button[1].setBackgroundResource(R.drawable.mm_02_up);	
//	  			button[1].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[1]));
//	  		}
//	  		else if("3".equals(rootStr)){
//	  			button[2].setBackgroundResource(R.drawable.mm_03_up);
//	  			button[2].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[2]));
//	  		}
//	  		else if("4".equals(rootStr)){
//	  			button[3].setBackgroundResource(R.drawable.mm_04_up);
//	  			button[3].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[3]));
//	  		}
//	  		else if("5".equals(rootStr)){
//	  			button[4].setBackgroundResource(R.drawable.mm_05_up);
//	  			button[4].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[4]));
//	  		}
//	  		else if("6".equals(rootStr)){
//	  			button[5].setBackgroundResource(R.drawable.mm_06_up);
//	  			button[5].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[5]));
//	  		}
//	  		else if("7".equals(rootStr)){
//	  			button[6].setBackgroundResource(R.drawable.mm_07_up);
//	  			button[6].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[6]));
//	  		}
//	  		else if("8".equals(rootStr)){
//	  			button[7].setBackgroundResource(R.drawable.mm_08_up);
//	  			button[7].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[7]));
//	  		}
//	  		else if("9".equals(rootStr)){
//	  			button[8].setBackgroundResource(R.drawable.mm_09_up);
//	  			button[8].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[8]));
//	  		}
//	  		else if("10".equals(rootStr)){
//	  			button[9].setBackgroundResource(R.drawable.mm_10_up);
//	  			button[9].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[9]));
//	  		}
//	  		else if("11".equals(rootStr)){
//	  			button[10].setBackgroundResource(R.drawable.mm_11_up);
//	  			button[10].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[10]));
//	  		}
//	  		else if("12".equals(rootStr)){
//	  			button[11].setBackgroundResource(R.drawable.mm_12_up);
//	  			button[11].setOnClickListener(new Button_OnClickListener(
//						m_classActivity[11]));
//	  		}
//	  	}
		signin();
		
//		startService(new Intent(ArraignmentService.ACTION));
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// Capture KEYCODE_BACK event, give user a quit prompt for prevent
		// mistake operation.
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showQuitTipDialog();
		}

		return true;
	}

	// Show dialog to ask user whether to quit or not.
	private void showQuitTipDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(getString(R.string.tip_query_exit));

		builder.setTitle(getString(R.string.tip));

		builder.setPositiveButton(R.string.button_ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();

					}
				});

		builder.setNegativeButton(R.string.button_cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}

	private void signin() {
		String server = getSetting().getServer();
		String id = getSetting().getID();

		if (!server.equals("localhost"))
			getSignInResult(m_basehandler, id);
	}

	@Override
	protected void onReceiveSignInResult(SignInResult ret) {

		m_AppContext.setMonitorType(ret.mMonitorType);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.cancel_index:
			this.finish();
			break;

		default:
			break;
		}
	}
}
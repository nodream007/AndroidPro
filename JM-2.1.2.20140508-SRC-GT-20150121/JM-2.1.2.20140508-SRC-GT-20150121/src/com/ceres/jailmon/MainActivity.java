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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.ceres.jailmon.data.SignInResult;

/**
 * Activity for Main Menu, which contains all entries of functions.
 */
public class MainActivity extends BaseActivity {

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

		// Use corresponding layout according to version.
		if (bVer2)
			setContentView(R.layout.main2);
		else
			setContentView(R.layout.main);

		int[] m_buttonID = { R.id.button01, R.id.button02, R.id.button03,
				R.id.button04, R.id.button05, R.id.button06, R.id.button07,
				R.id.button08, R.id.button09, R.id.button10, R.id.button11,
				R.id.button12 };

		Class<?>[] m_classActivity = {
				ShiftInfoActivity.class,
				bVer2 ? PrisonerInfoGridActivity.class
						: PrisonerInfoActivity.class,
				m_AppContext.getMonitorType() == 0 ? MonitorActivity.class
						: MonitorDHActivity.class, LearningActivity.class,
				bVer2 ? SecurityRBActivity.class : SecurityActivity.class,
				MedicineActivity.class, PatrolMapActivity.class,
				bVer2 ? ThreeFixedTabActivity.class : CallingActivity.class,
				OutInfoActivity.class, PurchaseActivity.class,
				PowerInfoActivity.class, RecordActivity.class, };//SettingActivity

		Button[] button = new Button[m_buttonID.length];

		// Find all buttons and set the click handler.
		for (int i = 0; i < button.length; i++) {
			button[i] = (Button) this.findViewById(m_buttonID[i]);
			button[i].setOnClickListener(new Button_OnClickListener(
					m_classActivity[i]));
		}

		// Invoke SIGN_IN API to notify device ID to server .
		signin();
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
}
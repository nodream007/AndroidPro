/*
 * Description: Setting
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import com.ceres.jailmon.data.CommonResult;
import com.ceres.jailmon.data.SignInResult;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends BaseActivity {

	private LinearLayout m_layoutContent, m_layoutSettingRefresh;
	private Button m_btnOK;
	private EditText m_editServer;
	private EditText m_editID;
	private TextView[] m_txtlabel = new TextView[4];
	private TextView m_txtRegNum;
	private AnimationUtil m_aniset;

	private int[] m_textValue = { 5, 15, 30, 60 };
	private SettingData m_data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);

		m_data = getSetting();

		setContentView(R.layout.setting);

		m_layoutContent = (LinearLayout) findViewById(R.id.layoutContent);
		m_layoutSettingRefresh = (LinearLayout) findViewById(R.id.layoutSettingRefresh);

		// Init Server.
		m_editServer = (EditText) findViewById(R.id.editServer);
		m_editServer.setText(m_data.getServer());
		
		//Init ID
		m_editID = (EditText) findViewById(R.id.editID);
		m_editID.setText(m_data.getID());

		// Init serial number.
		m_txtRegNum = (TextView) findViewById(R.id.textView4);

		String strNum = getSetting().loadRegisterNum();
		if (strNum.equals(""))
			m_txtRegNum.setText(R.string.not_registered);
		else
			m_txtRegNum.setText(getString(R.string.serial_number) + strNum);

		// Init Button OK & Back.
		m_btnOK = (Button) findViewById(R.id.buttonOK);

		m_btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String server = m_editServer.getText().toString();

				if (server.equals("")) {
					Toast.makeText(SettingActivity.this,
							getString(R.string.setting_blank_server),
							Toast.LENGTH_SHORT).show();
					return;
				}

				m_data.setServer(server);
				m_data.saveServer();
				
				String id = m_editID.getText().toString();

				if (id.equals("")) {
					Toast.makeText(SettingActivity.this,
							getString(R.string.setting_blank_server),
							Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					if ( !id.equals("NA") )
						getSignInResult(m_basehandler, id);
				}

				m_data.setID(id);
				m_data.saveID();
				
				m_data.saveInteval();
				Toast.makeText(SettingActivity.this,
						getString(R.string.setting_modified),
						Toast.LENGTH_SHORT).show();
				
				signin();
				
				finish();
			}

		});
		
		initBackButton( R.id.buttonBack );

		initRefreshChoiceItems();

		m_layoutContent.startAnimation(m_aniset.m_aniFadeIn);
	}

	void initRefreshChoiceItems() {
		int[] res_id = { R.id.textSetting05s, R.id.textSetting15s,
				R.id.textSetting30s, R.id.textSetting60s };

		for (int i = 0; i < 4; i++) {
			m_txtlabel[i] = (TextView) this.findViewById(res_id[i]);

			if (m_data.getInteval() == m_textValue[i])
				setLabelSelected(i);
		}

		m_txtlabel[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				setLabelSelected(0);
			}
		});

		m_txtlabel[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(1);
			}
		});

		m_txtlabel[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(2);
			}
		});

		m_txtlabel[3].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(3);
			}
		});
	}

	void setLabelSelected(int text) {

		int[] m_labelBK = { R.drawable.refresh_bk_00, R.drawable.refresh_bk_01,
				R.drawable.refresh_bk_02, R.drawable.refresh_bk_03 };

		m_data.setInteval(m_textValue[text]);

		m_layoutSettingRefresh.setBackgroundResource(m_labelBK[text]);
	}

	void signin()
	{
		String server = getSetting().getServer();
		String id = getSetting().getID();
		
		if( !server.equals("localhost") )
			getSignInResult(m_basehandler, id);
	}

	@Override
	protected void onReceiveSignInResult(SignInResult ret) {
		
		m_AppContext.setMonitorType(ret.mMonitorType);		
	}
}

/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.ceres.jailmon.data.*;

public class LoginActivity extends BaseActivity {

	private EditText m_editUser;
	private EditText m_editPasswd;
	private EditText m_editServer;
	private CheckBox m_checkSaveInfo;
	private Button m_buttonLogin, m_buttonMode;
	private LinearLayout m_layoutAdvance;
	private ProgressBar m_progressLogin;
	private LinearLayout m_layoutReg, m_layoutLogin;

	private Button m_buttonOK;
	private EditText m_editReg;
	private EditText m_textDevID, m_editAuthCode;
	private TextView m_textAuthCode;

	int m_nMode = 0; // 0 - Normal, 1 - Advance

	class LoginInfo {
		boolean onoff;
		String user;
		String passwd;
		String root;
	}

	LoginInfo m_logininfo = new LoginInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		m_editUser = (EditText) findViewById(R.id.editUser);
		m_editPasswd = (EditText) findViewById(R.id.editPasswd);
		m_editServer = (EditText) findViewById(R.id.editServer);
		m_checkSaveInfo = (CheckBox) findViewById(R.id.checkBoxSave);
		m_buttonLogin = (Button) findViewById(R.id.buttonLogin);
		m_buttonMode = (Button) findViewById(R.id.buttonMode);
		m_layoutAdvance = (LinearLayout) findViewById(R.id.layoutAdvance);
		m_progressLogin = (ProgressBar) findViewById(R.id.progressLogin);

		m_layoutReg = (LinearLayout) findViewById(R.id.layoutRegister);
		m_layoutLogin = (LinearLayout) findViewById(R.id.layoutLogin);

		m_textDevID = (EditText) findViewById(R.id.editDevID);
		m_editAuthCode = (EditText) findViewById(R.id.editAuthCode);
		m_textAuthCode = (TextView) findViewById(R.id.textRegSN);

		m_progressLogin.setVisibility(View.GONE);

		String strDevID = getDeviceID();
		if (strDevID == null)
			strDevID = "不正确";

		m_textDevID.setText(strDevID);
		m_layoutReg.setVisibility(View.GONE);
		m_layoutLogin.setVisibility(View.VISIBLE);
		String strSN = getSetting().loadRegisterNum();
		String strCode = getSetting().loadAuthCode();
		/*if (!verifyRegisterNum(strCode, strSN)) {
			m_layoutReg.setVisibility(View.VISIBLE);
			m_layoutLogin.setVisibility(View.GONE);
		} else {
			//m_layoutReg.setVisibility(View.GONE);
			//m_layoutLogin.setVisibility(View.VISIBLE);
			finish();
			EnterMainMenu();
		}*/
		m_layoutReg.setVisibility(View.GONE);
		m_layoutLogin.setVisibility(View.VISIBLE);

		m_editReg = (EditText) findViewById(R.id.editReg);
		m_buttonOK = (Button) findViewById(R.id.buttonOK);

		m_buttonOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String strSN = m_editReg.getText().toString();
				String strCode = m_editAuthCode.getText().toString();

				/*if (verifyRegisterNum(strCode, strSN)) {
					getSetting().saveRegisterNum(strSN);
					getSetting().saveAuthCode(strCode);
					//EnterMainMenu();
					m_layoutReg.setVisibility(View.GONE);
					m_layoutLogin.setVisibility(View.VISIBLE);
				} else
					Toast.makeText(LoginActivity.this, "您输入的注册信息不正确",
							Toast.LENGTH_SHORT).show();*/
				//just for test 
				/*m_layoutReg.setVisibility(View.GONE);
				m_layoutLogin.setVisibility(View.VISIBLE);*/
				//EnterMainMenu();
			}
		});

		LoadLoginInfo(m_logininfo);

		m_editServer.setText(getSetting().getServer());

		if (m_logininfo.onoff) {
			m_editUser.setText(m_logininfo.user);
			m_editPasswd.setText(m_logininfo.passwd);
			m_checkSaveInfo.setChecked(true);
		}

		m_buttonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//test
				EnterMainMenu("");
				//Login();
			}
		});

		m_buttonMode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (m_nMode == 0) {
					m_nMode = 1;
					m_layoutAdvance.setVisibility(View.VISIBLE);
					m_buttonMode.setText(R.string.normal);
				} else {
					m_nMode = 0;
					m_layoutAdvance.setVisibility(View.GONE);
					m_buttonMode.setText(R.string.advance);
				}
			}
		});

		m_textAuthCode.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				loadSerialNum();
				return false;
			}

		});

	}

	private void Login() {
		m_logininfo.onoff = m_checkSaveInfo.isChecked();
		m_logininfo.user = m_editUser.getText().toString();
		m_logininfo.passwd = m_editPasswd.getText().toString();

		// Check validity of user input.
		if (m_logininfo.user.equals("")) {
			ShowError(R.string.user_blank);
			m_editUser.requestFocus();
			return;
		}

		if (m_logininfo.passwd.equals("")) {
			ShowError(R.string.passwd_blank);
			m_editUser.requestFocus();
			m_editPasswd.requestFocus();
			return;
		}

		if (m_nMode == 1) {
			String strServer = m_editServer.getText().toString();
			if (strServer != "") {
				getSetting().setServer(strServer);
				getSetting().saveServer();
			}
		}
		if (m_logininfo.onoff)
			SaveLoginInfo(m_logininfo);
		else
			ClearLoginInfo();

		m_progressLogin.setVisibility(View.VISIBLE);
		m_buttonLogin.setEnabled(false);

		getAuthResult(m_basehandler, m_logininfo.user, m_logininfo.passwd);
	}

	protected void onReceiveAuthResult(StringResult ret) {
		m_progressLogin.setVisibility(View.GONE);
		m_buttonLogin.setEnabled(true);
		String result = "";
		if(ret != null){
			result = ret.getM_bOK();
		}
		String[] resultArray = null;
		String resultFlag = "";
		String resultContent = "";
		if (result.contains("|")) {
			resultArray = result.trim().split("\\|");
			if (resultArray != null) {
				resultFlag = resultArray[0];
				if (resultArray.length >= 2) {
					resultContent = resultArray[1];
				}
			}
		}
		if ("true".equals(resultFlag))
			EnterMainMenu(resultContent);
		else{
			Toast.makeText(this, resultContent, Toast.LENGTH_SHORT).show();
		}
	}
	
	
	@Override
	protected void onReceiveFail(AppException exception) {
		m_progressLogin.setVisibility(View.GONE);
		m_buttonLogin.setEnabled(true);

		super.onReceiveFail(exception);
	}
	
	private String[] splitRoot(String resultContent){
		String[] contentArray = null;
		String resultFlag = "";
		if (resultContent.contains(",")) {
			contentArray = resultContent.trim().split(",");
		}
		return contentArray;
	}

	private void EnterMainMenu(String root) {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		intent.putExtra("root", root);
		startActivity(intent);
		finish();
	}

	private void ShowError(int id) {
		Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show();
	}

	private boolean SaveLoginInfo(LoginInfo info) {
		if (info == null || info.user == null || info.passwd == null)
			return false;

		SharedPreferences sp = getPreferences(0);
		SharedPreferences.Editor editor = sp.edit();

		editor.putString("USER", info.user);
		editor.putString("PASS", info.passwd);
		editor.putBoolean("ON", info.onoff);
		editor.commit();

		return true;
	}

	private void ClearLoginInfo() {
		SharedPreferences sp = getPreferences(0);
		SharedPreferences.Editor editor = sp.edit();

		editor.putString("USER", "");
		editor.putString("PASS", "");
		editor.putBoolean("ON", false);
		editor.commit();
	}

	private boolean LoadLoginInfo(LoginInfo info) {
		if (info == null)
			return false;
		SharedPreferences sp = getPreferences(0);
		info.user = sp.getString("USER", "admin");
		info.passwd = sp.getString("PASS", "12345");
		info.onoff = sp.getBoolean("ON", false);

		return true;
	}

	void loadSerialNum() {
		File file = null;

		String dir = Environment.getExternalStorageDirectory().getPath();
		String filepath = dir + "/jailmon_sn.txt";

		try {
			file = new File(filepath);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (final IOException e1) {
			e1.printStackTrace();
		}

		byte[] buffer = new byte[32];
		try {
			final InputStream is = new FileInputStream(file);
			is.read(buffer);
			is.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		String strSN = null;

		strSN = new String(buffer);

		if (strSN != null)
			m_editReg.setText(strSN);
	}

}

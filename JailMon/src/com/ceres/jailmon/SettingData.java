/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

public class SettingData {
	
	SharedPreferences m_sp = null;
	
	public final static int m_version = 2;

	private String m_strServer = "localhost";
	private String m_strID = "NA";
	int m_nInteval = 15;

	SettingData(Activity activity) {
		m_sp = activity.getSharedPreferences("TB", 0);
		loadInteval();
		loadServer();
	}
	
	SettingData(Application app) {
		m_sp = app.getSharedPreferences("TB", 0);
		loadInteval();
		loadServer();
	}

	int getInteval() {
		return m_nInteval;
	}

	void setInteval(int inteval) {
		m_nInteval = inteval;
	}

	void loadInteval() {
		m_nInteval = m_sp.getInt("INTV", m_nInteval);
	}

	void saveInteval() {
		SharedPreferences.Editor editor = m_sp.edit();

		editor.putInt("INTV", m_nInteval);

		editor.commit();
	}

	public String getServer() {
		return m_strServer;
	}

	void setServer(String server) {
		m_strServer = server;
	}

	void loadServer() {
		m_strServer = m_sp.getString("SERVER", m_strServer);
	}

	void saveServer() {
		SharedPreferences.Editor editor = m_sp.edit();

		editor.putString("SERVER", m_strServer);

		editor.commit();
	}

	int loadMapVersion() {
		return m_sp.getInt("MAPVER", 0);
	}

	void saveMapVersion( int ver )
	{
		SharedPreferences.Editor editor = m_sp.edit();

		editor.putInt("MAPVER", ver);

		editor.commit();
	}
	
	
	String loadAuthCode() {
		return m_sp.getString("AUTH", "" );
	}
	
	void saveAuthCode( String code )
	{
		SharedPreferences.Editor editor = m_sp.edit();

		editor.putString("AUTH", code);

		editor.commit();
	}
	
	String loadRegisterNum() {
		return m_sp.getString("REG", "" );
	}
	
	void saveRegisterNum( String num )
	{
		SharedPreferences.Editor editor = m_sp.edit();

		editor.putString("REG", num);

		editor.commit();
	}
	
	String getID() {
		return m_strID;
	}

	void setID(String id) {
		m_strID = id;
	}

	void loadID() {
		m_strID = m_sp.getString("ID", m_strID);
	}

	void saveID() {
		SharedPreferences.Editor editor = m_sp.edit();

		editor.putString("ID", m_strID);

		editor.commit();
	}
}

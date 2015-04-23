/*
 * Description: Security Info
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.ceres.jailmon.data.SecurityInfoList;
import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.CustomAdapter_NormalInfo;

public class SecurityActivity extends BaseActivity {

	private LinearLayout m_layoutItems, m_layoutConent;
	private ListView m_listContent;

	private AnimationUtil m_aniset;

	int[] m_res_id = { R.string.sec_door, R.string.sec_call,
			R.string.sec_patrol, R.string.sec_alert, R.string.sec_breakrule,
			R.string.sec_room };

	CustomAdapter_NormalInfo[] m_adapter = new CustomAdapter_NormalInfo[6];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);
		m_aniset.loadListControl();

		setContentView(R.layout.security);

		m_layoutItems = (LinearLayout) findViewById(R.id.layoutSecurityItems);
		m_layoutConent = (LinearLayout) findViewById(R.id.layoutSecurityContent);

		m_listContent = (ListView) findViewById(R.id.lstSecurityContent);

		initBackButton(R.id.buttonBack);

		initSecurityItemListView();

		loadSecurityInfo();
	}

	void initSecurityItemListView() {

		ListView lvItems = (ListView) findViewById(R.id.lstSecurityItems);

		String[] m_strItems = new String[m_res_id.length];

		for (int i = 0; i < m_res_id.length; i++)
			m_strItems[i] = getString(m_res_id[i]);

		int[] m_imgItems = { R.drawable.list_icon_security_01,
				R.drawable.list_icon_security_02,
				R.drawable.list_icon_security_03,
				R.drawable.list_icon_security_04,
				R.drawable.list_icon_security_05,
				R.drawable.list_icon_security_06 };

		CustomAdapter_MainList adapter = new CustomAdapter_MainList(
				SecurityActivity.this, m_strItems, m_imgItems, 6);

		lvItems.setAdapter(adapter);

		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				showSecurityInfo(arg2);
			}
		});
	}

	
	private void loadSecurityInfo() {

		m_layoutItems.setVisibility(View.GONE);
		m_layoutConent.setVisibility(View.GONE);

		showProcessDialog();

		getSecurityfoList(m_basehandler);
	}

	protected void onReceiveSecurityInfo(SecurityInfoList infolist) {

		closeProcessDialog();
		
		createSecurityInfoAdapter(infolist);

		m_layoutItems.setVisibility(View.VISIBLE);
		m_layoutItems.startAnimation(m_aniset.m_aniFadeIn);		
		
		m_listContent.setSelection(0);
		showSecurityInfo(0);
	}

	private void createSecurityInfoAdapter(SecurityInfoList infolist) {
		if (infolist == null)
			return;

		if (infolist.getListDoor() != null) {
			m_adapter[0] = new CustomAdapter_NormalInfo(SecurityActivity.this,
					infolist.getListDoor());
		}

		if (infolist.getListCall() != null) {
			m_adapter[1] = new CustomAdapter_NormalInfo(SecurityActivity.this,
					infolist.getListCall() );
		}

		if (infolist.getListPatrol() != null) {
			m_adapter[2] = new CustomAdapter_NormalInfo(SecurityActivity.this,
					infolist.getListPatrol());
		}

		if (infolist.getListAlert() != null) {
			m_adapter[3] = new CustomAdapter_NormalInfo(SecurityActivity.this,
					infolist.getListAlert());
		}

		if (infolist.getListBreakrule() != null) {
			m_adapter[4] = new CustomAdapter_NormalInfo(SecurityActivity.this,
					infolist.getListBreakrule());
		}

		if (infolist.getListRoom() != null) {
			m_adapter[5] = new CustomAdapter_NormalInfo(SecurityActivity.this,
					infolist.getListRoom());
		}
	}

	void showSecurityInfo(int item) {
		if (item < 0 && item >= m_res_id.length)
			return;

		if (m_adapter[item] != null) {
			m_listContent.setAdapter(m_adapter[item]);
			
			if( m_layoutConent.getVisibility() != View.VISIBLE )
				m_layoutConent.setVisibility(View.VISIBLE);
				
			// UI effect
			m_layoutConent.startAnimation(m_aniset.m_aniFadeIn);			
			m_listContent.setLayoutAnimation(m_aniset.m_listController);
		}
	}
}

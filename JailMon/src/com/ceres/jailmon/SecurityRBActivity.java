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

import com.ceres.jailmon.data.Cell;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.data.SecurityInfoList;
import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.CustomAdapter_NormalInfo;

public class SecurityRBActivity extends BaseActivity {

	private LinearLayout m_layoutCells, m_layoutConent;
	private ListView m_lvCells, m_lvContent;
	private Cell m_cell = null;
	private CellList m_celllist = null;
	private AnimationUtil m_aniset;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);
		m_aniset.loadListControl();

		setContentView(R.layout.security_rb);

		m_layoutCells = (LinearLayout) findViewById(R.id.layoutCells);
		m_layoutConent = (LinearLayout) findViewById(R.id.layoutSecurityContent);

		m_lvContent = (ListView) findViewById(R.id.lstSecurityContent);

		initBackButton(R.id.buttonBack);

		initCellListView();
		
		loadCellList();
	}

	void initCellListView() {
		m_lvCells = (ListView) findViewById(R.id.lvCells);

		m_lvCells.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				if (m_celllist != null)	
				{
					m_cell = m_celllist.getCell(arg2);
					loadSecurityInfo( m_cell );
				}
			}

		});
	}
	
	private void loadCellList() {

		m_layoutCells.setVisibility(View.GONE);
		m_layoutConent.setVisibility(View.GONE);

		showProcessDialog();

		getCellList(m_basehandler);
	}
	
	protected void onReceiveCellList(CellList celllist) {
		closeProcessDialog();

		if ((m_celllist = celllist) == null)
			return;

		CustomAdapter_MainList adapter = new CustomAdapter_MainList(
				SecurityRBActivity.this, celllist.getList(),
				R.drawable.list_icon_home);

		m_lvCells.setAdapter(adapter);

		m_layoutCells.setVisibility(View.VISIBLE);
		m_layoutCells.startAnimation(m_aniset.m_aniFadeIn);
	}

	
	private void loadSecurityInfo(Cell cell) {

		if (cell == null || cell.getID() == null)
			return;

		m_layoutConent.setVisibility(View.GONE);

		showProcessDialog();

		getSecurityRBInfoList(m_basehandler, m_cell.getID());
	}

	
	protected void onReceiveSecurityRBInfo(SecurityInfoList infolist )
	{
		closeProcessDialog();

		if (infolist == null)
			return;

		CustomAdapter_NormalInfo adapter = new CustomAdapter_NormalInfo(
				SecurityRBActivity.this, infolist.getListBreakrule());

		m_lvContent.setAdapter(adapter);

		m_layoutConent.setVisibility(View.VISIBLE);
		m_layoutConent.startAnimation(m_aniset.m_aniFadeIn);

		m_lvContent.setLayoutAnimation(m_aniset.m_listController);
	}
}

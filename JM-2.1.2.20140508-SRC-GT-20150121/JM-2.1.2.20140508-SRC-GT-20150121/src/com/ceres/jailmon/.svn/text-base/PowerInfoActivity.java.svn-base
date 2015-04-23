/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.CustomAdapter_MainListChoice;
import com.ceres.jailmon.adapter.CustomAdapter_PowerInfo;
import com.ceres.jailmon.data.*;

public class PowerInfoActivity extends BaseActivity {

	private static final int SINGLE_MODE = 0;
	private static final int MULTI_MODE = 1;

	private int m_nMode = SINGLE_MODE;

	private LinearLayout m_layoutCells, m_layoutConent;
	private ListView m_lvCells, m_lvContent;
	private TextView txtSelectAll, txtSelectMulti;

	private AnimationUtil m_aniset;

	Cell m_cell = null;

	private int[] m_listicons = { R.drawable.list_power_00,
			R.drawable.list_power_01, R.drawable.list_power_02,
			R.drawable.list_power_03 };

	private CellList m_celllist = null;

	CustomAdapter_MainList m_adapterSingle;
	CustomAdapter_MainListChoice m_adapterMulti;

	PowerInfoList m_powerinfolist = null, m_listInfoMulti;
	CustomAdapter_PowerInfo m_adapterInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);
		m_aniset.loadListControl();

		setContentView(R.layout.powerinfo);

		m_layoutCells = (LinearLayout) findViewById(R.id.layoutRooms);
		m_layoutConent = (LinearLayout) findViewById(R.id.layoutContent);

		initCellListView();
		
		initContentViews();

		Button btnOK = (Button) findViewById(R.id.buttonOK);

		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				postPowerCtrl();
			}

		});

		initBackButton(R.id.buttonBack);

		m_lvCells.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (getMode() == SINGLE_MODE) {
					arg1.setSelected(true);

					if (m_celllist != null) {
						m_cell = m_celllist.getCell(arg2);
						loadPowerInfoList(m_cell);
					}
				}
			}

		});

		loadCellList();

	}

	void initCellListView() {
		m_lvCells = (ListView) findViewById(R.id.lstRooms);

		txtSelectAll = (TextView) findViewById(R.id.textSelectAll);
		txtSelectMulti = (TextView) findViewById(R.id.textSelectMulti);

		txtSelectAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getMode() == SINGLE_MODE)
					setMode(MULTI_MODE);

				if (m_adapterMulti != null) {
					for (int i = 0; i < m_adapterMulti.getCount(); i++) {
						m_adapterMulti.getIsSelected().put(i, true);
					}

					m_adapterMulti.notifyDataSetChanged();
				}

			}

		});

		txtSelectMulti.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (getMode() == SINGLE_MODE) {
					for (int i = 0; i < m_adapterMulti.getCount(); i++) {
						m_adapterMulti.getIsSelected().put(i, false);
					}
					setMode(MULTI_MODE);
				} else
					setMode(SINGLE_MODE);
			}

		});
	}

	void initContentViews() {
		m_lvContent = (ListView) findViewById(R.id.lstContent);
	}

	int getMode() {
		return m_nMode;
	}

	void setMode(int mode) {
		m_nMode = mode;

		if (m_nMode == SINGLE_MODE) {
			txtSelectMulti.setSelected(false);
			txtSelectAll.setVisibility(View.GONE);
			m_lvCells.setAdapter(m_adapterSingle);
			m_lvCells.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

			m_layoutConent.setVisibility(View.GONE);
		} else {
			txtSelectMulti.setSelected(true);
			txtSelectAll.setVisibility(View.VISIBLE);
			m_lvCells.setAdapter(m_adapterMulti);
			m_lvCells.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

			if (m_celllist.getNum() > 0)
				loadPowerInfoList(m_celllist.getCell(0));
		}
	}

	private void loadCellList() {

		m_layoutCells.setVisibility(View.GONE);
		m_layoutConent.setVisibility(View.GONE);

		m_cell = null;

		showProcessDialog();

		getCellList(m_basehandler);
	}

	@Override
	protected void onReceiveCellList(CellList celllist) {

		closeProcessDialog();

		m_celllist = celllist;

		if (m_celllist != null) {

			m_adapterMulti = new CustomAdapter_MainListChoice(
					PowerInfoActivity.this, m_celllist.getList());

			m_adapterSingle = new CustomAdapter_MainList(
					PowerInfoActivity.this, m_celllist.getList(),
					R.drawable.list_icon_home);

			setMode(SINGLE_MODE);
			m_layoutCells.setVisibility(View.VISIBLE);
			m_layoutCells.startAnimation(m_aniset.m_aniFadeIn);
		}
	}

	void loadPowerInfoList(Cell cell) {

		if (m_cell != null && m_cell.getID() != null) {
			m_layoutConent.setVisibility(View.GONE);

			getPowerInfoList(m_basehandler, m_cell.getID());
		}
	}

	protected void onReceivePowerInfoList(PowerInfoList infolist) {
		m_powerinfolist = infolist;

		if (m_powerinfolist != null) {

			if (getMode() == MULTI_MODE) {
				m_listInfoMulti = m_powerinfolist;

				for (int i = 0; i < m_listInfoMulti.m_list.size(); i++)
					m_listInfoMulti.m_list.get(i).status = false;

				m_adapterInfo = new CustomAdapter_PowerInfo(
						PowerInfoActivity.this, m_listicons,
						m_listInfoMulti.m_list);

				m_lvContent.setAdapter(m_adapterInfo);
			} else
				m_adapterInfo = new CustomAdapter_PowerInfo(
						PowerInfoActivity.this, m_listicons,
						m_powerinfolist.m_list);

			m_lvContent.setAdapter(m_adapterInfo);
			m_lvContent.setLayoutAnimation(m_aniset.m_listController);

			m_layoutConent.setVisibility(View.VISIBLE);
			m_layoutConent.startAnimation(m_aniset.m_aniFadeIn);

		}
	}

	void postPowerCtrl() {
		StringBuilder cids = new StringBuilder();
		StringBuilder param = new StringBuilder();

		Cell cell;

		if (m_nMode == SINGLE_MODE) {
			cids.append(m_cell.getID());
		} else {
			for (int i = 0; i < m_celllist.getNum(); i++) {
				if (m_adapterMulti.getIsSelected().get(i) == true) {
					cell = m_celllist.getCell(i);

					if (cell != null) {
						cids.append(cell.getID());
						cids.append(",");
					}
				}
			}
		}

		PowerInfo power;

		for (int i = 0; i < m_powerinfolist.m_list.size(); i++) {
			power = m_powerinfolist.m_list.get(i);

			if (power != null) {
//				param.append(power.type);
//				param.append(",");
				param.append(power.pointid);
				param.append(",");
				param.append(power.status); 
				param.append(";");
			}
		}

		showProcessDialog();

		postPowerCtrlResult(m_basehandler, cids.toString(), param.toString());
	}

	protected void onPostPowerCtrlResult(PowerCtrlResult ret) {
		closeProcessDialog();

		showSubmitResult(ret != null && ret.m_bOK == true);
	}
}

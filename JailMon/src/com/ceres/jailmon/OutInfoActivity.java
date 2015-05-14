/*
 * Description: Out Info
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
import android.widget.Toast;

import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.CustomAdapter_NormalInfo_ForTX;
import com.ceres.jailmon.adapter.CustomAdapter_NormalInfo_ForTX.btnListener;
import com.ceres.jailmon.data.Cell;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.data.OutInfoList;
import com.ceres.jailmon.data.TXResult;

public class OutInfoActivity extends BaseActivity implements btnListener{

	private LinearLayout m_layoutCells, m_layoutContent;
	private ListView m_lvCells, m_lvContent;
	private AnimationUtil m_aniset;
	private CellList m_celllist = null;
	private OutInfoList m_outinfolist = null;
	private CustomAdapter_NormalInfo_ForTX adapter;
	CustomAdapter_MainList mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);
		m_aniset.loadListControl();

		setContentView(R.layout.outinfo);

		m_layoutCells = (LinearLayout) findViewById(R.id.layoutRooms);
		m_layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

//		initCellListView();
		initContentViews();
		loadOutInfo();
		

		initBackButton(R.id.buttonBack);

//		loadCellList();
	}

	private void initCellListView() {
		m_lvCells = (ListView) findViewById(R.id.lstRooms);

		m_lvCells.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				if (m_celllist != null)
					loadOutInfo();
				mAdapter.setPosition(arg2);
			}
			
		});
	}

	private void initContentViews() {
		m_lvContent = (ListView) findViewById(R.id.lstContent);
	}

	private void loadCellList() {

		m_layoutCells.setVisibility(View.GONE);
		m_layoutContent.setVisibility(View.GONE);

		showProcessDialog();

		getCellList(m_basehandler);
	}

	@Override
	protected void onReceiveCellList(CellList celllist) {
		closeProcessDialog();

		m_celllist = celllist;

		if (m_celllist != null) {

			mAdapter = new CustomAdapter_MainList(
					OutInfoActivity.this, m_celllist.getList(),
					R.drawable.list_icon_home);

			m_lvCells.setAdapter(mAdapter);

			m_layoutCells.setVisibility(View.VISIBLE);
			m_layoutCells.startAnimation(m_aniset.m_aniFadeIn);
		}
	}

	private void loadOutInfo() {

		getOutInfoList(m_basehandler,getSharedPreferences("LoginActivity", 0).getString("USER", "admin"));
	}

	protected void onReceiveOutInfoList(OutInfoList infolist) {
		m_outinfolist = infolist;

		if (m_outinfolist != null) {
			getAllOutInfoPhoto(m_basehandler, m_outinfolist);
			adapter = new CustomAdapter_NormalInfo_ForTX(this.getApplicationContext(),
					m_outinfolist.m_list);
			adapter.setListener(OutInfoActivity.this);
			m_lvContent.setAdapter(adapter);
			m_lvContent.setLayoutAnimation(m_aniset.m_listController);
			m_layoutContent.setVisibility(View.VISIBLE);
			m_layoutContent.startAnimation(m_aniset.m_aniFadeIn);
		}
	}

	protected void onReceiveAllPrisonerPhotoNotify() {
		adapter.notifyDataSetChanged();
	}
	
	protected void onReceiveTXResult(TXResult ret){
		String result = ret.getM_bOK();
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
		if ("true".equals(resultFlag)) {
			Toast.makeText(this, "提讯成功", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "提讯失败", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void submit(String txFlag,String txID) {
		postTxData(m_basehandler, txID, txFlag);
	}
}

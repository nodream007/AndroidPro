/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ceres.jailmon.adapter.CustomAdapter_AgentPhoto;
import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.data.AgentInfo;
import com.ceres.jailmon.data.AgentInfoList;
import com.ceres.jailmon.data.BedInfo;
import com.ceres.jailmon.data.BedInfoList;
import com.ceres.jailmon.data.Cell;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.data.DutyInfo;
import com.ceres.jailmon.data.DutyInfoList;

public class ThreeFixedTabActivity extends BaseActivity {

	private LinearLayout m_layoutCells, m_layoutConent;
	private ListView m_listViewRooms;
	String[] m_strItems = new String[6];

	LinearLayout[] m_layout = new LinearLayout[3];
	TextView[] m_txtlabel = new TextView[3];

	AnimationUtil m_aniset;
	Cell m_cell = null;

	TableLayout m_tablayout_3fixed;
	TableLayout m_tablayout_duty;
	TableLayout m_tablayout_agent;

	TextView m_txtComment;

	private GridView m_grid;
	CustomAdapter_AgentPhoto m_adpter_grid = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);
		m_aniset.loadListControl();

		setContentView(R.layout.threefixedtab);

		m_layoutCells = (LinearLayout) findViewById(R.id.layoutRooms);
		m_layoutConent = (LinearLayout) findViewById(R.id.layoutConetent);		

		initCellListView();
		initContentViews();
		
		initBackButton(R.id.buttonBack);		

		loadCellList();
	}
	
	
	void initCellListView()
	{
		m_listViewRooms = (ListView) findViewById(R.id.lstRooms);
		
		m_listViewRooms.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				if (m_celllist != null) {
					m_cell = m_celllist.getCell(arg2);
					loadCellData(m_cell);
				}
			}

		});
	}

	void initContentViews() {
		
		m_tablayout_3fixed = (TableLayout) findViewById(R.id.tablayout_3fixed);
		m_tablayout_duty = (TableLayout) findViewById(R.id.tablayout_duty);
		m_tablayout_agent = (TableLayout) findViewById(R.id.tablayout_agent);

		m_txtComment = (TextView) findViewById(R.id.txtComment);
		
		m_grid = (GridView) findViewById(R.id.GridAgent);		
		
		m_layout[0] = (LinearLayout) this.findViewById(R.id.layout1);
		m_layout[1] = (LinearLayout) this.findViewById(R.id.layout2);
		m_layout[2] = (LinearLayout) this.findViewById(R.id.layout3);

		m_txtlabel[0] = (TextView) this.findViewById(R.id.textView1);
		m_txtlabel[1] = (TextView) this.findViewById(R.id.textView2);
		m_txtlabel[2] = (TextView) this.findViewById(R.id.textView3);

		setLabelSelected(0);
		setLayoutVisibility(0);

		m_txtlabel[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(0);
				setLayoutVisibility(0);
				m_layoutConent.setBackgroundResource(R.drawable.tab_3_1);
				if (m_bedinfo_list == null)
					loadBedInfoTable(m_cell.getID());
			}
		});

		m_txtlabel[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(1);
				setLayoutVisibility(1);
				m_layoutConent.setBackgroundResource(R.drawable.tab_3_2);
				m_layout[1].startAnimation(m_aniset.m_AniFlipLeftIn);
				if (m_dutyinfo_list == null)
					loadDutyInfoTable(m_cell.getID());
			}
		});

		m_txtlabel[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLabelSelected(2);
				setLayoutVisibility(2);
				m_layoutConent.setBackgroundResource(R.drawable.tab_3_3);
				m_layout[2].startAnimation(m_aniset.m_AniFlipLeftIn);
				if (m_agentinfo_list == null)
					loadAgentInfoTable(m_cell.getID());
			}
		});

	}

	void setLayoutVisibility(int layout) {
		for (int i = 0; i < m_layout.length; i++) {
			if (i == layout)
				m_layout[i].setVisibility(View.VISIBLE);
			else
				m_layout[i].setVisibility(View.GONE);
		}
	}

	void setLabelSelected(int text) {
		for (int i = 0; i < m_txtlabel.length; i++) {
			if (i == text)
				m_txtlabel[i].setSelected(true);
			else
				m_txtlabel[i].setSelected(false);
		}
	}

	void show3FixedTable(BedInfoList infolist) {

		m_tablayout_3fixed.setStretchAllColumns(true);
		m_tablayout_3fixed.removeAllViewsInLayout();

		int num = infolist.m_list.size();
		int lines = 5;

		BedInfo info;

		int n = 0;

		while (n < num) {

			TableRow tablerow = new TableRow(this);

			for (int j = 0; j < lines; j++) {
				info = infolist.m_list.get(n);

				if (info != null) {
					TextView textNum = new TextView(this);
					textNum.setGravity(Gravity.CENTER);
					textNum.setText(info.m_id);
					textNum.setTextSize(22);
					textNum.setBackgroundResource(R.drawable.table_style);
					textNum.setWidth(50);
					textNum.setTextColor(Color.WHITE);
					textNum.setHeight(100);
					tablerow.addView(textNum);

					TextView textName = new TextView(this);
					textName.setGravity(Gravity.CENTER);
					textName.setText(info.m_name);
					textName.setTextSize(22);
					textName.setWidth(100);
					textName.setHeight(100);
					textName.setBackgroundResource(R.drawable.table_style);
					tablerow.addView(textName);
				}

				n++;

				if (n >= num)
					break;
			}

			m_tablayout_3fixed.addView(tablerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}

		if (m_layoutConent.getVisibility() == View.INVISIBLE)
			;
		m_layoutConent.setVisibility(View.VISIBLE);
	}

	void showDutyTable(DutyInfoList infolist) {
		m_tablayout_duty.setStretchAllColumns(true);
		m_tablayout_duty.removeAllViewsInLayout();

		int num = infolist.m_list.size();
		DutyInfo info;

		for (int i = 0; i < num; i++) {
			info = infolist.m_list.get(i);

			TableRow tablerow = new TableRow(this);

			TextView textNum = new TextView(this);
			textNum.setGravity(Gravity.CENTER);
			textNum.setText(info.m_item);
			textNum.setTextSize(22);
			textNum.setBackgroundResource(R.drawable.table_style);
			textNum.setWidth(300);
			textNum.setTextColor(Color.WHITE);
			textNum.setHeight(50);
			tablerow.addView(textNum);

			TextView textName = new TextView(this);
			textName.setGravity(Gravity.CENTER);
			textName.setText(info.m_name);
			textName.setTextSize(22);
			textName.setWidth(500);
			textName.setHeight(50);
			textName.setBackgroundResource(R.drawable.table_style);
			tablerow.addView(textName);

			m_tablayout_duty.addView(tablerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}

	void showAgentTable(AgentInfoList infolist) {
		m_tablayout_agent.setStretchAllColumns(true);
		m_tablayout_agent.removeAllViewsInLayout();

		int num = infolist.m_list.size();
		AgentInfo info;
		String strTime;

		m_txtComment.setText(infolist.m_comment);

		for (int i = 0; i < num; i++) {
			info = infolist.m_list.get(i);

			TableRow tablerow = new TableRow(this);

			TextView textNum = new TextView(this);
			textNum.setGravity(Gravity.CENTER);

			strTime = String.format("%s-%s", info.m_timeStart, info.m_timeEnd);
			textNum.setText(strTime);
			textNum.setTextSize(22);
			textNum.setBackgroundResource(R.drawable.table_style);
			textNum.setWidth(200);
			textNum.setTextColor(Color.WHITE);
			textNum.setHeight(50);
			tablerow.addView(textNum);

			TextView textName = new TextView(this);
			textName.setGravity(Gravity.CENTER);
			textName.setText(info.m_pname);
			textName.setTextSize(22);
			textName.setWidth(180);
			textName.setHeight(50);
			textName.setBackgroundResource(R.drawable.table_style);
			tablerow.addView(textName);

			m_tablayout_agent.addView(tablerow, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}

		m_layoutConent.setVisibility(View.VISIBLE);

		loadCurrentAgent(infolist);

	}

	private CellList m_celllist = null;
	private BedInfoList m_bedinfo_list = null;
	private DutyInfoList m_dutyinfo_list = null;
	private AgentInfoList m_agentinfo_list = null;

	private void loadCellList() {

		m_layoutCells.setVisibility(View.GONE);
		m_layoutConent.setVisibility(View.GONE);

		showProcessDialog();

		getCellList(m_basehandler);
	}

	@Override
	protected void onReceiveCellList(CellList celllist) {
		closeProcessDialog();

		m_celllist = celllist;

		if (m_celllist != null) {
			CustomAdapter_MainList adapter = new CustomAdapter_MainList(
					ThreeFixedTabActivity.this, m_celllist.getList(),
					R.drawable.list_icon_home);

			m_listViewRooms.setAdapter(adapter);

			m_layoutCells.setVisibility(View.VISIBLE);
			m_layoutCells.startAnimation(m_aniset.m_aniFadeIn);
		}
	}

	private void loadCellData(Cell cell) {
		if (cell == null || cell.getID() == null)
			return;
		
		// Clear current data
		m_bedinfo_list = null;
		m_dutyinfo_list = null;
		m_agentinfo_list = null;

		m_layoutConent.setVisibility(View.INVISIBLE);

		m_layoutConent.setBackgroundResource(R.drawable.tab_3_1);
		setLayoutVisibility(0);
		setLabelSelected(0);

		// Load first tab
		loadBedInfoTable(cell.getID());

	}

	private void loadBedInfoTable(String cid) {

		this.showProcessDialog();
		getBedInfoList(m_basehandler, cid);
	}

	protected void onReceiveBedInfoList(BedInfoList infolist) {
		this.closeProcessDialog();
		m_bedinfo_list = infolist;

		if (m_bedinfo_list != null)
			show3FixedTable(m_bedinfo_list);
	}

	private void loadDutyInfoTable(String cid) {

		this.showProcessDialog();
		getDutyInfoList(m_basehandler, cid);
	}

	protected void onReceiveDutyInfoList(DutyInfoList infolist) {
		this.closeProcessDialog();

		m_dutyinfo_list = infolist;

		if (m_dutyinfo_list != null)
			showDutyTable(m_dutyinfo_list);
	}

	private void loadAgentInfoTable(String cid) {

		this.showProcessDialog();

		getAgentInfoList(m_basehandler, cid);
	}

	protected void onReceiveAgentInfoList(AgentInfoList infolist) {
		this.closeProcessDialog();
		m_agentinfo_list = infolist;

		if (m_agentinfo_list != null) {
			showAgentTable(m_agentinfo_list);
		}
	}

	private void loadCurrentAgent(AgentInfoList infolist) {
		m_grid.setAdapter(null);
		m_adpter_grid = null;

		String strCurrent = new SimpleDateFormat("HH:mm").format(new Date());
		int current = AgentInfo.parseTime(strCurrent);
		
		AgentInfo info = null;
		int i = 0;

		for (i = 0; i < infolist.m_list.size(); i++) {
			info = infolist.m_list.get(i);

			if( info.m_tmStart != -1 && info.m_tmEnd != -1 )
			{
				if ( current >= info.m_tmStart && current < info.m_tmEnd )
						break;
			}
		}

		if (i < infolist.m_list.size() && info != null) {
			m_adpter_grid = new CustomAdapter_AgentPhoto(
					ThreeFixedTabActivity.this, info.m_pid_bitmap);

			m_grid.setAdapter(m_adpter_grid);

			loadAgentsPhoto(m_basehandler, info);
		}
	}

	protected void onReceiveAgentPhotoNotify() {
		m_adpter_grid.notifyDataSetChanged();
	}

}
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.CustomAdapter_MainListChoice;
import com.ceres.jailmon.adapter.CustomAdapter_TrainingInfo;
import com.ceres.jailmon.adapter.onPlayListener;
import com.ceres.jailmon.data.*;

public class LearningActivity extends BaseActivity {

	private static final int SINGLE_MODE = 0;
	private static final int MULTI_MODE = 1;

	private int m_nMode = SINGLE_MODE;

	private LinearLayout m_layoutRooms, m_layoutConent;
	private ListView m_listViewRooms;
	private ListView m_listViewTV, m_listViewMovie, m_listViewMusic;
	private TextView txtSelectAll, txtSelectMulti;

	Cell m_cell = null;
	private CellList m_celllist = null;

	CustomAdapter_MainList m_adapterSingle;
	CustomAdapter_MainListChoice m_adapterMulti;

	private AnimationUtil m_aniset;
	CustomAdapter_TrainingInfo m_adapterTV, m_adapterMovie, m_adapterMusic;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);

		setContentView(R.layout.learning);

		m_layoutRooms = (LinearLayout) findViewById(R.id.layoutRooms);
		m_layoutConent = (LinearLayout) findViewById(R.id.layoutContent);

		initBackButton(R.id.buttonBack);

		initCellListView();
		initContentViews();

		loadRooms();
		loadTrainingList();
	}

	int getMode() {
		return m_nMode;
	}

	void setMode(int mode) {
		m_nMode = mode;

		if (m_nMode == SINGLE_MODE) {
			txtSelectMulti.setSelected(false);
			txtSelectAll.setVisibility(View.GONE);
			m_listViewRooms.setAdapter(m_adapterSingle);
			m_listViewRooms.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		} else {
			txtSelectMulti.setSelected(true);
			txtSelectAll.setVisibility(View.VISIBLE);
			m_listViewRooms.setAdapter(m_adapterMulti);
			m_listViewRooms.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		}
	}

	void initCellListView() {
		m_listViewRooms = (ListView) findViewById(R.id.lstRooms);

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

		m_listViewRooms.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				m_cell = m_celllist.getCell(arg2);

				m_layoutConent.setVisibility(View.VISIBLE);
			}

		});
	}

	void initContentViews() {
		m_listViewTV = (ListView) findViewById(R.id.lstTV);
		m_listViewMovie = (ListView) findViewById(R.id.lstMovie);
		m_listViewMusic = (ListView) findViewById(R.id.lstMusic);
	}

	private void loadRooms() {

		m_layoutRooms.setVisibility(View.GONE);
		m_layoutConent.setVisibility(View.GONE);

		this.getCellList(m_basehandler);
	}

	protected void onReceiveCellList(CellList infolist) {
		m_celllist = infolist;

		if (infolist == null)
			return;

		m_adapterMulti = new CustomAdapter_MainListChoice(
				LearningActivity.this, m_celllist.getList());

		m_adapterSingle = new CustomAdapter_MainList(LearningActivity.this,
				m_celllist.getList(), R.drawable.list_icon_home);

		setMode(SINGLE_MODE);
		m_layoutRooms.setVisibility(View.VISIBLE);
		m_layoutRooms.startAnimation(m_aniset.m_aniFadeIn);
	}

	private void loadTrainingList() {

		m_layoutConent.setVisibility(View.GONE);

		showProcessDialog();
		getTrainingList(m_basehandler);
	}

	protected void onReceiveTrainingList(TrainingList infolist) {

		closeProcessDialog();

		if (infolist == null)
			return;

		m_adapterTV = new CustomAdapter_TrainingInfo(LearningActivity.this,
				infolist.m_listTV, m_onPlayListener);
		m_adapterMovie = new CustomAdapter_TrainingInfo(LearningActivity.this,
				infolist.m_listMovie, m_onPlayListener);
		m_adapterMusic = new CustomAdapter_TrainingInfo(LearningActivity.this,
				infolist.m_listMusic, m_onPlayListener);

		m_listViewTV.setDivider(null);
		m_listViewTV.setAdapter(m_adapterTV);

		m_listViewMovie.setDivider(null);
		m_listViewMovie.setAdapter(m_adapterMovie);

		m_listViewMusic.setDivider(null);
		m_listViewMusic.setAdapter(m_adapterMusic);
	}

	onPlayListener m_onPlayListener = new onPlayListener() {

		@Override
		public void onPlay(Training info) {

			StringBuilder cids = new StringBuilder();

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

			showProcessDialog();

			postTrainingResult(m_basehandler, cids.toString(), info.type,
					info.id);
		}
	};

	protected void onPostTrainingResult(TrainingResult ret) {
		closeProcessDialog();

		if (ret != null && ret.m_bOK == true)
			Toast.makeText(getApplicationContext(),
					getString(R.string.submit_ok), Toast.LENGTH_LONG).show();

		else
			Toast.makeText(getApplicationContext(),
					getString(R.string.submit_fail), Toast.LENGTH_LONG).show();
	}
}

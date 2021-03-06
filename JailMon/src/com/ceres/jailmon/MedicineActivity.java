/*
 * Description: Medicine info
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.data.Cell;
import com.ceres.jailmon.data.CellList;
import com.ceres.jailmon.fragment.RountSendMedFragment;
import com.ceres.jailmon.fragment.SendMedCleanHistoryPrisonFragment;
import com.ceres.jailmon.fragment.SendMedHistoryPrisonFragment;
import com.ceres.jailmon.fragment.SendMedOutHistoryPrisonFragment;
import com.ceres.jailmon.fragment.TotalSituationFragment;
/**
 * 医药管理 Activity
 * @author zhounan1
 *
 */
public class MedicineActivity extends BaseFragmentActivity implements
		OnClickListener {

	private LinearLayout m_layoutCells, m_layoutConent;
	private ListView m_lvCells, m_lvContent;
	AnimationUtil m_aniset;
	private Cell m_cell = null;
	private CellList m_celllist = null;
	private static final int TAB_TOTAL_SITUATION = 0;
	public static final int TAB_ROUNDS_SEND_MED = 1;
	public static final int TAB_SEND_MED_HISTORY_IN_PRISON = 2;
	public static final int TAB_SEND_MED_HISTORY_OUT_PRISON = 3;
	public static final int TAB_HEALTH_HISTORY = 4;
	private int mIndex = TAB_TOTAL_SITUATION;
	private FragmentManager mFragmentManager;
	private TotalSituationFragment mTotalSituationFragment;
	private RountSendMedFragment mRountSendMedFragment;
	private SendMedHistoryPrisonFragment mSendMedHistoryInPrisonFragment;
	private SendMedOutHistoryPrisonFragment mSendMedHistoryOutPrisonFragment;
	private SendMedCleanHistoryPrisonFragment mSendMedCleanHistoryPrisonFragment;
	private String cellId;
	private static final String MED_HISTORY_IN_PRISONER = "in";
	private static final String MED_HISTORY_OUT_PRISONER = "out";
	private List<TextView> mViewList = new ArrayList<TextView>();
	private CustomAdapter_MainList mListdapter;
	private List<?> mListItems;
	private TextView mTotolSituationText;
	private TextView mRoundsSendMedText;
	private TextView mSendMedHistoryInPrisonText;
	private TextView mSendMedHistoryOutPrisonText;
	private TextView mHealthHistoryText;
	private FrameLayout mContentFramLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);
		m_aniset.loadListControl();

		setContentView(R.layout.medicine);

		m_layoutCells = (LinearLayout) findViewById(R.id.layoutCells);

		mFragmentManager = getSupportFragmentManager();

		initMedTabView();

		initCellListView();

		loadCellList();

		changeTab();

		initBackButton(R.id.buttonBack);

		mListdapter.setPosition(-1);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * mIndex = getIntent().getIntExtra("index", TAB_TOTAL_SITUATION);
		 * changeTab(); switch (mIndex) { case TAB_TOTAL_SITUATION:
		 * changeBackGround(mTotolSituationText); break; case
		 * TAB_ROUNDS_SEND_MED: changeBackGround(mRoundsSendMedText); break;
		 * case TAB_SEND_MED_HISTORY_IN_PRISON:
		 * changeBackGround(mSendMedHistoryInPrisonText); break; case
		 * TAB_SEND_MED_HISTORY_OUT_PRISON:
		 * changeBackGround(mSendMedHistoryOutPrisonText); break; case
		 * TAB_HEALTH_HISTORY: changeBackGround(mHealthHistoryText); break;
		 * default: break;
		 * 
		 * }
		 */

	}

	protected void initBackButton(int resid) {
		Button btnBack = (Button) findViewById(resid);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
	}

	private void changeTab() {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (mIndex) {
		case TAB_TOTAL_SITUATION:
			m_layoutCells.setVisibility(View.GONE);
			cellId = "";
			mListdapter.setPosition(-1);
			if (mTotalSituationFragment == null) {
				mTotalSituationFragment = new TotalSituationFragment();
				transaction.add(R.id.content_layout, mTotalSituationFragment);
			} else {
				transaction.show(mTotalSituationFragment);
			}
			break;
		case TAB_ROUNDS_SEND_MED:
			m_lvCells.setEnabled(true);
			m_layoutCells.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(cellId)) {
				if (mRountSendMedFragment == null) {
					mRountSendMedFragment = new RountSendMedFragment(cellId);
					transaction.add(R.id.content_layout, mRountSendMedFragment);
				} else {
					mRountSendMedFragment.loadRountSendMedList();
					transaction.show(mRountSendMedFragment);
				}
			}
			break;
		case TAB_SEND_MED_HISTORY_IN_PRISON:
			m_lvCells.setEnabled(true);
			m_layoutCells.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(cellId)) {
				if (mSendMedHistoryInPrisonFragment == null) {
					mSendMedHistoryInPrisonFragment = new SendMedHistoryPrisonFragment(
							cellId, MED_HISTORY_IN_PRISONER, null);
					transaction.add(R.id.content_layout,
							mSendMedHistoryInPrisonFragment);
				} else {
					mSendMedHistoryInPrisonFragment.loadSendMedHistoryList();
					transaction.show(mSendMedHistoryInPrisonFragment);
				}
			}
			break;
		case TAB_SEND_MED_HISTORY_OUT_PRISON:
			m_lvCells.setEnabled(true);
			m_layoutCells.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(cellId)) {
				if (mSendMedHistoryOutPrisonFragment == null) {
					mSendMedHistoryOutPrisonFragment = new SendMedOutHistoryPrisonFragment(
							cellId, MED_HISTORY_OUT_PRISONER, null);
					transaction.add(R.id.content_layout,
							mSendMedHistoryOutPrisonFragment);
				} else {
					mSendMedHistoryOutPrisonFragment.loadSendMedHistoryList();
					transaction.show(mSendMedHistoryOutPrisonFragment);
				}
			}
			break;
		case TAB_HEALTH_HISTORY:
			m_lvCells.setEnabled(true);
			m_layoutCells.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(cellId)) {
				if (mSendMedCleanHistoryPrisonFragment == null) {
					mSendMedCleanHistoryPrisonFragment = new SendMedCleanHistoryPrisonFragment(
							cellId, MED_HISTORY_OUT_PRISONER);
					transaction.add(R.id.content_layout,
							mSendMedCleanHistoryPrisonFragment);
				} else {
					mSendMedCleanHistoryPrisonFragment.loadCleanHistoryList();
					transaction.show(mSendMedCleanHistoryPrisonFragment);
				}
			}
			break;
		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		if (mTotalSituationFragment != null) {
			transaction.hide(mTotalSituationFragment);
		}
		if (mRountSendMedFragment != null) {
			transaction.hide(mRountSendMedFragment);
		}
		if (mSendMedHistoryInPrisonFragment != null) {
			transaction.hide(mSendMedHistoryInPrisonFragment);
		}
		if (mSendMedHistoryOutPrisonFragment != null) {
			transaction.hide(mSendMedHistoryOutPrisonFragment);
		}
		if (mSendMedCleanHistoryPrisonFragment != null) {
			transaction.hide(mSendMedCleanHistoryPrisonFragment);
		}
	}

	private void initMedTabView() {
		mTotolSituationText = (TextView) findViewById(R.id.total_situation);
		mRoundsSendMedText = (TextView) findViewById(R.id.rounds_send_med);
		mSendMedHistoryInPrisonText = (TextView) findViewById(R.id.send_med_history_in_prison);
		mSendMedHistoryOutPrisonText = (TextView) findViewById(R.id.send_med_history_out_prison);
		mHealthHistoryText = (TextView) findViewById(R.id.health_history);
		mTotolSituationText.setOnClickListener(this);
		mRoundsSendMedText.setOnClickListener(this);
		mSendMedHistoryInPrisonText.setOnClickListener(this);
		mSendMedHistoryOutPrisonText.setOnClickListener(this);
		mHealthHistoryText.setOnClickListener(this);
		mViewList.add(mTotolSituationText);
		mViewList.add(mRoundsSendMedText);
		mViewList.add(mSendMedHistoryInPrisonText);
		mViewList.add(mSendMedHistoryOutPrisonText);
		mViewList.add(mHealthHistoryText);
		mContentFramLayout = (FrameLayout) findViewById(R.id.content_layout);
	}

	void initCellListView() {
		m_lvCells = (ListView) findViewById(R.id.lvCells);

		m_lvCells.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("jiayy", "arg2 = " + arg2);
				if (m_celllist != null) {
					m_cell = m_celllist.getCell(arg2);
					cellId = m_cell.getID();
					if (mIndex != TAB_ROUNDS_SEND_MED) {
						mIndex = TAB_ROUNDS_SEND_MED;
					}
					if (mRountSendMedFragment != null) {
						mRountSendMedFragment.setCellId(cellId);
					}
					if (mSendMedHistoryInPrisonFragment != null) {
						mSendMedHistoryInPrisonFragment.setCellId(cellId);
					}
					if (mSendMedHistoryOutPrisonFragment != null) {
						mSendMedHistoryOutPrisonFragment.setCellId(cellId);
					}
					if (mSendMedCleanHistoryPrisonFragment != null) {
						mSendMedCleanHistoryPrisonFragment.setCellId(cellId);
					}
					changeBackGround(mRoundsSendMedText);
					changeTab();
				}
				mListdapter.setPosition(arg2);
			}

		});

		mListdapter = new CustomAdapter_MainList(MedicineActivity.this,
				mListItems, R.drawable.list_icon_home);
	}

	private void loadCellList() {

		m_layoutCells.setVisibility(View.GONE);

		showProcessDialog();

		getCellList(m_basehandler);
	}

	protected void onReceiveCellList(CellList celllist) {
		closeProcessDialog();

		if ((m_celllist = celllist) == null)
			return;
		// cellId = m_celllist.getCell(0).getID();
		// mListItems = celllist.getList();
		mListdapter.setList(celllist.getList());
		m_lvCells.setAdapter(mListdapter);
		mListdapter.notifyDataSetChanged();
		m_layoutCells.startAnimation(m_aniset.m_aniFadeIn);
	}

	private void changeBackGround(View view) {

		for (TextView mView : mViewList) {
			if (mView.equals(view)) {
				mView.setBackgroundResource(R.drawable.menu0_down);
			} else {
				mView.setBackgroundResource(R.drawable.menu0);
			}
		}

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id = view.getId();
		changeBackGround(view);
		switch (id) {
		case R.id.total_situation:
			if (mIndex != TAB_TOTAL_SITUATION) {
				mIndex = TAB_TOTAL_SITUATION;
				changeTab();
			}
			break;
		case R.id.rounds_send_med:
			if (mIndex != TAB_ROUNDS_SEND_MED) {
				mIndex = TAB_ROUNDS_SEND_MED;
				changeTab();

			}
			break;
		case R.id.send_med_history_in_prison:
			if (mIndex != TAB_SEND_MED_HISTORY_IN_PRISON) {
				mIndex = TAB_SEND_MED_HISTORY_IN_PRISON;
				changeTab();
			}
			break;
		case R.id.send_med_history_out_prison:
			if (mIndex != TAB_SEND_MED_HISTORY_OUT_PRISON) {
				mIndex = TAB_SEND_MED_HISTORY_OUT_PRISON;
				changeTab();
			}
			break;
		case R.id.health_history:
			if (mIndex != TAB_HEALTH_HISTORY) {
				mIndex = TAB_HEALTH_HISTORY;
				changeTab();
			}
			break;

		default:
			break;
		}
	}
	
}
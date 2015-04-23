/*
 * Description: Main menu
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.CustomAdapter_PatrolHistory;
import com.ceres.jailmon.data.MapInfo;
import com.ceres.jailmon.data.MapLocationList;
import com.ceres.jailmon.data.PatrolHistory;
import com.ceres.jailmon.data.PatrolResult;
import com.ceres.jailmon.data.Police;

public class PatrolMapActivity extends BaseActivity implements OnClickListener {
	MapView m_mapview;
	MapInfo m_mapinfo;
	MapLocationList m_locationlist = null;
	int m_nInteval;
	private ListView mPoliceListListView;
	private ListView mPatrolHistoryListView;
	private List<Police> mPoliceList;
	private AnimationUtil m_aniset;
	private Police mPolice;
	private TextView confirmPatrolBtn;
	private CustomAdapter_MainList adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_aniset = new AnimationUtil(this);
		getSetting().loadInteval();

		m_nInteval = getSetting().m_nInteval * 1000;

		setContentView(R.layout.patralmap);

		initBackButton(R.id.buttonBack);

		getAllPolice(m_basehandler);

		getPatrolHistory(m_basehandler);

		mPoliceListListView = (ListView) findViewById(R.id.lst_police);
		mPatrolHistoryListView = (ListView) findViewById(R.id.sigin_history);
		confirmPatrolBtn = (TextView) findViewById(R.id.patrol);
		confirmPatrolBtn.setOnClickListener(this);
		mPoliceListListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);

				if (mPoliceListListView != null) {
					mPolice = mPoliceList.get(arg2);
				}
			}

		});

		/*
		 * m_mapview = (MapView) this.findViewById(R.id.mapview);
		 * 
		 * loadMapInfo();
		 */
	}

	private void loadMapInfo() {
		showProcessDialog();
		getMapInfo(m_basehandler);
		getAllPolice(m_basehandler);
	}

	protected void onReceiveAllPoliceResult(List<Police> policeList) {
		mPoliceList = policeList;

		if (mPoliceList != null) {

			adapter = new CustomAdapter_MainList(
					PatrolMapActivity.this, mPoliceList, R.drawable.police_tb2);

			mPoliceListListView.setAdapter(adapter);

			mPoliceListListView.setVisibility(View.VISIBLE);

			mPoliceListListView.startAnimation(m_aniset.m_aniFadeIn);
		}
	}

	protected void onReceivePatrolResult(List<PatrolHistory> patrolList) {

		if (patrolList != null) {

			CustomAdapter_PatrolHistory adapter = new CustomAdapter_PatrolHistory(
					PatrolMapActivity.this, patrolList);

			mPatrolHistoryListView.setAdapter(adapter);

			mPatrolHistoryListView.setVisibility(View.VISIBLE);

			mPatrolHistoryListView.startAnimation(m_aniset.m_aniFadeIn);
		}

		/*
		 * protected void onReceiveMapInfo(MapInfo info) { closeProcessDialog();
		 * 
		 * m_mapinfo = info;
		 * 
		 * if (m_mapinfo != null && m_mapinfo.m_bmp != null ) {
		 * m_mapview.setMap(m_mapinfo.m_bmp);
		 * 
		 * StartUpdateLocation(); } }
		 * 
		 * 
		 * void StartUpdateLocation() { final Handler handler = new Handler();
		 * Runnable runable = new Runnable() { public void run() {
		 * 
		 * if( !PatrolMapActivity.this.isFinishing() ) { LoadMapLocation();
		 * handler.postDelayed(this, m_nInteval); } } };
		 * 
		 * handler.postDelayed(runable, 1000); }
		 * 
		 * void LoadMapLocation() { getMapLocationList(m_basehandler); }
		 * 
		 * protected void onReceiveMapLocation(MapLocationList infolist) {
		 * m_locationlist = infolist;
		 * 
		 * if ( m_mapinfo!=null && m_mapinfo.m_bmp != null)
		 * m_mapview.setLocation(m_locationlist.m_list); }
		 */
	}
	
	
	protected void onReceivePostPatrolResult(PatrolResult patrolResult) {

		if (patrolResult != null) {
			String[] resultArray = null;
			String resultFlag = "";
			String resultContent = "";
			String result = patrolResult.getM_bOK();
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
				Toast.makeText(this, "签到成功", Toast.LENGTH_SHORT).show();
				getPatrolHistory(m_basehandler);
				adapter.setPosition(-1);
			} else {
				Toast.makeText(this, "签到失败,"+resultContent, Toast.LENGTH_SHORT).show();
			}
			
		}

	}

	@Override
		public void onClick(View view) {
			int id = view.getId();
			switch (id) {
			case R.id.patrol:
			if (mPolice != null) {
				if (mPolice.getID() != null) {
					postPatrol(m_basehandler,mPolice.getID());
					
				}
			} else {
				Toast.makeText(this, "请先选择民警再签到", Toast.LENGTH_SHORT).show();
			}
				break;

			default:
				break;
			}
		}
}
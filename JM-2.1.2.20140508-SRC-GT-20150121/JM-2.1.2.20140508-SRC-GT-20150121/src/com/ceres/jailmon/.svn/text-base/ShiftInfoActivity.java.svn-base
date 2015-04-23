/*
 * Description: Shift Info
 * 
 * Programmed by Jie Zhuang
 * 
 * (c) 2013, CeresLink IT Co.Ltd
 */
package com.ceres.jailmon;

import com.ceres.jailmon.adapter.CustomAdapter_MainList;
import com.ceres.jailmon.adapter.CustomAdapter_NormalInfo;
import com.ceres.jailmon.data.ShiftInfoList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ShiftInfoActivity extends BaseActivity {

	private LinearLayout m_layoutDays, m_layoutConent;
	private ListView m_listViewDays, m_listViewContent;
	private AnimationUtil m_aniset;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		m_aniset = new AnimationUtil(this);
		m_aniset.loadListControl();

		setContentView(R.layout.shiftinfo);		

		m_layoutDays = (LinearLayout) findViewById(R.id.layoutWeekdays);
		
		m_layoutConent = (LinearLayout) findViewById(R.id.layoutContent);
		m_layoutConent.setVisibility(View.GONE);

		m_listViewDays = (ListView) findViewById(R.id.lstWeekdays);
		m_listViewContent = (ListView) findViewById(R.id.lstContent);

		initShiftInfoItems();

		initBackButton(R.id.buttonBack);
		
		m_layoutDays.startAnimation(m_aniset.m_aniFadeIn);

	}

	
	private void initShiftInfoItems() {
		int[] res_id = { R.string.shift_day1, R.string.shift_day2,
				R.string.shift_day3, R.string.shift_day4, R.string.shift_day5,
				R.string.shift_day6, R.string.shift_day7 };

		String[] strItems = new String[res_id.length];

		for (int i = 0; i < res_id.length; i++)
			strItems[i] = getString(res_id[i]);

		int[] imgItems = { R.drawable.list_icon_shift_01,
				R.drawable.list_icon_shift_02, R.drawable.list_icon_shift_03,
				R.drawable.list_icon_shift_04, R.drawable.list_icon_shift_05,
				R.drawable.list_icon_shift_06, R.drawable.list_icon_shift_07 };

		CustomAdapter_MainList adapter = new CustomAdapter_MainList(
				ShiftInfoActivity.this, strItems, imgItems, 7);

		m_listViewDays.setAdapter(adapter);

		m_listViewDays.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				arg1.setSelected(true);
				int nItemNum = arg2 + 1;

				loadShiftInfoList(Integer.toString(nItemNum));
			}

		});
	}

	private void loadShiftInfoList(final String day) {
		m_layoutConent.setVisibility(View.GONE);

		showProcessDialog();
		
		getShiftInfoList(m_basehandler, day);
	}

	protected void onReceiveShiftInfoList(ShiftInfoList infolist) {
		
		closeProcessDialog();
		
		if (infolist == null)
			return;

		CustomAdapter_NormalInfo adapter = new CustomAdapter_NormalInfo(
				ShiftInfoActivity.this, infolist.getList());

		m_listViewContent.setAdapter(adapter);

		m_layoutConent.setVisibility(View.VISIBLE);

		m_layoutConent.startAnimation(m_aniset.m_aniFadeIn);
		m_listViewContent.setLayoutAnimation(m_aniset.m_listController);
	}
}


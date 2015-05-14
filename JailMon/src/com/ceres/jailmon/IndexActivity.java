package com.ceres.jailmon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ceres.jailmon.adapter.CustomAdapter_IndexMajorSecurity;
import com.ceres.jailmon.adapter.CustomAdapter_index;
import com.ceres.jailmon.data.IndexDuty;
import com.ceres.jailmon.data.IndexMajorSecurity;
import com.ceres.jailmon.data.Police;
import com.ceres.jailmon.data.RoomIndex;
import com.ceres.jailmon.data.Warning;

public class IndexActivity extends BaseActivity implements OnClickListener {
	private ListView mCustomerListView;
	private ListView mMajorSecurityListView;
	private List<RoomIndex> mRoomIndexList;
	private Button mBtn01;
	private Button mBtn02;
	private RoomIndex mRoomIndex;
	private Button mMainBtn;
	private CustomAdapter_index mIndexAdapter;
	private CustomAdapter_IndexMajorSecurity mIndexMajorAdapter;
	private List<Warning> mWarningList;
	private List<IndexMajorSecurity> mIndexOutPrisonerList;
	private TextView mCellNumText;
	private TextView mOutNumText;
	private TextView mArraText;
	private TextView mRiskDegreeText;
	private TextView mSeraialNumText;
	private TextView mCellTypeText;
	private TextView mCellNameText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		mCustomerListView = (ListView) findViewById(R.id.warning_list);
		getIndexData(m_basehandler);
		mBtn01 = (Button) findViewById(R.id.btn1);
		mBtn02 = (Button) findViewById(R.id.btn2);
		mMainBtn = (Button) findViewById(R.id.main_btn);
		mBtn01.setOnClickListener(this);
		mBtn02.setOnClickListener(this);
		mMainBtn.setOnClickListener(this);
		mCellNumText = (TextView) findViewById(R.id.cell_num_info);
		mArraText = (TextView) findViewById(R.id.wait_array_num_info);
		mRiskDegreeText = (TextView) findViewById(R.id.risk_info);
		mSeraialNumText = (TextView) findViewById(R.id.security_num_info);
		mOutNumText = (TextView) findViewById(R.id.cell_out_num_info);
		mCellTypeText = (TextView) findViewById(R.id.cell_type_info);
		mCellNameText = (TextView) findViewById(R.id.cell_name);
		mMajorSecurityListView = (ListView) findViewById(R.id.major_security_list);
		TextView currentTime = (TextView) findViewById(R.id.curren_time);
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日    HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String time = formatter.format(curDate);
		currentTime.setText(time);
	}

	protected void onReceiveIndexDataResult(final List<RoomIndex> indexDataList) {
		mRoomIndexList = indexDataList;
		if (!mRoomIndexList.isEmpty()) {
			if (mRoomIndexList.size() == 1) {
				mBtn02.setVisibility(View.INVISIBLE);
			} else {
				mBtn02.setVisibility(View.VISIBLE);
				RoomIndex roomIndex = mRoomIndexList.get(1);
				if (roomIndex != null) {
					String room2Name = roomIndex.getName();
					mBtn02.setText(room2Name);
				}
			}
			mRoomIndex = mRoomIndexList.get(0);
			refreshView(mRoomIndex);
		}
		// List<Warning> warningList = mRoomIndex.getWarningList();
		// CustomAdapter_index waringAdapter = new
		// CustomAdapter_index(this,warningList);
	}

	private void refreshView(RoomIndex mRoomIndex) {
		String cellNum = mRoomIndex.getNum();
		mCellNumText.setText(cellNum);
		String outRoomNum = mRoomIndex.getOutRoomNum();
		mOutNumText.setText(outRoomNum);
		String arraNum = mRoomIndex.getForArraNum();
		mArraText.setText(arraNum);
		String serialNum = mRoomIndex.getSecurityNum();
		mSeraialNumText.setText(serialNum);
		String reskDegree = mRoomIndex.getRiskDegree();
		mRiskDegreeText.setText(reskDegree);
		String roomType = mRoomIndex.getRoomType();
		mCellTypeText.setText(roomType);
		String roomName = mRoomIndex.getName();
		mCellNameText.setText(roomName);
		mBtn01.setText(roomName);
		mWarningList = mRoomIndex.getWarningList();
		if (mWarningList != null && mWarningList.size() != 0) {
			mIndexAdapter = new CustomAdapter_index(this, mWarningList);
			mCustomerListView.setAdapter(mIndexAdapter);

		}
		mIndexOutPrisonerList = mRoomIndex.getMajorSecurity().getOurPrisoner();
		if (mIndexOutPrisonerList != null && mIndexOutPrisonerList.size() != 0) {
			getAllMajorSecurityPhoto(m_basehandler, mIndexOutPrisonerList);
			mIndexMajorAdapter = new CustomAdapter_IndexMajorSecurity(this,
					mIndexOutPrisonerList);
			mMajorSecurityListView.setAdapter(mIndexMajorAdapter);
			List<Police> policeList = mRoomIndex.getPoliceList();
			if (!policeList.isEmpty()) {
				Police police1 = policeList.get(0);
				Police police2 = policeList.get(1);
				TextView mainPoliceText = (TextView) findViewById(R.id.main_police_pic_name);
				TextView helpPoliceText = (TextView) findViewById(R.id.help_police_pic_name);
				if (police1 != null) {
					mainPoliceText.setText(police1.getName());
				}
				if (police2 != null) {
					helpPoliceText.setText(police2.getName());
				}
			}

			List<IndexDuty> dutyList = mRoomIndex.getDutyList();
			if (dutyList != null && dutyList.size() > 0) {
				IndexDuty duty1 = dutyList.get(0);
				IndexDuty duty2 = dutyList.get(1);
				TextView duty1NameText = (TextView) findViewById(R.id.duty_police_name);
				TextView duty2NameText = (TextView) findViewById(R.id.duty_police_name_sencond);
				// TextView time1Text = (TextView)
				// findViewById(R.id.duty_police_time_info);
				// TextView time2Text = (TextView)
				// findViewById(R.id.duty_police_time_info_sencond);

				if (duty1 != null) {
					String name1 = duty1.getName();
					String time1 = duty1.getTime();
					duty1NameText.setText(name1);
					// time1Text.setText(time1);
				}
				if (duty2 != null) {
					String name2 = duty2.getName();
					String time2 = duty2.getTime();
					duty2NameText.setText(name2);
					// time2Text.setText(time2);
				}
			}
		}
	}

	protected void onReceiveMajorSecurityPhotoNotify() {
		mIndexMajorAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();

		switch (id) {
		case R.id.btn1:
			mRoomIndex = mRoomIndexList.get(0);
			mWarningList = mRoomIndex.getWarningList();
			refreshView(mRoomIndex);
			/*
			 * mBtn01.setBackgroundColor(getResources().getColor(R.color.yellow))
			 * ; mBtn02.setBackgroundResource(R.drawable.left_link_before_bg);
			 */
			break;
		case R.id.btn2:
			mRoomIndex = mRoomIndexList.get(1);
			refreshView(mRoomIndex);
			/*
			 * mBtn01.setBackgroundResource(R.drawable.left_link_before_bg);
			 * mBtn02
			 * .setBackgroundColor(getResources().getColor(R.color.yellow));
			 */
			break;
		case R.id.main_btn:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		default:
			break;
		}
	}

}

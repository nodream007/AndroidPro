package com.ceres.jailmon;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ceres.jailmon.adapter.CustomAdapter_index;
import com.ceres.jailmon.data.RoomIndex;
import com.ceres.jailmon.data.Warning;

public class IndexActivity extends BaseActivity implements OnClickListener {
 private CustomerListView mCustomerListView;
 private List<RoomIndex> mRoomIndexList;
 private Button mBtn01;
 private Button mBtn02;
 private RoomIndex mRoomIndex;
 private CustomAdapter_index mIndexAdapter;
 private List<Warning> mWarningList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		mCustomerListView = (CustomerListView) findViewById(R.id.warning_list);
		getIndexData(m_basehandler);
		mBtn01 = (Button)findViewById(R.id.btn1);
		mBtn02 = (Button)findViewById(R.id.btn2);
		mBtn01.setOnClickListener(this);
		mBtn02.setOnClickListener(this);
	}


	protected void onReceivePrisonerList(final List<RoomIndex> indexDataList) {
		mRoomIndexList = indexDataList;
		if(!mRoomIndexList.isEmpty()){
			if(mRoomIndexList.size() == 1){
			mBtn02.setVisibility(View.INVISIBLE);
			mRoomIndex = mRoomIndexList.get(0);
			}else{
				mBtn02.setVisibility(View.VISIBLE);
			}
			mWarningList = mRoomIndex.getWarningList();
			mIndexAdapter = new CustomAdapter_index(this,mWarningList);
			mCustomerListView.setAdapter(mIndexAdapter);
		} 
		//List<Warning> warningList = mRoomIndex.getWarningList();
		//CustomAdapter_index waringAdapter = new CustomAdapter_index(this,warningList);
	}


	@Override
	public void onClick(View view) {
		int id = view.getId();
		
		switch (id) {
		case R.id.btn1:
			mRoomIndex = mRoomIndexList.get(0);
			mWarningList = mRoomIndex.getWarningList();
			if(mIndexAdapter != null){
				mIndexAdapter.setData(mWarningList);
			}
			break;
		case R.id.btn2:
			mRoomIndex = mRoomIndexList.get(1);
			mWarningList = mRoomIndex.getWarningList();
			mIndexAdapter.setData(mWarningList);
			break;
		default:
			break;
		}
	}

}

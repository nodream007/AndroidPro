package com.ceres.jailmon.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ceres.jailmon.AppContext;
import com.ceres.jailmon.MedicineActivity;
import com.ceres.jailmon.R;
import com.ceres.jailmon.adapter.CustomAdapter_MedRoundSendMedcineImportPatient;
import com.ceres.jailmon.adapter.CustomAdapter_MedRoundSendMedcineTotalPatient;
import com.ceres.jailmon.adapter.CustomAdapter_PolicePhoto;
import com.ceres.jailmon.adapter.CustomAdapter_illTypePerson;
import com.ceres.jailmon.data.MedRoundSendImportPatient;
import com.ceres.jailmon.data.MedRoundSendMedTotalPatient;
import com.ceres.jailmon.data.MedRoundSendMedicineList;
import com.ceres.jailmon.data.TotalPrisonSituationList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class RountSendMedFragment extends BaseFragment {
	private View mContentView;
	private MedRoundSendMedicineList mMedRoundSendMedicineList = null;
	ListView mPatientTypeList;
	private TextView mTotalNumTextView;
	private GridView mPatientNameListGridView;
	private String mCid;
	private CustomAdapter_MedRoundSendMedcineTotalPatient totalAdapter;

	public RountSendMedFragment(String roomId) {
		mCid = roomId;
	}

	public RountSendMedFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.med_round_send_med_fragment, null);
		m_AppContext = (AppContext) this.getActivity().getApplication();
		initMedCategoryViews(mContentView);
		loadRountSendMedList();
		return mContentView;
	}

	private void initMedCategoryViews(View mContentView) {
		mPatientTypeList = (ListView) mContentView
				.findViewById(R.id.important_patient_list);
		mTotalNumTextView = (TextView) mContentView
				.findViewById(R.id.prison_total_num);
		mPatientNameListGridView = (GridView) mContentView
				.findViewById(R.id.prison_total_person_grid);
	}

	public void loadRountSendMedList() {
		getRoundsSendMedList(m_basehandler, mCid);
	}

	protected void onReceiveMedRoundSendMessageList(
			MedRoundSendMedicineList medRoundSendMedicineList) {
		String lockNum = medRoundSendMedicineList
				.getMedRoundSendMedTotalPatientNum();
		List<MedRoundSendImportPatient> medImportPatientList = new ArrayList<MedRoundSendImportPatient>();
		List<MedRoundSendMedTotalPatient> medTotalPatietList = new ArrayList<MedRoundSendMedTotalPatient>();
		mTotalNumTextView.setText(lockNum);
		medImportPatientList = medRoundSendMedicineList
				.getMedImportPatientList();
		medTotalPatietList = medRoundSendMedicineList.getMedTotalPatietList();
		// getAllMedicineTotalPatientPhoto(m_basehandler, medTotalPatietList);
		CustomAdapter_MedRoundSendMedcineImportPatient importAdapter = new CustomAdapter_MedRoundSendMedcineImportPatient(
				getActivity(), medImportPatientList);
		mPatientTypeList.setAdapter(importAdapter);
		totalAdapter = new CustomAdapter_MedRoundSendMedcineTotalPatient(
				getActivity(), medTotalPatietList, mCid);

		mPatientNameListGridView.setAdapter(totalAdapter);
	}

	protected void onReceiveAllPrisonerPhotoNotify() {
		totalAdapter.notifyDataSetChanged();
	}

	public void setCellId(String cellId) {
		mCid = cellId;
	}
}

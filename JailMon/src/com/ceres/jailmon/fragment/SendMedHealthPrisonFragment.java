package com.ceres.jailmon.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ceres.jailmon.AppContext;
import com.ceres.jailmon.R;
import com.ceres.jailmon.data.MedicineHealthyInfo;

@SuppressLint({ "ValidFragment", "NewApi" })
public class SendMedHealthPrisonFragment extends BaseFragment {

	private View mContentView;
	private TextView mHealthyTime;
	private TextView mHealthyType;
	private TextView mHeight;
	private TextView mWeight;
	private TextView mBloodType;
	private TextView mBloodPre;
	private TextView mPoliceName;
	private TextView mMyMedHis;//本人病史
	private TextView mFamilyMedHis;//家族病史
	private TextView mDoctorSug;//医生意见
	private TextView mRemark;
	
	private String mPId;

	public SendMedHealthPrisonFragment(String roomId, String medType,
			String peopleId) {
		mPId = peopleId;
	}

	public SendMedHealthPrisonFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.med_send_medicine_in_health_fragment, null);
		m_AppContext = (AppContext) this.getActivity().getApplication();
		initSendMedHealthViews();
		loadSendMedHistoryList();
		return mContentView;
	}

	private void initSendMedHealthViews() {
		mHealthyTime = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_time);
		mHealthyType = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_type);
		mHeight = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_height);
		mWeight = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_weight);
		mBloodType = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_blood_type);
		mBloodPre = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_blood_pre);
		mPoliceName = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_police_name);
		mMyMedHis = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_my_his);
		mFamilyMedHis = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_family_his);
		mDoctorSug = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_doctor_sug);
		mRemark = (TextView) mContentView.findViewById(R.id.send_med_in_healthy_remark);
	}

	public void loadSendMedHistoryList() {
		getSendMedHealthy(m_basehandler, mPId);
	}

	protected void onReceiveMedicineHealthyInfo(MedicineHealthyInfo medicineHealthyInfo) {
		mHealthyTime.setText(medicineHealthyInfo.getHealthyTime());
		mHealthyType.setText(medicineHealthyInfo.getHealthyType());
		mHeight.setText(medicineHealthyInfo.getHeight());
		mWeight.setText(medicineHealthyInfo.getWeight());
		mBloodType.setText(medicineHealthyInfo.getBloodType());
		mBloodPre.setText(medicineHealthyInfo.getBloodPre());
		mPoliceName.setText(medicineHealthyInfo.getPoliceName());
		mMyMedHis.setText(medicineHealthyInfo.getMyMedHis());
		mFamilyMedHis.setText(medicineHealthyInfo.getFamilyMedHis());
		mDoctorSug.setText(medicineHealthyInfo.getDoctorSug());
		mRemark.setText(medicineHealthyInfo.getRemark());
	}
}

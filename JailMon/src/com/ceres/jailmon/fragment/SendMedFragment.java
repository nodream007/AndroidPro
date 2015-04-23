package com.ceres.jailmon.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.ceres.jailmon.AppContext;
import com.ceres.jailmon.R;
import com.ceres.jailmon.adapter.CustomAdapter_MedSendMedicine;
import com.ceres.jailmon.data.Medicine;
import com.ceres.jailmon.data.MedicineTypeAndNamesListParse;
import com.ceres.jailmon.data.MedicineUnit;
import com.ceres.jailmon.data.SendMedicineData;

@SuppressLint("ValidFragment")
public class SendMedFragment extends BaseFragment {
	private View mContentView;
	private String mPid;
	private String mCid;
	private Spinner medicineTypeSpinner;
	private ListView mMedicineListView;
	private GridView mMedicineGridView;
	private List<String> mMedicineTypeList = new ArrayList<String>();
	private List<Medicine> mMedicineList = new ArrayList<Medicine>();
	private List<String> mMedicineUnitsList = new ArrayList<String>();
	private CustomAdapter_MedSendMedicine mSendMedicineAdapter;

	public SendMedFragment(String cid, String pid) {
		mPid = pid;
		mCid = cid;
	}

	public SendMedFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.med_send_medicine_fragment, null);
		m_AppContext = (AppContext) this.getActivity().getApplication();
		initSendMedViews(mContentView);
		loadMedicineData();
		return mContentView;
	}

	public void loadMedicineData() {
		getMedicineUnits(m_basehandler);
		getSendMedTypeAndNames(m_basehandler);
	}

	private void initSendMedViews(View mContentView) {
		medicineTypeSpinner = (Spinner) mContentView
				.findViewById(R.id.medicine_type_spinner);
		mMedicineListView = (ListView) mContentView
				.findViewById(R.id.medicine_name_grid);
//		mMedicineGridView = (GridView) mContentView
//				.findViewById(R.id.medicine_name_grid);

	}

	protected void onReceiveMedicineNameAndTypes(
			MedicineTypeAndNamesListParse mediceAndNamesListParse) {
		final Map<String, List<Medicine>> medicineTypeIncludeMedicineMap = mediceAndNamesListParse.mMedicineTypeIncludeMedicineMap;
		for (String key : medicineTypeIncludeMedicineMap.keySet()) {
			mMedicineTypeList.add(key);
		}
		ArrayAdapter<String> medicineTypeAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				mMedicineTypeList);
		medicineTypeSpinner.setAdapter(medicineTypeAdapter);
		medicineTypeSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,
							int position, long arg3) {
						if (!mMedicineTypeList.isEmpty()) {
							String medicineType = mMedicineTypeList
									.get(position);
							mMedicineList = medicineTypeIncludeMedicineMap
									.get(medicineType);
							mSendMedicineAdapter = new CustomAdapter_MedSendMedicine(
									getActivity(), mMedicineList,
									mMedicineUnitsList,medicineType);
							mMedicineListView.setAdapter(mSendMedicineAdapter);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});
	}

	protected void onReceiveMedicineUnitList(MedicineUnit unit) {
		mMedicineUnitsList = unit.getMedicineUnitList();
	}

	public List<SendMedicineData> getSendMedicine() {
		return mSendMedicineAdapter.getSendMedicine();
	}
}

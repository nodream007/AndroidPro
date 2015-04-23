package com.ceres.jailmon.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.ceres.jailmon.AppContext;
import com.ceres.jailmon.R;
import com.ceres.jailmon.adapter.CustomAdapter_MedIllType;
import com.ceres.jailmon.adapter.CustomAdapter_illTypePerson;
import com.ceres.jailmon.data.TotalPrisonSituationList;

public class TotalSituationFragment extends BaseFragment {
	private View mContentView;
	private TotalPrisonSituationList mTotalPrisonSituationList = null;
	ListView mMedCategoryList;
	private TextView mLockNumTextView;
	private TextView mSerailIllNumTextView;
	private GridView illTypePersonGridView;
	List<String> illTypeList = new ArrayList<String>();
	Map<String, List<String>> illTypeIncludeMedicineMap = new HashMap<String, List<String>>();
	private CustomAdapter_MedIllType mCategoryListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.med_total_situation_fragment, null);
		m_AppContext = (AppContext) this.getActivity().getApplication();
		initMedCategoryViews(mContentView);
		loadMedicineInfo();
		return mContentView;
	}

	private void initMedCategoryViews(View mContentView) {
		mMedCategoryList = (ListView) mContentView.findViewById(R.id.ill_type);
		mSerailIllNumTextView = (TextView) mContentView
				.findViewById(R.id.lock_serial_num);
		mLockNumTextView = (TextView) mContentView.findViewById(R.id.lock_num);
		illTypePersonGridView = (GridView) mContentView
				.findViewById(R.id.ill_type_person_grid);

		mMedCategoryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				mCategoryListAdapter.setPostion(position);
				mCategoryListAdapter.notifyDataSetChanged();
				if (illTypeList.isEmpty())
					return;
				String illType = illTypeList.get(position);
				List<String> personList = new ArrayList<String>();
				personList = illTypeIncludeMedicineMap.get(illType);
				CustomAdapter_illTypePerson illTypePersonAdapter = new CustomAdapter_illTypePerson(
						TotalSituationFragment.this.getActivity(), personList);
				illTypePersonGridView.setAdapter(illTypePersonAdapter);
			}
		});
	}

	public void loadMedicineInfo() {
		getTotalPrisonSituationList(m_basehandler);
	}

	protected void onReceiveTotalPrisonSituationList(
			TotalPrisonSituationList totalPrisonSituationList) {
		String lockNum = totalPrisonSituationList.mPrisonNum;
		String serialNum = totalPrisonSituationList.mSeriouslyIllPrisonNum;
		mLockNumTextView.setText(lockNum);
		mSerailIllNumTextView.setText(serialNum);
		illTypeIncludeMedicineMap = totalPrisonSituationList.mIllTypeIncludeMedicineMap;
		Set<String> key = illTypeIncludeMedicineMap.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			illTypeList.add(s);
		}
		if ((mTotalPrisonSituationList = totalPrisonSituationList) == null)
			return;

		mCategoryListAdapter = new CustomAdapter_MedIllType(
				this.getActivity(),illTypeList,
				null);

		mCategoryListAdapter.setPostion(-1);
		mMedCategoryList.setAdapter(mCategoryListAdapter);
		mCategoryListAdapter.notifyDataSetChanged();
	}
	
}

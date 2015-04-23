package com.ceres.jailmon;

import android.content.Intent;
import android.os.Bundle;

public class MonitorCommonActivity extends BaseActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Class<?> m_classActivity = null;
		
		if( m_AppContext.getMonitorType() == 1 )
			m_classActivity = MonitorDHActivity.class;
		else
			m_classActivity = MonitorActivity.class;
		
		Intent intent = new Intent(this, m_classActivity );

		startActivity(intent);
		
		finish();
	}	
}

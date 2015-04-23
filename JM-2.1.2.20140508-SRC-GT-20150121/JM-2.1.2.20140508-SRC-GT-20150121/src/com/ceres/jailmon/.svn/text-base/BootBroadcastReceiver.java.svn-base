package com.ceres.jailmon;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(ACTION)) {
			Intent main_intent = new Intent(context, LoginActivity.class);
			main_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(main_intent);
		}
	}
}

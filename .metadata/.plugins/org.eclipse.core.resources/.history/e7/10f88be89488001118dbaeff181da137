/*
 * Author : ErVaLt / techwavedev.com
 * Description : TabLayout Andorid App
 */
package com.example.tablayout;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

public class TabLayout extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost

		Intent intent = new Intent(this, MainActivity.class);
		tabHost.addTab(tabHost.newTabSpec("Main")
				.setIndicator("Main", res.getDrawable(R.drawable.ic_tab_main))
				.setContent(intent));

		Intent intent2 = new Intent(this, SetupActivity.class);
		tabHost.addTab(tabHost
				.newTabSpec("Setup")
				.setIndicator("Setup", res.getDrawable(R.drawable.ic_tab_setup))
				.setContent(intent2));
		tabHost.setCurrentTab(0);

		// Set tabs Colors
		tabHost.setBackgroundColor(Color.BLACK);
		tabHost.getTabWidget().setBackgroundColor(Color.BLACK);

	}
}
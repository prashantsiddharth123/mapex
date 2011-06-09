package com.androidnatic.maps.demo.activities;

import android.os.Bundle;

import com.androidnatic.maps.HeatMapOverlay;
import com.androidnatic.maps.demo.db.MapExDBHelper;
import com.google.android.maps.MapActivity;

public class HeatMapActivity extends MapActivity {

	private MapExDBHelper helper;
	private HeatMapOverlay overlay;
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.helper = new MapExDBHelper(this);
		
	}

}

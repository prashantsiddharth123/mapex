package com.androidnatic.maps.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.androidnatic.maps.demo.activities.Selection;

import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Demo extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new SimpleAdapter(this, getItems(), R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
	}
	
	private List<Map<String, Object>> getItems(){
		HashMap<String, Object> selection = new HashMap<String, Object>();
		Intent intent = new Intent (this,Selection.class);
		selection.put("title","Selection demo");
		selection.put("intent", intent);
		List<Map<String,Object>> entries = new ArrayList<Map<String,Object>>();
		entries.add(selection);
		return entries;
	}
	
	 @Override
	    @SuppressWarnings("unchecked")
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	        Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);
	        Intent intent = (Intent) map.get("intent");
	        startActivity(intent);
	    }

}

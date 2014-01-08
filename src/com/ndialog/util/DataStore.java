package com.ndialog.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
 

public class DataStore {
	public static String datastore = "datastore";
	
	public static void store(Activity activity, String proverb) {
		SharedPreferences settings = activity.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("proverb", proverb);
		editor.commit();		
	}
	
	public static String fetch(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		
		String oauth_token = settings.getString("proverb", "");
		return oauth_token;
	}
	
	public static void clear(Activity activity) {
		SharedPreferences settings = activity.getSharedPreferences(datastore, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();  
        editor.commit(); 
	}	
}

package com.ndialog;

import android.content.Context;
import android.widget.Toast;

public class UEUtil {
	public static void qToast(Context context,String text,int duration) {
		Toast.makeText(context, text, duration).show();
	}
}

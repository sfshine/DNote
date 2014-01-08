package com.ndialog;

/* import相关class */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.Time;


/* 实际跳出闹铃Dialog的Activity */
public class AlarmAlert extends Activity {
	private Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String alarm = this.getIntent().getStringExtra("testalarm");
		/* 跳出的闹铃警示 */
		new AlertDialog.Builder(AlarmAlert.this).setIcon(R.drawable.clock)
				.setTitle("注意!有代办事项:").setMessage(alarm)
				.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						/* 关闭Activity */
						vibrator.cancel();
						AlarmAlert.this.finish();
						finish();
					}
				}).show();

		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		long[] pattern = new long[] { 1000, 5000, 2000, 1000, 1000, 500, 800,
				50, 400, 30, 800, 50, 400, 30, 800, 50, 400, 30, 800, 50, 400,
				30, 800, 50, 400, 30, 800, 50, 400, 30, 800, 50, 400, 30, 800,
				50, 400, 30, 800, 50, 400, 30, 800, 50, 400, 30, 800, 50, 400,
				30, 800, 50, 400, 30, 800, 50, 400, 30, 800, 50, 400, 30, 800,
				50, 400, 30, 800, 50, 400, 30, 800, 50, 400, 30, 800, 50, 400,
				30, 800, 50, 400, 30, 800, 50, 400, 30, 800, 50, 400, 30 }; // OFF/ON/OFF/ON...

		vibrator.vibrate(pattern, -1);// -1不重复，非-1为从pattern的指定下标开始重复
	}
}

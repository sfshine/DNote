package com.ndialog;

/* import���class */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.Time;


/* ʵ����������Dialog��Activity */
public class AlarmAlert extends Activity {
	private Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String alarm = this.getIntent().getStringExtra("testalarm");
		/* ���������徯ʾ */
		new AlertDialog.Builder(AlarmAlert.this).setIcon(R.drawable.clock)
				.setTitle("ע��!�д�������:").setMessage(alarm)
				.setPositiveButton("�ر�", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						/* �ر�Activity */
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

		vibrator.vibrate(pattern, -1);// -1���ظ�����-1Ϊ��pattern��ָ���±꿪ʼ�ظ�
	}
}

package com.ndialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ndialog.db.DetailEntityDb;
import com.ndialog.entity.DetailEntity;
import com.ndialog.util.FileUtil;
import com.ndialog.wheather.ChinaWeather;

public class NDialogActivity extends Activity {
	private static final int HANDLER = 556;
	private ListView talkView;
	private ArrayList<DetailEntity> list = null;
	private EditText et1;
	private String weather = "";
	private DetailEntityDb detailEntityDb;
	private DetailAdapter detailAdapter;
	private NDialogActivity context;
	private String time, text;
	private TextView proverb_textView;
	private int dbcounts = 0;
	private static int NOTECOUNTS = 20;
	private int MENU_NOTECOUNTS = 0;
	private ProgressDialog myProgressDialog;
	int i = 0;

	Calendar c = Calendar.getInstance();

	protected void onDestroy() {

		super.onDestroy();
		i = 0;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		NOTECOUNTS = 20;
		getDataFromDb(dbcounts);
	}

	private void init() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ndialong_main);
		context = this;
		detailEntityDb = new DetailEntityDb(NDialogActivity.this);// �������ݿ�����

		et1 = (EditText) findViewById(R.id.chat_text_editor);
		talkView = (ListView) findViewById(R.id.list);
		talkView.setDivider(null);

		Button button = (Button) findViewById(R.id.chat_send_button);
		button.setOnClickListener(send_msg);
		proverb_textView = (TextView) findViewById(R.id.proverb);
		text = DataStore.fetch(this);
		if (text != "") {
			proverb_textView.setText(text);
		}
		proverb_textView.setOnClickListener(proverb_textViewListener);
		dbcounts = (int) detailEntityDb.getCount();
		MENU_NOTECOUNTS = NOTECOUNTS * 2;
		// start = dbcounts - 500;
		// start = start < 0 ? 0 : start;

	}

	// private OnScrollListener onScrollListener = new OnScrollListener() {
	// @Override
	// public void onScrollStateChanged(AbsListView view, int scrollState) {
	//
	// switch (scrollState) {
	//
	// // ��������ʱ
	// case OnScrollListener.SCROLL_STATE_IDLE:
	// // �жϹ������ײ����
	//
	// if (i == 0) {// ���׵�һ����ʾ������ʾ����
	// if (dbcounts == (detailEntityDb.getCount())
	// && view.getLastVisiblePosition() == (view
	// .getCount() - 1)) {// ���dbcountsû�б仯
	//
	// } else if (list.size() == dbcounts) {
	//
	// } else {
	// // Toast.makeText(context, "�ٻ���һ�μ�����һҳ", 10).show();
	// i = 1;
	// }
	//
	// } else {
	// if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
	// // Log.i("���� ����", " ");
	// dbcounts = dbcounts + NOTECOUNTS;
	// getDataFromDb(dbcounts);
	//
	// } else if (view.getFirstVisiblePosition() == 0) {
	//
	// // Log.i("���Ե���", " " + view.getFirstVisiblePosition()
	// // + "   " + view.getCount() + "����������" + dbcounts);
	//
	// dbcounts = dbcounts - NOTECOUNTS;
	// getDataFromDb(dbcounts);
	//
	// }
	// i = 0;
	//
	// }
	// break;
	// }
	// }
	//
	// @Override
	// public void onScroll(AbsListView view, int firstVisibleItem,
	// int visibleItemCount, int totalItemCount) {
	// }
	// };

	private OnClickListener proverb_textViewListener = new OnClickListener() {
		public void onClick(View v) {
			text = DataStore.fetch(context);
			showEditDialog(1);
		}
	};
	private OnItemLongClickListener talkViewLonglistener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			text = list.get(position).getText();
			time = list.get(position).getTime();
			showListDialog();
			return false;
		}
	};

	private void showListDialog() {
		final String[] edit = new String[] { "�༭", "ɾ��", "����", "���", "����" };
		new AlertDialog.Builder(this).setTitle("����:" + text)
				.setItems(edit, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							showEditDialog(0);
							break;

						case 1:
							detailEntityDb.delete(time);
							NOTECOUNTS = 20;
							getDataFromDb(dbcounts);
							UEUtil.qToast(context, text + "�Ѿ��ɹ�ɾ��", 10);
							break;
						case 2:
							ClipboardManager c = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
							c.setText(text);// ����Clipboard ������
							UEUtil.qToast(context, text + "�Ѿ��ɹ����Ƶ����а�", 10);
							break;
						case 3:
							showMarkDialog();
							break;
						case 4:
							showAlarmDialog();
							break;
						default:
							break;
						}
					}
				}).create().show();

	}

	private void showAlarmDialog() {
		final String[] edit = new String[] { "��������", "ȡ������" };
		new AlertDialog.Builder(this).setTitle("���:" + text)
				.setItems(edit, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							showSetDialog();
							break;
						case 1:
							Intent intent = new Intent(context, CallAlarm.class);
							int code = getAlarmCode();
							PendingIntent sender = PendingIntent.getBroadcast(
									context, code, intent, 0);
							/* ��AlarmManager��ɾ�� */
							AlarmManager am;
							am = (AlarmManager) getSystemService(ALARM_SERVICE);
							am.cancel(sender);
							UEUtil.qToast(context, "�����ɹ�", 10);
							break;

						default:
							break;
						}

					}
				}).create().show();

	}

	private void showBackUpDialog() {
		final String[] edit = new String[] { "����", "�ָ�" };
		new AlertDialog.Builder(this).setTitle("ѡ�����")
				.setItems(edit, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							showBackUpDialog(0);
							break;
						case 1:
							showBackUpDialog(1);
							break;

						default:
							break;
						}

					}
				}).create().show();

	}

	private void showBackUpDialog(final int which) {

		AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
		ad1.setTitle("ȷ��?");
		ad1.setIcon(android.R.drawable.ic_dialog_info);
		ad1.setPositiveButton("��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				if (which == 0) {
					FileUtil.copyFile(
							"/data/data/com.ndialog/databases/popnote.db",
							"/sdcard/popnote.db");

				} else if (which == 1) {
					FileUtil.copyFile("/sdcard/popnote.db",
							"/data/data/com.ndialog/databases/popnote.db");

				}

			}
		});
		ad1.setNegativeButton("��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}
		});
		ad1.show();// ��ʾ�Ի���
	}

	private void showSetDialog() {
		/* ȡ�õ����ťʱ��ʱ����ΪTimePickerDialog��Ĭ��ֵ */
		c.setTimeInMillis(System.currentTimeMillis());
		int mHour = c.get(Calendar.HOUR_OF_DAY);
		int mMinute = c.get(Calendar.MINUTE);

		/* ����TimePickerDialog������ʱ�� */
		new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				/* ȡ�����ú��ʱ�䣬���������Ϊ0 */
				c.setTimeInMillis(System.currentTimeMillis());
				c.set(Calendar.HOUR_OF_DAY, hourOfDay);
				c.set(Calendar.MINUTE, minute);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				int day = c.get(Calendar.DAY_OF_YEAR);
				if (c.getTimeInMillis() < System.currentTimeMillis()) {

					c.set(Calendar.DAY_OF_YEAR, day + 1);
				}
				/* ָ����������ʱ�䵽ʱҪ����CallAlarm.class */
				Intent intent = new Intent(context, CallAlarm.class);
				/* ����PendingIntent */
				intent.putExtra("testalarm", text);
				int code = getAlarmCode();

				PendingIntent sender = PendingIntent.getBroadcast(context,
						code, intent, 0);
				AlarmManager am;
				am = (AlarmManager) getSystemService(ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);
				/* ������ʾ����������ʱ�� */
				String tmpS = format(hourOfDay) + "��" + format(minute);
				/* ��Toast��ʾ��������� */
				Toast.makeText(context, "��������ʱ��Ϊ" + tmpS, Toast.LENGTH_SHORT)
						.show();
			}

		}, mHour, mMinute, true).show();

	}

	private void showMarkDialog() {
		final String[] edit = new String[] { "����", "ؽ����", "��Ҫ��", "���" };
		new AlertDialog.Builder(this).setTitle("���:" + text)
				.setItems(edit, new DialogInterface.OnClickListener() {
					DetailEntity detailEntity;

					public void onClick(DialogInterface dialog, int which) {
						detailEntity = new DetailEntity(text, time, which, 0);
						detailEntityDb.updateMark(detailEntity);
						NOTECOUNTS = 20;
						getDataFromDb(dbcounts);
						UEUtil.qToast(context, "�Ѿ��ɹ��޸�", 10);
					}
				}).create().show();

	}

	private void showEditDialog(final int which) {
		final EditText et = new EditText(context);
		AlertDialog.Builder ad1 = new AlertDialog.Builder(context);
		ad1.setTitle("�༭:");
		ad1.setIcon(android.R.drawable.ic_dialog_info);
		ad1.setView(et);
		et.setText(text);
		ad1.setPositiveButton("��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				if (which == 0) {
					DetailEntity detailEntity = new DetailEntity(et.getText()
							.toString(), time, 0, 0);
					detailEntityDb.update(detailEntity);
					NOTECOUNTS = 20;
					getDataFromDb(dbcounts);
					UEUtil.qToast(context, "�Ѿ��ɹ��޸�", 10);
				} else {
					DataStore.store(context, et.getText().toString());
					String proverb = DataStore.fetch(context);
					if (proverb != "") {
						proverb_textView.setText(proverb);
					}
				}

			}
		});
		ad1.setNegativeButton("��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}
		});
		ad1.show();// ��ʾ�Ի���
	}

	public OnClickListener send_msg = new OnClickListener() {
		public void onClick(View v) {
			String text = et1.getText().toString();
			if (text.length() == 0) {
				Toast.makeText(NDialogActivity.this, "���벻��Ϊ�գ�",
						Toast.LENGTH_SHORT).show();

			} else {
				addDate(text, 0, 0);
				if (text.equals("����")) {
					myProgressDialog = ProgressDialog.show(context, "��ʾ",
							"���ڻ�ȡ����...", true);
					myProgressDialog.setCancelable(true);
					GetLocThread getLocThread = new GetLocThread();
					Thread thread = new Thread(getLocThread);
					thread.start();

				}
				et1.setText("");
			}

		}
	};

	private void addDate(String text, int mark, int who) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd  HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String time = formatter.format(curDate);
		String week = getWeekOfDate(curDate);
		time = time + "  " + week;
		talkView = (ListView) findViewById(R.id.list);
		DetailEntity detailEntity = new DetailEntity(text, time, mark, who);
		// list.add(detailEntity);//ע�͵��Ĵ�����ֱ�Ӽ��ص�list�е�����.����һ�ζ�ȡ���ݿ�
		// detailAdapter = new DetailAdapter(context, list);
		// talkView.setAdapter(detailAdapter);
		// dbcounts += 1;
		detailEntityDb.save(detailEntity);
		dbcounts = (int) detailEntityDb.getCount();
		NOTECOUNTS = 20;
		getDataFromDb(dbcounts);
	}

	private static final int TOOLBAR0 = 0;
	private static final int TOOLBAR1 = 1;
	private static final int TOOLBAR2 = 2;
	private static final int TOOLBAR3 = 3;

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, TOOLBAR0, 1, "ȫ����־").setIcon(
				android.R.drawable.ic_menu_compass);
		menu.add(0, TOOLBAR1, 2, "ǰ20��").setIcon(
				android.R.drawable.ic_menu_rotate);
		menu.add(0, TOOLBAR2, 3, "�˳�").setIcon(
				android.R.drawable.ic_lock_power_off);
		menu.add(0, TOOLBAR3, 4, "����/�ָ�").setIcon(
				android.R.drawable.ic_menu_agenda);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			NOTECOUNTS = 20000;
			getDataFromDb(dbcounts);
			// MENU_NOTECOUNTS = MENU_NOTECOUNTS + NOTECOUNTS;
			break;
		case 1:
			// MENU_NOTECOUNTS = MENU_NOTECOUNTS - NOTECOUNTS;
			NOTECOUNTS = 20;
			getDataFromDb(dbcounts);
			break;
		case 2:
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		case 3:
			showBackUpDialog();
			break;
		}

		return super.onOptionsItemSelected(item);

	}

	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == HANDLER) {
				int i = msg.arg1;

				switch (i) {
				case 542:
					myProgressDialog.dismiss();
					addDate(weather, 0, 1);
					break;
				default:
					break;

				}

			}
		}
	};

	class GetLocThread extends Thread {

		int what = 0;
		private boolean runable = true;

		public void run() {
			super.run();
			while (runable) {
				getWheather();

				if (weather != "") {
					int i = 542;
					runable = false;
					Message msg = mhandler.obtainMessage(HANDLER, i, 0);
					mhandler.sendMessage(msg);
				}
			}
		}
	}

	private String getWheather() {
		SLocation location = new SLocation();// ��վλ��

		// GoogleWeather googleWeather = new GoogleWeather();
		ChinaWeather chinaWeather = new ChinaWeather();

		// try {
		// Integer[] loc = location.getLoc(context);
		// weather = location.getLocation(context) + "\n";
		// weather += googleWeather.getWeatherData(loc[0], loc[1]);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		try {
			Integer[] loc = location.getLoc(context);
			weather = location.getLocation(context) + "\n";
			weather += chinaWeather.getWheather("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return weather;
	}

	private int getAlarmCode() {
		String ctime = time.substring(3, time.length() - 6);

		ctime = ctime.replace("-", "");
		ctime = ctime.replace(":", "");
		ctime = ctime.replace("  ", "");
		int code = (int) Long.parseLong(ctime.trim());
		Log.i("ʱ�����", code + "");
		return code;
	}

	/**
	 * �����ݿ��ȡ���ݲ���ʾ��listview
	 */
	private void getDataFromDb(int dbcounts) {

		list = (ArrayList<DetailEntity>) detailEntityDb.getScrollData(dbcounts
				- NOTECOUNTS, dbcounts);
		detailAdapter = new DetailAdapter(context, list);
		// detailAdapter.notifyDataSetChanged();
		if (detailAdapter.getCount() != 0) {
			talkView.setAdapter(detailAdapter);
			talkView.setOnItemLongClickListener(talkViewLonglistener);
			// talkView.setOnScrollListener(onScrollListener);
		}

	}

	private String format(int x) {
		String s = "" + x;
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
}
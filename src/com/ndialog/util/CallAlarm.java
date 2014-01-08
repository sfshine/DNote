package com.ndialog.util;

/* import���class */
import com.ndialog.AlarmAlert;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;

/* ��������Alert��Receiver */
public class CallAlarm extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent)
  {
    /* create Intent������AlarmAlert.class �����趨ʱ�� ����������Ĵ���,�������д */
    String alarm = intent.getStringExtra("testalarm");
    Intent i = new Intent(context, AlarmAlert.class);
    i.putExtra("testalarm", alarm);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);
  }
}

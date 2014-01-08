package com.ndialog;

/* import相关class */
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;

/* 调用闹钟Alert的Receiver */
public class CallAlarm extends BroadcastReceiver
{
  @Override
  public void onReceive(Context context, Intent intent)
  {
    /* create Intent，调用AlarmAlert.class 到了设定时间 将运行这里的代码,可以随便写 */
    String alarm = intent.getStringExtra("testalarm");
    Intent i = new Intent(context, AlarmAlert.class);
    i.putExtra("testalarm", alarm);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);
  }
}

package com.ndialog;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ndialog.db.DetailEntityDb;
import com.ndialog.entity.DetailEntity;

public class DetailAdapter extends BaseAdapter {
	private ArrayList<DetailEntity> coll;
	private Context ctx;
	int i = 0;
	private DetailEntityDb detailEntityDb;

	public DetailAdapter(Context context, ArrayList<DetailEntity> coll) {
		ctx = context;
		this.coll = coll;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		DetailEntity entity = coll.get(position);
		int itemLayout;
		if (entity.getLayoutID() == 1) {// 1是对方说 0是我说
			itemLayout = R.layout.list_say_he_item;
		} else {
			itemLayout = R.layout.list_say_me_item;
		}

		LinearLayout layout = new LinearLayout(ctx);
		LayoutInflater vi = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vi.inflate(itemLayout, layout, true);

		TextView tvTime = (TextView) layout
				.findViewById(R.id.messagedetail_row_time);
		tvTime.setText(entity.getTime());

		TextView tvText = (TextView) layout
				.findViewById(R.id.messagedetail_row_text);
		tvText.setText(entity.getText());
		switch (entity.getMark()) {
		case 0:
			tvText.setTextColor(Color.BLACK);
			break;
		case 1:
			tvText.setTextColor(Color.RED);
			break;
		case 2:
			tvText.setTextColor(Color.BLUE);

			break;
		case 3:
			tvText.setTextColor(Color.GRAY);
			break;
		default:
			break;
		}
		// addListener(tvText, tvTime, tvTime, layout);
		return layout;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return coll.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return coll.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 监听:监听listview里面的空间的各种事件
	 * 
	 * @param convertView
	 */
	public void addListener(final TextView tvName, final TextView tvDate,
			final TextView tvText, LinearLayout layout_bj) {

		layout_bj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		layout_bj.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				tvName.setTextColor(0xffffffff);
				tvDate.setTextColor(0xffffffff);
				tvText.setTextColor(0xffffffff);
				new AlertDialog.Builder(ctx).setTitle("当前是长按操作")
						.setMessage(tvName.getText().toString()).create()
						.show();
				detailEntityDb = new DetailEntityDb(ctx);// 声明数据库连接
				detailEntityDb.delete(tvDate.getText().toString().trim());
				return true;
			}
		});

		layout_bj.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// if (i==0) {
					// tvName.setTextColor(0xff000000);
					// tvDate.setTextColor(0xff000000);
					// tvText.setTextColor(0xff0000ff);
					// i=1;
					// }
					// else {
					// tvName.setTextColor(0x00000000);
					// tvDate.setTextColor(0x00000000);
					// tvText.setTextColor(0x00000000);
					// i=0;
					// }
				case MotionEvent.ACTION_MOVE:
					// tvName.setTextColor(0xffffffff);
					// tvDate.setTextColor(0xffffffff);
					// tvText.setTextColor(0xffffffff);
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

}

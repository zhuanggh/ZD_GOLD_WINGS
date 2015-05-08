package com.eoe.tampletfragment.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eoe.store.ContactsInfo;
import com.eoe.tampletfragment.QueryActivity;
import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.addActivity;

public class ListAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	// 传入的显示的联系人
	private ArrayList<ContactsInfo> list = new ArrayList<ContactsInfo>();
	// 辅助数据，用于辨别每个list对应的是通过搜索联系人的哪个方面得到的
	private ArrayList<String> type = new ArrayList<String>();
	// 辅助数据，搜索词
	private String searchKeys = null;
	private Context context;

	public ListAdapter(Context context) {
		super();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	public void setList(ArrayList<ContactsInfo> list) {
		this.list = list;
	}

	public void setType(ArrayList<String> type) {
		this.type = type;
	}

	public void setSearchKeys(String searchKeys) {
		this.searchKeys = searchKeys;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	static class ViewHolder {
		public TextView cn_word;
		public TextView en_word;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder.cn_word = (TextView) convertView.findViewById(R.id.cn_word);
			holder.en_word = (TextView) convertView.findViewById(R.id.en_word);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (type.isEmpty()) {
			holder.cn_word.setText(list.get(position).getName());
			holder.en_word.setText("");
		} else {
			if (type.get(position) == "name") {
				// 高亮代码
				String title = list.get(position).getName();
				SpannableString wordToSpan = new SpannableString(title);
				int start = title.indexOf(searchKeys);
				int end = start + searchKeys.length();
				wordToSpan.setSpan(new ForegroundColorSpan(Color.BLUE), start,
						end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.cn_word.setText(wordToSpan);
				holder.en_word.setText("");
			} else if (type.get(position) == "address") {
				String title=list.get(position).getAddress();
				SpannableString wordToSpan = new SpannableString(title);  
		        int start = title.indexOf(searchKeys);  
		        int end = start + searchKeys.length();  
		        wordToSpan.setSpan(new ForegroundColorSpan(Color.BLUE),   
		                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText(wordToSpan);
			} else if (type.get(position) == "remarks") {
				String title=list.get(position).getRemark();
				SpannableString wordToSpan = new SpannableString(title);  
		        int start = title.indexOf(searchKeys);  
		        int end = start + searchKeys.length();  
		        wordToSpan.setSpan(new ForegroundColorSpan(Color.BLUE),   
		                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText(wordToSpan);
			} else if (type.get(position) == "pinYin") {
				String title=list.get(position).getPinYin();
				SpannableString wordToSpan = new SpannableString(title);  
		        int start = title.indexOf(searchKeys);  
		        int end = start + searchKeys.length();  
		        wordToSpan.setSpan(new ForegroundColorSpan(Color.BLUE),   
		                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText(wordToSpan);
			} else if (type.get(position) == "FirstpinYin") {
				String title=list.get(position).getFirstPinYin();
				SpannableString wordToSpan = new SpannableString(title);  
		        int start = title.indexOf(searchKeys);  
		        int end = start + searchKeys.length();  
		        wordToSpan.setSpan(new ForegroundColorSpan(Color.BLUE),   
		                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText(wordToSpan);
			} else {
				//电话号码
				String title=type.get(position).substring(8);
				SpannableString wordToSpan = new SpannableString(title);  
		        int start = title.indexOf(searchKeys);  
		        int end = start + searchKeys.length();  
		        wordToSpan.setSpan(new ForegroundColorSpan(Color.BLUE),   
		                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText(wordToSpan);
			}
		}

		// holder.speaker.setOnClickListener(new OnClickListener(){
		//
		// @Override
		// public void onClick(View v) {
		// System.out.println("Click on the speaker image on ListItem ");
		// }
		// });

		return convertView;
	}

}

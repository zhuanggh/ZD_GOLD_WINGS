package com.eoe.tampletfragment.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.view.TitleView;
import com.eoe.tampletfragment.view.TitleView.OnLeftButtonClickListener;

/**
 * @author yangyu
 *	������������ҳfragmentҳ��
 */
public class HomeFragment extends Fragment {

	private View mParent;
	
	private FragmentActivity mActivity;
	
	private TitleView mTitle;
	
	//private TextView mText;
	private Button dial_button;
	private Button num0;
	private Button num1;
	private Button num2;
	private Button num3;
	private Button num4;
	private Button num5;
	private Button num6;
	private Button num7;
	private Button num8;
	private Button num9;
	private Button numx;
	private Button numj;
	private Button delete;
	private Context context;
	private String phone_number = "";
    private int length = 14;
	
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static HomeFragment newInstance(int index) {
		HomeFragment f = new HomeFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();
		mParent = getView();
		
		mTitle = (TitleView) mParent.findViewById(R.id.title);
		mTitle.setTitle(R.string.tab_dial);
		mTitle.setLeftButton(R.string.exit, new OnLeftButtonClickListener(){
			@Override
			public void onClick(View button) {
				mActivity.finish();
			}
			
		});
	    num0 = (Button)mParent.findViewById(R.id.dialNum0);
	    num0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(phone_number.length() < length){	
				phone_number += "0"; 
				dial_button.setText(phone_number);}}	
			
		});
	    num1 = (Button)mParent.findViewById(R.id.dialNum1);
	    num1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "1";dial_button.setText(phone_number); }}			
		});
	    num2 = (Button)mParent.findViewById(R.id.dialNum2);
	    num2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "2";dial_button.setText(phone_number);} }			
		});
	    num3 = (Button)mParent.findViewById(R.id.dialNum3);
	    num3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "3";dial_button.setText(phone_number); }}			
		});
	    num4 = (Button)mParent.findViewById(R.id.dialNum4);
	    num4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "4"; dial_button.setText(phone_number);}}			
		});
	    num5 = (Button)mParent.findViewById(R.id.dialNum5);
	    num5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "5"; dial_button.setText(phone_number);}}			
		});
	    num6 = (Button)mParent.findViewById(R.id.dialNum6);
	    num6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "6"; dial_button.setText(phone_number);}}			
		});
	    num7 = (Button)mParent.findViewById(R.id.dialNum7);
	    num7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "7"; dial_button.setText(phone_number);}}			
		});
	    num8 = (Button)mParent.findViewById(R.id.dialNum8);
	    num8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "8";dial_button.setText(phone_number);} }			
		});
	    num9 = (Button)mParent.findViewById(R.id.dialNum9);
	    num9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "9"; dial_button.setText(phone_number);}}			
		});
	    numx = (Button)mParent.findViewById(R.id.dialx);
	    numx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "*"; dial_button.setText(phone_number);}}			
		});
	    numj = (Button)mParent.findViewById(R.id.dialj);
	    numj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(phone_number.length() < length){
				phone_number += "#"; dial_button.setText(phone_number);}}			
		});
	    delete = (Button)mParent.findViewById(R.id.delete);
	    delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (phone_number.length() > 0) {  
                	phone_number = phone_number.substring(0, phone_number.length() - 1); 
                	dial_button.setText(phone_number);
                }  }			
		});
	    
		dial_button = (Button) mParent.findViewById(R.id.phone_view);
		//dial_button.setText("10086");
		dial_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dial_button.getText().toString().length() != 0) {
				
					Uri uri = Uri.parse("tel:"+dial_button.getText().toString());
					Intent it = new Intent(Intent.ACTION_CALL, uri);
					startActivity(it);
			}
			}
		});
//		mTitle.setRightButton(R.string.help, new OnRightButtonClickListener() {
//
//			@Override
//			public void onClick(View button) {
//				goHelpActivity();
//			}
//		});
//		
//		mText = (TextView) mParent.findViewById(R.id.fragment_home_text);

	}
	
//	private void goHelpActivity() {
//		Intent intent = new Intent(mActivity, addActivity.class);
//		startActivity(intent);
//	}
	

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}

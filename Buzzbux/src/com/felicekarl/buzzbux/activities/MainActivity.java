package com.felicekarl.buzzbux.activities;

import com.felicekarl.buzzbux.R;
import com.felicekarl.buzzbux.models.MainModel;
import com.felicekarl.buzzbux.presenters.MainPresenter;
import com.felicekarl.buzzbux.views.MainView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
	public static final boolean D = true;
	@SuppressWarnings("unused")
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private MainPresenter presenter;
	private MainView view;
	private MainModel model;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view = new MainView(this);
		model = new MainModel(this);
		presenter = new MainPresenter(this, view, model);
		
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		openQuitDialog();
	}

	private void openQuitDialog(){
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
		quitDialog.setTitle("Confirm to Quit Buzzbux?");
		quitDialog.setPositiveButton("Quit", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}});
		quitDialog.setNegativeButton("Cancel", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}});
		quitDialog.show();
	}
}

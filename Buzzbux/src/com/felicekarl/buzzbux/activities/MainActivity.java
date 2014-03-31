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

/**
 * MainActivity to hold MVP model.
 * @author Karl
 *
 */
public class MainActivity extends FragmentActivity {
	/**
	 * Presenter.
	 */
    @SuppressWarnings("unused")
    private MainPresenter presenter;
    /**
     * View.
     */
    private MainView view;
    /**
     * Model.
     */
    private MainModel model;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = new MainView(this);
        model = new MainModel(this);
        presenter = new MainPresenter(this, view, model);
    }
	
    /**
     * Return View of MVP model.
     * @return view of MVP model
     */
    public MainView getView() {
        return view;
    }
	
    /**
     * Return the Model of MVP model.
     * @return model of MVP model
     */
    public MainModel getModel() {
        return model;
    }
	
    @Override
    protected void onResume() {
        super.onResume();
    }
	
    @Override
    public void onBackPressed() {
        openQuitDialog();
    }
    
    /**
     * Open Dialog box for confirming exiting application.
     */
    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setTitle("Confirm to Quit Buzzbux?");
        quitDialog.setPositiveButton("Quit", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        quitDialog.setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	
            } 
        });
	quitDialog.show();
    }
}

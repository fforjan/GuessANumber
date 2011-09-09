package com.geovah.guessnumber.view;

import com.geovah.guessnumber.R;
import com.geovah.guessnumber.viewmodel.WelcomeViewModel;

import gueei.binding.Binder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class WelcomeView extends Activity {
	
	WelcomeViewModel vm;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new WelcomeViewModel(this);
        Binder.setAndBindContentView(this, R.layout.welcome,vm);
       
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	vm.updateStatistics();
    }
    
   
}
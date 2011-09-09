package com.geovah.guessnumber.view;

import com.geovah.guessnumber.R;
import com.geovah.guessnumber.viewmodel.WelcomeViewModel;

import gueei.binding.Binder;
import android.app.Activity;
import android.os.Bundle;


public class WelcomeView extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.setAndBindContentView(this, R.layout.welcome,new WelcomeViewModel(this));
       
    }
    
}
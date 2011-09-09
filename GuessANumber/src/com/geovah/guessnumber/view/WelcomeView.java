package com.geovah.guessnumber.view;

import gueei.binding.Binder;
import android.os.Bundle;

import com.geovah.guessnumber.BindingActivity;
import com.geovah.guessnumber.R;
import com.geovah.guessnumber.viewmodel.WelcomeViewModel;


public class WelcomeView extends BindingActivity {
	
	WelcomeViewModel vm;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new WelcomeViewModel(this);
        Binder.setAndBindContentView(this, R.layout.welcome,vm);
        bindActivityResult(vm);
    }
    
   
}
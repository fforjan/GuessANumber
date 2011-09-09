package com.geovah.guessanumber.test;

import gueei.binding.Binder;
import android.app.Application;

public class GuessANumberTestApplication extends Application {

	/**
	 * this method register the binding implementation
	 * @see android.app.Application#onCreate()
	 */
	@Override
    public void onCreate() {
            super.onCreate();
            Binder.init(this);
    }
}

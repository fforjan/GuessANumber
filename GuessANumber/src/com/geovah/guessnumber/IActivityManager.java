package com.geovah.guessnumber;

import android.content.Context;
import android.content.Intent;


public interface IActivityManager {

	void startActivityForResult(Intent intent,int resultCode);
	Context getContext();
	
	void finish();
	void setResult(int resultCode);
}

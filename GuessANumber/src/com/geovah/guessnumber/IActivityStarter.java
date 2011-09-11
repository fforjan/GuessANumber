package com.geovah.guessnumber;

import android.content.Context;
import android.content.Intent;


public interface IActivityStarter {

	void startActivityForResult(Intent intent,int resultCode);
	Context getContext();
}

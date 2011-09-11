package com.geovah.guessnumber;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

public class ActivityManagerHelper implements IActivityManager {

	private Context mContext;
	public ActivityManagerHelper(Context context)
	{
		mContext = context;
	    isFinished = false;
	    resultCode = Integer.MIN_VALUE;
	}
	@Override
	public void startActivityForResult(Intent intent, int resultCode) {
			startedActivity.add(new Pair<Intent,Integer>(intent,resultCode));
	}
	
	public List<Pair<Intent,Integer>> startedActivity = new ArrayList<Pair<Intent,Integer>>();

	@Override
	public Context getContext() {
		return mContext;
	}
	
	public Boolean isFinished;
	@Override
	public void finish() {
		isFinished = true;
	}
	
	public int resultCode;
	@Override
	public void setResult(int resultCode)
	{
		this.resultCode = resultCode;
	}

}

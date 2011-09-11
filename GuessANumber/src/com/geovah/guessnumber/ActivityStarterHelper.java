package com.geovah.guessnumber;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

public class ActivityStarterHelper implements IActivityStarter {

	private Context mContext;
	public ActivityStarterHelper(Context context)
	{
		mContext = context;
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

}

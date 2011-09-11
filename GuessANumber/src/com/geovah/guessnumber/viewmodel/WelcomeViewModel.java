package com.geovah.guessnumber.viewmodel;

import gueei.binding.Command;
import gueei.binding.observables.DoubleObservable;
import gueei.binding.observables.IntegerObservable;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.geovah.guessnumber.ActivityList;
import com.geovah.guessnumber.ActivityResultBinding;
import com.geovah.guessnumber.ArgumentNullException;
import com.geovah.guessnumber.IActivityStarter;
import com.geovah.guessnumber.model.GuessNumberModel;
import com.geovah.guessnumber.view.PlayingView;

public class WelcomeViewModel {

	
	IActivityStarter _activityStarter;
	
	public IntegerObservable MinGuesses;
	public IntegerObservable MaxGuesses;
	public DoubleObservable AverageGuesses;
	
	public Command StartPlaying = new Command(){
		public void Invoke(View view, Object... args) {
			Intent i = new Intent(_activityStarter.getContext(), PlayingView.class);
			_activityStarter.startActivityForResult(i, ActivityList.Playing);
		}    
	};
	
    private GuessNumberModel _db;
    
    
    public WelcomeViewModel(IActivityStarter activityStarter)
    {
    	if(activityStarter ==null) { throw new ArgumentNullException("activityStarter");}
    	
    	_activityStarter = activityStarter;
    	_db = new GuessNumberModel(_activityStarter.getContext()).open();
    	
    	MinGuesses = new IntegerObservable();
    	MaxGuesses = new IntegerObservable();
    	AverageGuesses = new DoubleObservable();
    	
    	updateStatistics();
    }
    
    public void updateStatistics()
    {
    	GuessNumberModel.Result result = _db.calculateResult();
    	
    	MinGuesses.set(result.min);
    	MaxGuesses.set(result.max);
    	
    	AverageGuesses.set(((int)(result.average * 100))/100.0);
    
    }
    
    @ActivityResultBinding(ActivityId = ActivityList.Playing)
    public void onFinishPlaying(int resultCode,Intent data)
    {
    	updateStatistics();
    }
   
}

package com.geovah.guessnumber.viewmodel;

import gueei.binding.Command;
import gueei.binding.observables.DoubleObservable;
import gueei.binding.observables.IntegerObservable;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.geovah.guessnumber.ArgumentNullException;
import com.geovah.guessnumber.model.GuessNumberModel;
import com.geovah.guessnumber.view.PlayingView;

public class WelcomeViewModel {

	
	Activity _activity;
	
	public IntegerObservable MinGuesses;
	public IntegerObservable MaxGuesses;
	public DoubleObservable AverageGuesses;
	
	public Command StartPlaying = new Command(){
		public void Invoke(View view, Object... args) {
			Intent i = new Intent(_activity, PlayingView.class);
			_activity.startActivity(i);
		}    
	};
	
    private GuessNumberModel _db;
    
    
    public WelcomeViewModel(Activity activity)
    {
    	if(activity ==null) { throw new ArgumentNullException("activity");}
    	
    	_activity = activity;
    	_db = new GuessNumberModel(_activity).open();
    	
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
   
}

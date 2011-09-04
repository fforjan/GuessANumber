package com.geovag.guessnumber;

import gueei.binding.Command;
import gueei.binding.observables.DoubleObservable;
import gueei.binding.observables.IntegerObservable;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class WelcomeViewModel {

	
	Activity _activity;
	
	public IntegerObservable MinGuesses;
	public IntegerObservable MaxGuesses;
	public DoubleObservable AverageGuesses;
	
	public Command StartPlaying = new Command(){
		public void Invoke(View view, Object... args) {
			Intent i = new Intent(_activity, PlayingGuessNumber.class);
			_activity.startActivity(i);
		}    
	};
	
    private GuessNumberDbAdapter _db;
    
    
    public WelcomeViewModel(Activity activity)
    {
    	if(activity ==null) { throw new NullPointerException("activity");}
    	
    	_activity = activity;
    	_db = new GuessNumberDbAdapter(_activity).open();
    	
    	MinGuesses = new IntegerObservable();
    	MaxGuesses = new IntegerObservable();
    	AverageGuesses = new DoubleObservable();
    	
    	updateStatistics();
    }
    
    protected void updateStatistics()
    {
    	GuessNumberDbAdapter.Result result = _db.calculateResult();
    	
    	MinGuesses.set(result.min);
    	MaxGuesses.set(result.max);
    	AverageGuesses.set(result.average);
    
    }
   
}

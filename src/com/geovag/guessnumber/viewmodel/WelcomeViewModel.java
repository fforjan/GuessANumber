package com.geovag.guessnumber.viewmodel;

import com.geovag.guessnumber.PlayingGuessNumber;
import com.geovag.guessnumber.model.GuessNumberModel;

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
	
    private GuessNumberModel _db;
    
    
    public WelcomeViewModel(Activity activity)
    {
    	if(activity ==null) { throw new NullPointerException("activity");}
    	
    	_activity = activity;
    	_db = new GuessNumberModel(_activity).open();
    	
    	MinGuesses = new IntegerObservable();
    	MaxGuesses = new IntegerObservable();
    	AverageGuesses = new DoubleObservable();
    	
    	updateStatistics();
    }
    
    protected void updateStatistics()
    {
    	GuessNumberModel.Result result = _db.calculateResult();
    	
    	MinGuesses.set(result.min);
    	MaxGuesses.set(result.max);
    	AverageGuesses.set(result.average);
    
    }
   
}

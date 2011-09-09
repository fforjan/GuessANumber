package com.geovah.guessnumber.viewmodel;


import gueei.binding.Command;
import gueei.binding.collections.ArrayListObservable;
import gueei.binding.observables.StringObservable;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.geovah.guessnumber.ArgumentNullException;
import com.geovah.guessnumber.R;
import com.geovah.guessnumber.model.GuessNumberModel;
import com.geovah.guessnumber.view.PlayingView.SerializationField;

public class PlayingViewModel {
	
	

	private static Random randomizer = new Random();
	
	
	private GuessNumberModel _db;
	private Activity _activity;
	private int _proposalCount;
   
    
    public PlayingViewModel(Activity activity)
    {
    	if(activity ==null) { throw new ArgumentNullException("activity");}
    	
    	_activity = activity;
    	_db = new GuessNumberModel(_activity).open();
    	
    }
    
    public Command NewProposal = new Command(){
		public void Invoke(View view, Object... args) {
			if(Proposal.get() !=null && Proposal.get().length() > 0)
			{
				newProposal();
			}
		}    
	};
	
    public StringObservable Proposal;
	private int _guessedNumber;
	public ArrayListObservable<String> Results;
	
	public void onCreate(Bundle savedInstanceState) {
	
		
		_guessedNumber = (savedInstanceState == null) ? randomizer.nextInt(1001) :
	            (Integer) savedInstanceState.getSerializable(SerializationField.GuessedNumber);
		

		_proposalCount = (savedInstanceState == null) ?0 :
			(Integer) savedInstanceState.getSerializable(SerializationField.ProposalCount);
		
		String[] history = (savedInstanceState == null) ? new String[0]  :
			(String[]) savedInstanceState.getSerializable(SerializationField.History);
		
		Results=  new ArrayListObservable<String>(String.class);
		
		Results.setArray(history);
		
		Proposal = new StringObservable();
	}
    
    
    private void newProposal()
	{
		_proposalCount ++;
		int proposal = Integer.parseInt(Proposal.get());

		if(proposal == _guessedNumber)
		{
			winDialog(_proposalCount);
			
		}
		else if (proposal < _guessedNumber)
		{
			Results.add(_activity.getString(R.string.GuessedNumberGreather,proposal));
		}
		else
		{
			Results.add(_activity.getString(R.string.GuessedNumberLesser,proposal));
		}
		
		
		Proposal.set("");
	}
	
	private void winDialog(int guessCount)
	{
		
		_db.storeResult(0, guessCount);
		AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
		builder.setMessage(_activity.getString(R.string.Congrats,guessCount))
		       .setCancelable(false)
		       .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   _activity.setResult(Activity.RESULT_OK);
		        	   _activity.finish();
		           }
		       })
		       .setIcon(R.drawable.icon);
		builder.create().show();
	}
	
	public void onSaveInstanceState(Bundle outState) {
		
		
		String[] history = (String[]) Results.toArray();
		outState.putSerializable(SerializationField.GuessedNumber, _guessedNumber);
		outState.putSerializable(SerializationField.History, history);
		outState.putSerializable(SerializationField.ProposalCount, _proposalCount);
		
	}
}

package com.geovah.guessnumber.view;

import java.util.Random;

import com.geovah.guessnumber.R;
import com.geovah.guessnumber.model.GuessNumberModel;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class PlayingView extends ListActivity {
	
	public final class MenuId
	{
		public static final int Quit = Menu.FIRST;
	
	}
	
	public final class SerializationField
	{
		
		public static final String GuessedNumber= "GuessedNumber";
		public static final String ProposalCount= "ProposalCount";
		public static final String History = "History";
	}
	
	private static Random randomizer = new Random();
	
	public int guessedNumber;
	
	private EditText proposedNumber;
	private int proposalCount;
	private Button proposeButton;
	
	private ArrayAdapter<String> results;
	
	private GuessNumberModel _db;
    /**
     * Gets the database connection
     * if the database is not opened, open it
     * @return
     */
    public GuessNumberModel GetDB()
    {
    	if(_db == null)
    	{
    		_db = new GuessNumberModel(this).open();
    	}
    	return _db;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playing);
		
		
		guessedNumber = (savedInstanceState == null) ? randomizer.nextInt(1001) :
	            (Integer) savedInstanceState.getSerializable(SerializationField.GuessedNumber);
		

		proposalCount = (savedInstanceState == null) ?0 :
			(Integer) savedInstanceState.getSerializable(SerializationField.ProposalCount);
		
		
	
		proposedNumber = (EditText) findViewById(R.id.proposedNumber);
		
		
		results = (savedInstanceState == null) ? new ArrayAdapter<String>(this,R.layout.guessed_row ):
			new ArrayAdapter<String>(this,R.layout.guessed_row, (String[]) savedInstanceState.getSerializable(SerializationField.ProposalCount));
		
		setListAdapter(results);
		
		proposeButton = (Button) findViewById(R.id.proposeButton);
		proposeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(proposedNumber.getText().length() > 0)
				newProposal();
			}
		});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		String[] history = new String[results.getCount() ];
		for(int i=0;i< history.length;i++)
		{
			history[i] = results.getItem(i);
		}
		
		outState.putSerializable(SerializationField.GuessedNumber, guessedNumber);
		outState.putSerializable(SerializationField.History, history);
		outState.putSerializable(SerializationField.ProposalCount, proposalCount);
		
	}
	
	
	private void newProposal()
	{
		proposalCount ++;
		int proposal = Integer.parseInt(proposedNumber.getText().toString());
		if(proposal == guessedNumber)
		{
			winDialog(proposalCount);
			
		}
		else if (proposal < guessedNumber)
		{
			results.insert(String.format(getText(R.string.GuessedNumberGreather).toString(),proposal),0);
		}
		else
		{
			results.insert(String.format(getText(R.string.GuessedNumberLesser).toString(),proposal),0);
		}
		
		proposedNumber.setText("");
	}
	
	private void winDialog(int guessCount)
	{
		
		GetDB().storeResult(0, guessCount);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(String.format(getText(R.string.Congrats).toString(),guessCount))
		       .setCancelable(false)
		       .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   PlayingView.this.setResult(RESULT_OK);
		        	   PlayingView.this.finish();
		           }
		       })
		       .setIcon(R.drawable.icon);
		builder.create().show();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		boolean result =  super.onCreateOptionsMenu(menu);
		menu.add(0, MenuId.Quit, 0, R.string.QuitMenu);
		return result;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	
		switch(item.getItemId())
		{
		case MenuId.Quit: setResult(RESULT_CANCELED); finish();
		}
		return super.onMenuItemSelected(featureId, item);
	}
}

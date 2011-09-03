package com.geovag.guessnumber;

import java.util.Random;

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

public class PlayingGuessNumber extends ListActivity {
	
	public final class MenuId
	{
		public static final int Quit = Menu.FIRST;
	
	}
	
	private static Random randomizer = new Random();
	
	public int guessedNumber;
	
	private EditText proposedNumber;
	private int proposalCount;
	private Button proposeButton;
	
	private ArrayAdapter<String> results;
	
	private GuessNumberDbAdapter _db;
    /**
     * Gets the database connection
     * if the database is not opened, open it
     * @return
     */
    public GuessNumberDbAdapter GetDB()
    {
    	if(_db == null)
    	{
    		_db = new GuessNumberDbAdapter(this).open();
    	}
    	return _db;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playing);
		
		//TODO : handle to saved instance state..
		guessedNumber = randomizer.nextInt(1001);
		proposalCount = 0;
	
		proposedNumber = (EditText) findViewById(R.id.proposedNumber);
		results = new ArrayAdapter<String>(this,R.layout.guessed_row );
		setListAdapter(results);
		
		proposeButton = (Button) findViewById(R.id.proposeButton);
		proposeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				newProposal();
			}
		});
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
		        	   PlayingGuessNumber.this.setResult(RESULT_OK);
		        	   PlayingGuessNumber.this.finish();
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

package com.geovag.guessnumber;

import java.util.Random;

import android.app.ListActivity;
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
	private Button proposeButton;
	
	private ArrayAdapter<String> results;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playing);
		
		//TODO : handle to saved instance state..
		guessedNumber = randomizer.nextInt(1001);
	
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
		int proposal = Integer.parseInt(proposedNumber.getText().toString());
		if(proposal == guessedNumber)
		{
			results.insert("Win!", 0);
			proposedNumber.setEnabled(false);
			proposeButton.setEnabled(false);
			
		}
		else if (proposal < guessedNumber)
		{
			results.insert(String.format("Guessed number is greather than %1$d",proposal),0);
		}
		else
		{
			results.insert(String.format("Guessed number is lesser than %1$d",proposal),0);
		}
		
		proposedNumber.setText("");
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

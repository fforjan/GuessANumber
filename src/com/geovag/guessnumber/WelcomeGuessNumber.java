package com.geovag.guessnumber;

import junit.framework.Assert;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeGuessNumber extends Activity {
	
	private TextView _minTextView;
	private TextView _maxTextView;
	private TextView _avgTextView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
        _minTextView = (TextView) findViewById(R.id.minimumGuess);
        Assert.assertNotNull(_minTextView);
        _maxTextView = (TextView) findViewById(R.id.maximumGuess);
        Assert.assertNotNull(_maxTextView);
        _avgTextView = (TextView) findViewById(R.id.averageGuess);
        Assert.assertNotNull(_avgTextView);
        
        updateStatistics();
        
        Button startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	startPlaying();
            }

        });
    }
    
    private void startPlaying()
    {    	
       	Intent i = new Intent(this, PlayingGuessNumber.class);
    	startActivityForResult(i, ActivityList.Playing );
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		updateStatistics();
	}
    
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
    
    protected void updateStatistics()
    {
    	GuessNumberDbAdapter.Result result = GetDB().calculateResult();
    	_minTextView.setText(Integer.toString(result.min));
    	_maxTextView.setText(Integer.toString(result.max));
    	_avgTextView.setText( Double.toString(result.average));
    
    }
}
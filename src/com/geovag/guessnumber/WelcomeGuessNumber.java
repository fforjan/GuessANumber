package com.geovag.guessnumber;

import junit.framework.Assert;
import android.app.Activity;
import android.os.Bundle;
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
package com.geovag.guessnumber;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PlayingGuessNumber extends ListActivity {
	
	public final class MenuId
	{
		public static final int Quit = Menu.FIRST;
	
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playing);
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

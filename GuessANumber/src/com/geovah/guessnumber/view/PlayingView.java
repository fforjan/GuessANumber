package com.geovah.guessnumber.view;

import gueei.binding.Binder;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.geovah.guessnumber.R;
import com.geovah.guessnumber.viewmodel.PlayingViewModel;

public class PlayingView extends Activity {
	
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
	
	class Dummy {}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PlayingViewModel vm = new PlayingViewModel(this);
		vm.onCreate(savedInstanceState);
		Binder.setAndBindContentView(this, R.layout.playing,vm);
		
	
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
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

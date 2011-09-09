package com.geovah.guessanumber.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.geovah.guessnumber.ArgumentNullException;
import com.geovah.guessnumber.view.PlayingView;
import com.geovah.guessnumber.viewmodel.PlayingViewModel;

public class PlayingViewModelTest  extends ActivityInstrumentationTestCase2<PlayingView> {

	
	

	public PlayingViewModelTest()
	{
		super("com.geovah.guessnumber",PlayingView.class);
	}
	public void NewProposal()
	{
		
	
	}
	
	
	/**
	 * Test method for {@link com.geovah.guessnumber.viewmodel.PlayingViewModel#PlayingViewModel(android.app.Activity)}.
	 */
	public void testPlayingView() {
		
		try
		{
			new PlayingViewModel(null);
		}
		catch(ArgumentNullException ex)
		{
			Assert.assertEquals("activity",ex.getParameterName());
		}
		catch(Exception e)
		{
			Assert.fail("Wrong exception catch !");
		}
			     
		
		PlayingViewModel model = new PlayingViewModel(getActivity());
		model.onCreate(null);
		
		Assert.assertNotNull(model.NewProposal);
		Assert.assertNotNull(model.Results);
		Assert.assertNotNull(model.Proposal);
		
		
	}
}

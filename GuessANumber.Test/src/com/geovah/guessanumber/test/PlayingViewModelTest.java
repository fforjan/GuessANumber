package com.geovah.guessanumber.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.geovah.guessnumber.ActivityManagerHelper;
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
		
		ActivityManagerHelper helper = new ActivityManagerHelper(getActivity());
		
		PlayingViewModel model = new PlayingViewModel(helper);
	
		Assert.assertEquals(0, model.Results.size());
		model.Proposal.set(""); //should not be accepted
		model.NewProposal.Invoke(null, new Object[0]);
		Assert.assertEquals(0, model.Results.size());
		Assert.assertEquals("",model.Proposal.get());
		model.Proposal.set("9999");
		model.NewProposal.Invoke(null, new Object[0]);
		Assert.assertEquals(1, model.Results.size());
		Assert.assertEquals("",model.Proposal.get());
		
	}
	
	
	/**
	 * Test method for {@link com.geovah.guessnumber.viewmodel.PlayingViewModel#PlayingViewModel(android.app.Activity)}.
	 */
	public void testPlayingView() {
		
		try
		{
			new PlayingViewModel(null);
			Assert.fail("wrong path");
		}
		catch(ArgumentNullException ex)
		{
			Assert.assertEquals("activityManager",ex.getParameterName());
		}
		catch(Exception e)
		{
			Assert.fail("Wrong exception catch !");
		}
			     
		ActivityManagerHelper helper = new ActivityManagerHelper(getActivity());
		
		PlayingViewModel model = new PlayingViewModel(helper);
		model.onCreate(null);
		
		Assert.assertNotNull(model.NewProposal);
		Assert.assertNotNull(model.Results);
		Assert.assertNotNull(model.Proposal);
		
		
	}
}

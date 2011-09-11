/**
 * 
 */
package com.geovah.guessanumber.test;

import gueei.binding.IObservable;
import gueei.binding.Observer;

import java.util.Collection;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

import com.geovah.guessnumber.ActivityStarterHelper;
import com.geovah.guessnumber.ArgumentNullException;
import com.geovah.guessnumber.view.WelcomeView;
import com.geovah.guessnumber.viewmodel.WelcomeViewModel;

/**
 * @author GeoVah
 *
 */
public class WelcomeViewModelTest extends ActivityInstrumentationTestCase2<WelcomeView> {
	
	public WelcomeViewModelTest()
	{
	        super("com.geovah.guessnumber",WelcomeView.class);
	}

	
	/**
	 * Test method for {@link com.geovah.guessnumber.viewmodel.WelcomeViewModel#WelcomeViewModel(android.app.Activity)}.
	 */
	public void testWelcomeViewModel() {
		
		
		try
		{
			new WelcomeViewModel(null);
			Assert.fail("Exception not thrown !");
		}
		catch(ArgumentNullException ex)
		{
			Assert.assertEquals("activityStarter",ex.getParameterName());
		}
		catch(Exception e)
		{
			Assert.fail("Wrong exception catch !");
		}
			     
		ActivityStarterHelper helper = new ActivityStarterHelper(getActivity());
		
		WelcomeViewModel model = new WelcomeViewModel(helper);
		
		Assert.assertNotNull(model.AverageGuesses);
		Assert.assertNotNull(model.MinGuesses);
		Assert.assertNotNull(model.MaxGuesses);
		
		
	}

	
	private boolean maxGuessUpdated;
	private boolean minGuessUpdated;
	private boolean avgGuessUpdated;
	/**
	 * Test method for {@link com.geovah.guessnumber.viewmodel.WelcomeViewModel#updateStatistics()}.
	 */
	public void testUpdateStatistics() {
		
		ActivityStarterHelper helper = new ActivityStarterHelper(getActivity());
		
		WelcomeViewModel model = new WelcomeViewModel(helper);
		
		maxGuessUpdated = false;
		model.MaxGuesses.subscribe(new Observer() {

			@Override
			public void onPropertyChanged(IObservable<?> arg0,
					Collection<Object> arg1) {
				maxGuessUpdated = true;
				
			}} );
		
		minGuessUpdated = false;
		model.MinGuesses.subscribe(new Observer() {

			@Override
			public void onPropertyChanged(IObservable<?> arg0,
					Collection<Object> arg1) {
				minGuessUpdated = true;
				
			}} );
		
		
		avgGuessUpdated = false;
		model.AverageGuesses.subscribe(new Observer() {

			@Override
			public void onPropertyChanged(IObservable<?> arg0,
					Collection<Object> arg1) {
				avgGuessUpdated = true;
				
			}} );
		
		Assert.assertFalse(maxGuessUpdated);
		Assert.assertFalse(minGuessUpdated);
		Assert.assertFalse(avgGuessUpdated);
		
		model.updateStatistics();
		
		Assert.assertTrue(maxGuessUpdated);
		Assert.assertTrue(minGuessUpdated);
		Assert.assertTrue(avgGuessUpdated);
		
		
	}
	
	/**
	 * 
	 */
	public void testStartPlaying()
	{

		ActivityStarterHelper helper = new ActivityStarterHelper(getActivity());
		
		WelcomeViewModel model = new WelcomeViewModel(helper);
		
		Assert.assertEquals(0, helper.startedActivity.size());
		model.StartPlaying.Invoke(null, new Object[0]);
		Assert.assertEquals(1, helper.startedActivity.size());
		
		
	}
	
	

}

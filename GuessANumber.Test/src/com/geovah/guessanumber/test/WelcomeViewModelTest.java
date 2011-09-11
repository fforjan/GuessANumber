/**
 * 
 */
package com.geovah.guessanumber.test;

import gueei.binding.IObservable;
import gueei.binding.Observer;

import java.util.Collection;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

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
		}
		catch(ArgumentNullException ex)
		{
			Assert.assertEquals("activity",ex.getParameterName());
		}
		catch(Exception e)
		{
			Assert.fail("Wrong exception catch !");
		}
			     
		
		WelcomeViewModel model = new WelcomeViewModel(getActivity());
		
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
		
		WelcomeViewModel model = new WelcomeViewModel(getActivity());
		
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
	private void testStartPlaying()
	{
		WelcomeViewModel model = new WelcomeViewModel(getActivity());
		
		//model.StartPlaying.Invoke(null, new Object[0]);
		
	}
	
	

}

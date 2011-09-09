package com.geovah.guessanumber.test;

import junit.framework.Assert;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.geovah.guessnumber.ActivityResultBinding;
import com.geovah.guessnumber.ArgumentNullException;
import com.geovah.guessnumber.BindingActivity;

public class BindingActivityTest extends ActivityInstrumentationTestCase2<BindingActivity> {

	abstract class Action
	{
		 abstract void Do();
	}
	
	public <T extends Throwable> T AssertException(Action action, Class<T> genericInstance)
	{
		
		try
		{
			action.Do();
			Assert.fail(String.format("Exception %1$s was not throw !",genericInstance.getCanonicalName()));
		}
		catch(junit.framework.AssertionFailedError e)
		{
			throw e;
		}
		catch(Throwable e)
		{
			if(genericInstance.isInstance(e))
			{
				return genericInstance.cast(e);
			}
			Assert.fail(String.format("Wrong Exception,expecting %1$s received :%2$s",genericInstance.getCanonicalName(), e.toString()));
		}
		return null;
	}

	public BindingActivityTest()
	{
		super("com.geovah.guessnumber",BindingActivity.class);
	}
	
	public class WrongSignature
	{
		@ActivityResultBinding(ActivityId = 0)
		public void Wrong()
		{
		}
	}
	
	class WrongSignature1
	{
		@ActivityResultBinding(ActivityId = 0)
		public void Wrong(int i)
		{
		}
	}
	
	class RighSignature
	{
		@ActivityResultBinding(ActivityId = 0)
		public void Wrong(int resultCode,Intent data)
		{
			ResultCode = resultCode;
			Data = data;
			Invoke = true;
		}
		
		public int ResultCode = Integer.MIN_VALUE;
		public Intent Data;
		
		public boolean Invoke = false; 
	}
	
	public void testBindActivityResultArgumentNull()
	{
		final BindingActivity activity = getActivity();
		
		Assert.assertEquals(
				"contentViewModel",
				AssertException(new Action() {
					
					@Override
					void Do() {
						activity.bindActivityResult((Object[])null);
					}
				},ArgumentNullException.class).getParameterName());
	}
	public void testBindActivityResultWrongArgument()
	{
		final BindingActivity activity = getActivity();
		
		Assert.assertTrue(
				AssertException(new Action() {
					
					@Override
					void Do() {
						activity.bindActivityResult(new WrongSignature());
					}
				},IllegalArgumentException.class).getMessage().startsWith("com.geovah.guessanumber.test.BindingActivityTest$WrongSignature.Wrong"));
	}
	
	public void testBindActivityResultRightWrongArgument()
	{
		final BindingActivity activity = getActivity();
		IllegalArgumentException ex = AssertException(new Action() {
			
			@Override
			void Do() {
				activity.bindActivityResult(new RighSignature(), new WrongSignature());
			}
		},IllegalArgumentException.class);
		Assert.assertTrue(
				ex.getMessage(),
				ex.getMessage().startsWith("com.geovah.guessanumber.test.BindingActivityTest$WrongSignature.Wrong"));
	}
	
	public void testBindActivityResultWrong1Argument()
	{
		final BindingActivity activity = getActivity();
		
		IllegalArgumentException ex = AssertException(new Action() {
					
					@Override
					void Do() {
						activity.bindActivityResult(new WrongSignature1());
					}
				},IllegalArgumentException.class);
		Assert.assertTrue(
				ex.getMessage(),
				ex.getMessage().startsWith("com.geovah.guessanumber.test.BindingActivityTest$WrongSignature1.Wrong"));
	}
	
	public void testBindActivityResultInvoke()
	{
		BindingActivity act = getActivity();
		RighSignature right  = new RighSignature();
		
		Assert.assertEquals(Integer.MIN_VALUE,right.ResultCode);
		Assert.assertNull(right.Data);
		Assert.assertFalse(right.Invoke);
		act.bindActivityResult(right);
		//TODO : check the result has been invoke !
		
	
	}
}

package com.geovah.guessnumber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

public class BindingActivity extends gueei.binding.app.BindingActivity implements IActivityManager {
    
    private HashMap<Integer,Pair<Object,Method>> mActivityResultHanlder = new HashMap<Integer, Pair<Object,Method>>() ;
    
    
    public void bindActivityResult(Object... contentViewModel) throws ArgumentNullException,IllegalArgumentException,NullPointerException
    {
         
         if (contentViewModel == null){
                        throw new ArgumentNullException("contentViewModel");
                }
         
         
        
        for(Object viewModel : contentViewModel)
    	{
        	 
             
    		for(Method m :viewModel.getClass().getMethods())
        	{
    			
    			
        		ActivityResultBinding annotation = m.getAnnotation(ActivityResultBinding.class);

    			if(annotation != null)
    			{
    				Class<?>[] parametersType =  m.getParameterTypes();
    				if(parametersType== null || parametersType.length != 2 ||
    						parametersType[0] != int.class || parametersType[1] != Intent.class)
    				{
    					throw new IllegalArgumentException(
    							String.format("%1$s.%2$s must be void (int,Intent)", viewModel.getClass().getName(), m.getName()));
    				}
    				
    				if(mActivityResultHanlder.containsKey(annotation.ActivityId()))
    				{
    					Pair<Object,Method> existing = mActivityResultHanlder.get(annotation.ActivityId());
    					throw new IllegalArgumentException(
							String.format("id %1$d from %2$s.%3$s is already handle by %4$s.%5$s", 
									annotation.ActivityId(),
									viewModel.getClass().getName(), m.getName(),
									existing.first.getClass().getName(), existing.second.getName()));
    				}
    				
    				
    				
    				mActivityResultHanlder.put(annotation.ActivityId(),
    						new Pair<Object,Method>(viewModel,m)
    						);
    			}
    			
        	}
    	}
    }


    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	
		try {
			if(mActivityResultHanlder.containsKey(requestCode))
			{
				Pair<Object,Method> handler = mActivityResultHanlder.get(requestCode);
				handler.second.invoke(handler.first, resultCode,data);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
    	
    }
    
    public Context getContext() { return this;}
}


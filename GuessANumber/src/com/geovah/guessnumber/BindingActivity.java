package com.geovah.guessnumber;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import android.content.Intent;
import android.util.Pair;

public class BindingActivity extends gueei.binding.app.BindingActivity {
    
    private HashMap<Integer,Pair<WeakReference<Object>,Method>> mActivityResultHanlder = new HashMap<Integer, Pair<WeakReference<Object>,Method>>() ;
    
    
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
    				
    				
    				
    				mActivityResultHanlder.put(annotation.ActivityId(),
    						new Pair<WeakReference<Object>,Method>(
    								new WeakReference<Object>(viewModel),
    								m
    								)
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
				Pair<WeakReference<Object>,Method> handler = mActivityResultHanlder.get(requestCode);
				Object object = handler.first;
				if(object != null) //our object has been destroyed
				{
					handler.second.invoke(object, resultCode,data);
				}
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
}


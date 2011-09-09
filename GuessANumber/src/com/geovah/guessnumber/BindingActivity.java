package com.geovah.guessnumber;

import java.io.Console;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.ConsoleHandler;

import android.content.Intent;
import android.os.Bundle;

public class BindingActivity extends gueei.binding.app.BindingActivity {
    
    private Object mCcontentViewModel;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
    }
    
    protected void bindActivityResult(Object contentViewModel){
           
    		mCcontentViewModel = contentViewModel;
    }


    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	for(Method m :mCcontentViewModel.getClass().getMethods())
    	{
    		ActivityResultBinding annotation = m.getAnnotation(ActivityResultBinding.class);
    	
			if(annotation != null && annotation.ActivityId() == requestCode)
			{
				try {
					m.invoke(mCcontentViewModel, resultCode,data);
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
    }
}


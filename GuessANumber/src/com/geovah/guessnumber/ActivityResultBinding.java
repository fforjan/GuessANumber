package com.geovah.guessnumber;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface ActivityResultBinding {
	public int ActivityId();
}

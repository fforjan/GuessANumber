package com.geovah.guessnumber;

public class ArgumentNullException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6820783093704865366L;

	/**
	 * @param argumentName parameter name
	 */
	public ArgumentNullException(String parameterName)
	{
		super(parameterName);
	}
	
	/**
	 * @return the parameter involved in this exception
	 */
	public String getParameterName() { return this.getMessage();}
}

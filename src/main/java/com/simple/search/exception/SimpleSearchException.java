package com.simple.search.exception;

public class SimpleSearchException extends Exception
{
	private static final long serialVersionUID = 1L;

	public SimpleSearchException() {};
	
	public SimpleSearchException(String message)
	{
		super(message);
	}
}

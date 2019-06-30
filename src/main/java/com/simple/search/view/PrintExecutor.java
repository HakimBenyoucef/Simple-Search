package com.simple.search.view;

import java.util.Observable;

public class PrintExecutor extends Observable implements View
{
	private StringBuilder builder;
	
	
	@Override
	public void printTitle() {
		builder = new StringBuilder();
		builder.append("*********** Welcom to the Simple Search Engine ***********");
		
		setChanged();
        notifyObservers(builder.toString());
	}
	
	
	@Override
	public void printUsage()
	{
		builder = new StringBuilder();
		builder.append("\n");
		builder.append("*******************************************");
		builder.append("\n");
		builder.append("Usage: java -jar <jarFile> Searcher <path>");
		builder.append("\n");
		builder.append("*******************************************");
		builder.append("\n");
		
		setChanged();
        notifyObservers(builder.toString());
	}
	
	@Override
	public void printExit() {
		builder = new StringBuilder();
		builder.append("Exit...");
		
        setChanged();
        notifyObservers(builder.toString());
	}
	
	@Override
	public void printMessage(String message)
	{
		builder = new StringBuilder();
		builder.append(message);
		
		setChanged();
        notifyObservers(builder.toString());
	}
	
	@Override
	public StringBuilder getBuilder() {
		return this.builder;
	}


}

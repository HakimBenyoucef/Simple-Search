package com.simple.search.view;

import java.io.Console;
import java.util.Observable;

public class PrintExecutor implements View
{
	private Console console =  System.console();
	
	
	@Override
	public String prompt() {
		String search = "search> ";
		return console.readLine(search);
    }
	
	@Override
	public void printTitle() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("*********** Welcom to the Simple Search Engine ***********");
		builder.append("\n");
		builder.append("\n");
		builder.append("Please type the word(s) that you're looking for");
		builder.append("\n"); 
		console.printf(builder.toString());
	}
	
	
	@Override
	public void printUsage()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("*******************************************");
		builder.append("\n");
		builder.append("Usage: java -jar <jarFile> Searcher <path>");
		builder.append("\n");
		builder.append("*******************************************");
		builder.append("\n");
		console.printf(builder.toString());
	}

	@Override
	public void update(Observable o, Object arg) {
		StringBuilder builder = new StringBuilder();
		builder.append(arg.toString());
		builder.append("\n");
		console.printf(builder.toString());
		
	}


}

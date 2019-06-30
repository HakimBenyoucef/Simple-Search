package com.simple.search.controller;

import java.io.Console;
import java.util.Observable;
import java.util.Observer;

import com.simple.search.exception.SimpleSearchException;
import com.simple.search.model.Model;

public class ControllerImpl implements Controller, Observer {
	private Console console;
	
	private final Model model;
    
    private static Controller instance = null;
    
    private ControllerImpl(Model model)
    {
    	this.model = model;
    	this.console = System.console();
    }

    public static Controller getInstance(Model model) {
    	if (instance == null)
    	{
    		instance = new ControllerImpl(model);
    	}
    	return instance;
    }
    
    @Override
	public void run() throws SimpleSearchException {
		while (true) {
			String wanted = prompt();
			model.processCommand(wanted);
		}
	}
	
	private String prompt() {
		String search = "search> ";
		return console.readLine(search);
    }
	

	@Override
	public void update(Observable o, Object arg) {
		StringBuilder builder = new StringBuilder();
		builder.append(arg.toString());
		builder.append("\n");
		console.printf(builder.toString());
		
	}

}

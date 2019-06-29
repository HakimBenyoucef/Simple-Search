package com.simple.search.controller;

import com.simple.search.model.Model;
import com.simple.search.view.View;

public class Controller 
{
	private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
	public void run() {
		view.printTitle();
		while (true) {
			String wanted = view.prompt();
			model.processCommand(wanted);
		}
		
	}

}

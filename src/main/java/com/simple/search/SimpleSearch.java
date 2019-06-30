package com.simple.search;

import java.io.IOException;

import com.simple.search.controller.Controller;
import com.simple.search.controller.ControllerImpl;
import com.simple.search.exception.SimpleSearchException;
import com.simple.search.model.ModelImpl;
import com.simple.search.model.Model;
import com.simple.search.view.ViewImpl;
import com.simple.search.view.View;


public class SimpleSearch {
	public static void main(String[] args) {
		View view = new ViewImpl();
		parseArgs(args, view);
		
		Model model = new ModelImpl(args[1]);
		
		Controller controller = ControllerImpl.getInstance(model);
		((ViewImpl)view).addObserver((ControllerImpl)controller);
		view.printTitle();
		
		((ModelImpl)model).addObserver((ControllerImpl)controller);
		
		try
		{
			model.init();
			controller.run();
		}
		catch(SimpleSearchException e)
		{
			view.printMessage(e.getMessage());
			exit(view);
		} catch (IOException e) {
			exit(view);
		} 
		
	}

	private static void parseArgs(String[] args, View view) {
		
		if (args.length != 2
				|| (args.length ==2 && !args[0].equals("Searcher"))) {
			printUsage();
			exit(view);
		}
	}

	private static void exit(View view) {
		view.printExit();
		System.exit(1);
	}
	
	private static void printUsage()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("*******************************************");
		builder.append("\n");
		builder.append("Usage: java -jar <jarFile> Searcher <path>");
		builder.append("\n");
		builder.append("*******************************************");
		builder.append("\n");
		
		System.console().printf(builder.toString());
	}
}

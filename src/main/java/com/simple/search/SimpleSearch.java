package com.simple.search;

import java.io.IOException;

import com.simple.search.controller.Controller;
import com.simple.search.exception.SimpleSearchException;
import com.simple.search.model.FilesExplorer;
import com.simple.search.model.Model;
import com.simple.search.view.PrintExecutor;
import com.simple.search.view.View;


public class SimpleSearch {
	public static void main(String[] args) {
		View view = new PrintExecutor();
		parseArgs(args, view);
		
		Model model = new FilesExplorer(args[1]);
		
		Controller controller = Controller.getInstance(model);
		((PrintExecutor)view).addObserver(controller);
		view.printTitle();
		
		((FilesExplorer)model).addObserver(controller);
		
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
			exit(view);
		}
	}

	private static void exit(View view) {
		view.printExit();
		System.exit(1);
	}
}

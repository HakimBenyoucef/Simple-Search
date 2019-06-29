package com.simple.search;

import com.simple.search.controller.Controller;
import com.simple.search.model.FileHierarchy;
import com.simple.search.model.Model;
import com.simple.search.view.PrintExecutor;
import com.simple.search.view.View;

/**
 * Hello world!
 *
 */
public class SimpleSearch {
	public static void main(String[] args) {
		View view = new PrintExecutor();
		parseArgs(args, view);
		
		Model model = new FileHierarchy(args[1]);
		((FileHierarchy)model).addObserver(view);
		model.init();
		
		Controller controller = new Controller(model, view);
		controller.run();
	}

	// Maybe we need in the future to check inputs typed by the user
	private static void parseArgs(String[] args, View view) {
		
		if (args.length != 2
				|| (args.length ==2 && !args[0].equals("Searcher"))) {
			view.printUsage();
			System.exit(1);
		} 
		// check inputs
	}
}

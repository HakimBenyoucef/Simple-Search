package com.simple.search.view;

public interface View {

	void printTitle();

	void printUsage();
	
	void printExit();

	void printMessage(String message);

	StringBuilder getBuilder();

}

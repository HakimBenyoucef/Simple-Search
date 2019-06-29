package com.simple.search.view;

import java.util.Observer;

public interface View extends Observer {
	
	String prompt();

	void printTitle();

	void printUsage();

}

package com.simple.search.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.simple.search.view.ViewImpl;
import com.simple.search.view.View;

public class ViewTest {
	

	private static final Logger LOG = Logger.getLogger(ViewTest.class.getName());
	
	@Rule
	public TestWatcher watchman = new TestWatcher() 
	{
		@Override
		protected void starting(Description description) {
			LOG.info(" ==== "+description.getClassName()+ " : "
					+ description.getMethodName() + " ==== ");
		}
	};
	
	@Test
	public void printTitileTest()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("*********** Welcom to the Simple Search Engine ***********");
		
		View view = new ViewImpl();
		assertNull(view.getBuilder());
		view.printTitle();
		
		assertEquals(view.getBuilder().toString(), builder.toString());
	}
	
	@Test
	public void printExitTest()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Exit...");
		
		View view = new ViewImpl();
		assertNull(view.getBuilder());
		view.printExit();
		
		assertEquals(view.getBuilder().toString(), builder.toString());
	}
	
	@Test
	public void printMessageTest()
	{
		String message = "This is a message";
		StringBuilder builder = new StringBuilder();
		builder.append(message);
		
		View view = new ViewImpl();
		assertNull(view.getBuilder());
		view.printMessage(message);
		
		assertEquals(view.getBuilder().toString(), builder.toString());
	}
	
	 

}

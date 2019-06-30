package com.simple.search.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.simple.search.model.FilesExplorer;
import com.simple.search.model.Model;

public class ControllerTest 
{
	private static final Logger LOG = Logger.getLogger(ControllerTest.class.getName());
	
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
	public void singularityTest()
	{
		Model model = new FilesExplorer("path");
		Controller controller1 = Controller.getInstance(model);
		
		assertNotNull(controller1);
		model = new FilesExplorer("another/path");
		Controller controller2 = Controller.getInstance(model);
		
		assertSame(controller1, controller2);
	}
	
	
}

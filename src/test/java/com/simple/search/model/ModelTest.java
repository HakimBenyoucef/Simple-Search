package com.simple.search.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.simple.search.exception.SimpleSearchException;

public class ModelTest 
{
	private static final Logger LOG = Logger.getLogger(ModelTest.class.getName());
	private static final String WANTED_WORDS = 
			"to be or not to be Lorem ipsum dolor sit amet consectetur adipiscing elit";
	private static final String UNEXISTING_WORD = "ThisInputDoesNotExist";
	private static final String TEST_DIRECTORY_PATH = "src/test/resources/";
	
	private Model model;
	
	@Rule
	public TestWatcher watchman = new TestWatcher() 
	{
		@Override
		protected void starting(Description description) {
			LOG.info(" ==== "+description.getClassName()+ " : "
					+ description.getMethodName() + " ==== ");
		}
	};
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void init() throws IOException
	{
		model = new FilesExplorer(TEST_DIRECTORY_PATH);
		model.init();
	}
	
	@Test
	public void wrongDirectoryTest() throws IOException
	{
		String wrongPath = "/home/benyoucef";
		expectedEx.expect(IOException.class);
	    expectedEx.expectMessage("Directory not found '"
        		+wrongPath+"'");
		model = new FilesExplorer(wrongPath);
		model.init();
	}
	
	@Test
	public void notADirectoryTest() throws IOException
	{

		String wrongPath = TEST_DIRECTORY_PATH+"file1.txt";
		expectedEx.expect(NotDirectoryException.class);
	    expectedEx.expectMessage("'"+wrongPath+ "' is not a directory");
		model = new FilesExplorer(wrongPath);
		model.init();
	}
	 
	@Test
	public void correctDirectoryTest() throws IOException
	{		
		assertNotNull(model.getFilesContent());
		assertTrue(model.getFilesContent().size() > 0);
	}
	
	@Test
	public void processExitCommandTest() throws IOException, SimpleSearchException
	{
		expectedEx.expect(SimpleSearchException.class);
	    expectedEx.expectMessage("Command exit pressed...");
		
		model.processCommand(FilesExplorer.EXIT_COMMAND);
	}

	@Test
	public void processUnexistingWordTest() throws IOException, SimpleSearchException
	{
		model.processCommand(UNEXISTING_WORD);
		
		assertNotNull(model.getFilesContent());
		assertTrue(model.getFilesContent().size() > 0);
		assertTrue(model.getScoreMap().size() == 0);		
	}
	
	@Test
	public void processCommandTest() throws IOException, SimpleSearchException
	{
		model.processCommand(WANTED_WORDS);
		
		assertNotNull(model.getFilesContent());
		assertTrue(model.getFilesContent().size() > 0);
		
		//Test descending order
		int lastValue = 100;
		for(int value : model.getScoreMap().values()) {
			assertFalse("The scoreMap is unordered!!", value > lastValue);
			lastValue = value;
		}
		
		assertEquals(Integer.valueOf(100), model.getScoreMap().get("wantedWords.sh"));
		assertEquals(Integer.valueOf(100), model.getScoreMap().get("file1.txt"));
		assertEquals(Integer.valueOf(91), model.getScoreMap().get("file2.txt"));
		assertEquals(Integer.valueOf(83), model.getScoreMap().get("file12.txt"));
		assertEquals(Integer.valueOf(75), model.getScoreMap().get("file8.txt"));
		assertEquals(Integer.valueOf(66), model.getScoreMap().get("file5.txt"));
		assertEquals(Integer.valueOf(66), model.getScoreMap().get("file3.txt"));
		assertEquals(Integer.valueOf(66), model.getScoreMap().get("file7.txt"));
		assertEquals(Integer.valueOf(66), model.getScoreMap().get("file11.txt"));
		assertEquals(Integer.valueOf(66), model.getScoreMap().get("file6.txt"));
		
	}

}

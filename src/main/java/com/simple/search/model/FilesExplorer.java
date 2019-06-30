package com.simple.search.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.simple.search.exception.SimpleSearchException;

public class FilesExplorer extends Observable implements Model
{
	private static final String NON_ALPHABETIC_CHARS_REGEX = "[^a-zA-Z0-9]";
	public static final String EXIT_COMMAND = ":quit";
	public static final int TOP = 10;
	
	private Path directoryPath;
	
	private Map<String, List<String>> filesContent;
	
	private Map<String, Integer> scoreMap;
	
	public FilesExplorer(String directoryPath)
	{
		this.directoryPath = Paths.get(directoryPath);
	}
	
	@Override
	public void init() throws IOException {
		readFiles();
		notifyObserver();
	}
	
	@Override
	public Path getPath()
	{
		return directoryPath;
	}
	
	@Override
	public Map<String, List<String>> getFilesContent()
	{
		return filesContent;
	}
	
	@Override
	public Map<String, Integer> getScoreMap()
	{
		return scoreMap;
	}
	

	private void readFiles() throws IOException
	{
		filesContent = new HashMap<>();
		Stream<Path> stream = Stream.empty();
		try {
			stream = Files.list(directoryPath) // get the directory content
					.filter(Files::isRegularFile); // filter only regular files
			
			stream.forEach(file -> { // for each file:
						List<String> content = new ArrayList<>();
						try {
							Files.lines(file) // iterate its lines
									.forEach(line -> { // for each line
										// split it into words
										String[] tokens = line.split(NON_ALPHABETIC_CHARS_REGEX);
										for (String token : tokens) {
											// then add each word to the content list
											content.add(token);
										}
									});
							// add the file content to the map which is indexed by files name
							filesContent.put(file.getFileName().toString(), content);
						} catch (IOException e) {
							setChanged();
							notifyObservers("Error occured when opening the file: '" + file.getFileName() + "'");
						}
					});

		} catch (NotDirectoryException e) {
			String message = "'" + directoryPath.toString() + "' is not a directory";
			setChanged();
			notifyObservers(message);
			throw new NotDirectoryException(message);
		} catch (IOException e) {
			String message = "Directory not found '" + directoryPath.toString() + "'";
			setChanged();
			notifyObservers(message);
			throw new IOException(message);
		}
		finally
		{
			stream.close();
		}
	}
	
	private void notifyObserver()
	{
		setChanged();
        notifyObservers(filesContent.size()+" file(s) read in directory '"
		+directoryPath.toString()+"'");
	}
	
	private boolean fileContains(List<String> content, String word)
	{
		return content.contains(word);
	}
	
	private int calculateScore(int total, int found)
	{
		return (found * 100) / total;
	}
	
	

	@Override
	public void processCommand(String wanted) throws SimpleSearchException
	{
		if (wanted.equals(EXIT_COMMAND))
		{
			throw new SimpleSearchException("Command exit pressed...");
		}
		else if (wanted.isEmpty())
		{
			// Do nothing
		}
		else
		{
			if (filesContent != null && !filesContent.isEmpty()) {
				scoreMap = new HashMap<>();
				Set<String> words = new HashSet<>(Arrays.asList(wanted.split(" ")));
				
				search(words);
				fillMap();
				
			} else {
				String message = 
						"No files found in directory: '" 
								+ directoryPath + "'," + " please choose another directory";
				throw new SimpleSearchException(message);
			}
		}		
	}

	private void search(Set<String> words) {

		filesContent.forEach((key,value) -> {
			List<String> wordsFound = new ArrayList<>();
			for (String word : words) {
				if (fileContains(value, word)) {
					wordsFound.add(word);
					continue;
				}
			}
			int total = words.size();
			int found = wordsFound.size();
			if (found > 0) {
				int fileScore = calculateScore(total, found);
				scoreMap.put(key, fileScore);
			}
		});
	}

	private void fillMap() {
		if (scoreMap.isEmpty()) {
			setChanged();
			notifyObservers("no matches found");
		} else {
			StringBuilder builder = new StringBuilder();
			sortScoreMap();
			scoreMap.entrySet().stream()
					.limit(TOP)		
					.forEachOrdered(entry -> {
						builder.append("\n");
						builder.append(entry.getKey());
						builder.append(":");
						builder.append(entry.getValue());
						builder.append("%%");
					});

			setChanged();
			notifyObservers(builder.toString());
		}
	}
	
	private void sortScoreMap()
	{
		scoreMap = scoreMap.entrySet().stream()
				.sorted
				(Map.Entry.<String, Integer>comparingByValue().reversed())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

	}
}

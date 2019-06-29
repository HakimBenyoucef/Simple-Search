package com.simple.search.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Collectors;

public class FileHierarchy extends Observable implements Model
{
	private final String NON_ALPHABETIC_CHARS_REGEX = "[^a-zA-Z0-9]";
	private final String EXIT_COMMAND = ":quit";
	private final int TOP = 10;
	
	private Path directoryPath;
	
	private List<Path> filesList; // Files found in the directory
	
	private Map<Path, Integer> scoreMap;
	
	public FileHierarchy(String directoryPath)
	{
		this.directoryPath = Paths.get(directoryPath);
	}
	
	@Override
	public void init() {
		filesList = getDirectoryContent();
	}
	

	private List<Path> getDirectoryContent()
	{
		try {
			List<Path> list = Files.list(directoryPath)
					.filter(Files::isRegularFile)
					.collect(Collectors.toList());
			
			setChanged();
	        notifyObservers(list.size()+" file(s) read in directory '"
			+directoryPath.toString()+"'");
	        
	        return list;
			
		} catch (Exception e) {
			setChanged();
	        notifyObservers("Error occured when opening the directory '"
	        		+directoryPath.toString()+"'");
	        exit();
	        return null;
		}
	}
	
	private boolean contains(Path path, String word)
	{
		try {
			// iterate through file lines

			return Files.lines(path)
					.anyMatch(s -> {
						String[] tokens = s.split(NON_ALPHABETIC_CHARS_REGEX);

						for(String token : tokens)
						{
							if(token.equals(word))
							{
								return true;
							}
						}
						return false;
					});
			
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error occured when opening the file: '"
					+path.getFileName()+"'");
			return false;
		}
	}
	
	private int calculateScore(int total, int found)
	{
		return (found * 100) / total;
	}
	
	

	@Override
	public void processCommand(String wanted)
	{
		if (wanted.equals(EXIT_COMMAND))
		{
			exit();
		}
		else if (wanted.isEmpty())
		{
			// Do nothing
		}
		else
		{
			if (filesList != null && !filesList.isEmpty()) {
				scoreMap = new HashMap<>();
				Set<String> words = new HashSet<>(Arrays.asList(wanted.split(" ")));
				
				search(words);
				fillMap();
				
			} else {
				setChanged();
				notifyObservers(
						"No files found in directory: '" + directoryPath + "'," + " please choose another directory");
				exit();
			}
		}		
	}

	private void search(Set<String> words) {

		filesList.forEach(f -> {

			List<String> wordsFound = new ArrayList<>();
			for (String word : words) {
				if (contains(f, word)) {
					wordsFound.add(word);
					continue;
				}
			}
			int total = words.size();
			int found = wordsFound.size();
			if (found > 0) {
				int fileScore = calculateScore(total, found);
				scoreMap.put(f, fileScore);
			}
		});
	}

	private void fillMap() {
		if (scoreMap.isEmpty()) {
			setChanged();
			notifyObservers("no matches found");
		} else {
			StringBuilder builder = new StringBuilder();
			scoreMap.entrySet().stream().sorted(Map.Entry.<Path, Integer>comparingByValue().reversed())
					.limit(TOP)		
					.forEachOrdered(entry -> {
						builder.append("\n");
						builder.append(entry.getKey().getFileName());
						builder.append(":");
						builder.append(entry.getValue());
						builder.append("%%");
					});

			setChanged();
			notifyObservers(builder.toString());
		}
	}

	private void exit() {
		setChanged();
		notifyObservers("Exit...");
		System.exit(1);
	}
	
}

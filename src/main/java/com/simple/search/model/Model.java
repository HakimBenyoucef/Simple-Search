package com.simple.search.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.simple.search.exception.SimpleSearchException;

public interface Model {
	
	void processCommand(String wanted) throws SimpleSearchException;

	void init() throws IOException;

	Map<String, List<String>> getFilesContent();

	Map<String, Integer> getScoreMap();

	Path getPath();
}

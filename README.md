# Simple-Search
## Description
Simple-Search is a command line driven text search engine. It allows users search a couple of words within a given directory.
It reads all the text files in the given directory, building an in-memory representation of the files and their contents, and then give a command prompt at which interactive searches can be performed.

it takes the words given on the prompt and return a list of the top 10 (maximum) matching filenames in rank order, giving the rank score against each match.

## Prerequisites
- Java 8 (or higher) installed.
- Apache Maven (to compile code, build jar and run tests).

## Building the jar file
There is an existing jar file in `target`, but if you want to update the source code and generate a new jar follow these instructions:
- Open a terminal then run `mvn clean install` to build a jar and run existing tests.
- The jar file will be generated in the `target` folder.

## Usage
`java -jar <jarFile> Searcher <path>`
### Example
`java -jar target/Simple-Search-0.0.1-SNAPSHOT.jar Searcher src/test/resources/`



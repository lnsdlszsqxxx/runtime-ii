# How to use logger without maven

## Method 1
1. Download **slf4j-api-1.7.9.jar** and **slf4j-simple-1.7.9.jar** from [Maven central](http://repo2.maven.org/maven2/org/slf4j/).
2. Copy these two files to the java source file (testJar.java) directory.
3. Extract two jar files by ```jar xf jarfilename```
4. Logger is ready to use
5. Compile by ```javac testJar.java```
6. Run the compiled file by ```Java testJar``` (don't add .class)

## Method 2
In this method you don't need to extract the jar files.
1. Download **slf4j-api-1.7.9.jar** and **slf4j-simple-1.7.9.jar** from [Maven central](http://repo2.maven.org/maven2/org/slf4j/).
2. Copy these two files to the java source file directory.
3. Compile source file ```javac -cp ".:slf4j-api-1.7.9.jar:slf4j-simple-1.7.9.jar:" testJar.java``` (double quotation marks are not necessary)
4. Run source file ```java -cp ".:slf4j-api-1.7.9.jar:slf4j-simple-1.7.9.jar:" testJar``` (double quotation marks are not necessary)

References:
[SLF4J official web site](http://www.slf4j.org/manual.html)
[How to include jar files with java file and compile in command prompt](https://stackoverflow.com/questions/9395207/how-to-include-jar-files-with-java-file-and-compile-in-command-prompt)
[Jar files in Java](https://www.geeksforgeeks.org/jar-files-java/)

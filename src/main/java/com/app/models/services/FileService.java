package com.app.models.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileService {
  private final File FILE;
  private final String PATH;

  /**
   * A Path gets passed as argument, exception gets handled on controller  
   * @param path Relative path to the file that you try to access
   * @throws IOException
   */
  public FileService(String path) throws IOException {
    PATH = path;
    FILE = new File(PATH);
    createFileOnPath();
  }

  /**
   * Loops through file and returns an Array List og strings
   *
   * @return a String array from the file you want read
   * @throws FileNotFoundException
   */
  public String[] readFromFile() throws FileNotFoundException {
    ArrayList<String> result = new ArrayList();

    Scanner reader = new Scanner(FILE);

    while (reader.hasNextLine()) {
      result.add(reader.nextLine());
    }
    reader.close();

    return result.toArray(new String[0]);
  }

  /**
   * Recieves an array of strings and creates a new file
   *
   * @param input of Strings that you want to write to the file
   * @throws FileNotFoundException
   */
  public void writeToFile(String[] input) throws FileNotFoundException {
    PrintStream printStream = new PrintStream(FILE);
    for (String s : input) {
      printStream.println(s);
    }
    printStream.close();
  }

  /**
   * If a file does not exist, create a new file in path recieved
   *
   * @throws IOException
   */
  private void createFileOnPath() throws IOException {
    if (!FILE.exists()) {
      String[] subPaths = PATH.split("/");
      StringBuilder dirPath = new StringBuilder();
      for (int i = 0; i < subPaths.length - 1; i++) {
        dirPath.append(subPaths[i]).append("/");
      }
      File dirs = new File(dirPath.toString());
      dirs.mkdirs();
      FILE.createNewFile();
    }
  }
}

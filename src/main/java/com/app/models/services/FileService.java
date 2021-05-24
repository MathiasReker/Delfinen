package com.app.models.services;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileService {
  private final File FILE;
  private final String PATH;

  /**
   * A Path gets passed as argument, exception gets handled on controller
   *
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
    ArrayList<String> result = new ArrayList<>();
    Scanner reader = new Scanner(FILE);

    while (reader.hasNextLine()) {
      result.add(reader.nextLine());
    }
    reader.close();

    return result.toArray(new String[0]);
  }

  /**
   * Receives an array of strings and creates a new file
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
   * Create file if it does not exist.
   *
   * @throws IOException if the directory or file cannot be created.
   */
  private void createFileOnPath() throws IOException {
    if (!FILE.exists()) {
      Path path = Paths.get(PATH);
      Files.createDirectories(path.getParent());
      Files.createFile(path);
    }
  }

  public void writeToBin(byte[] bytes) throws IOException {
    FileOutputStream fos = new FileOutputStream(PATH);
    fos.write(bytes);
    fos.close();
  }

  public byte[] loadFromBin() throws IOException {
    byte[] result;
    FileInputStream fis = new FileInputStream(PATH);
    result = fis.readAllBytes();
    fis.close();

    return result;
  }

  public ArrayList<String> readTextFile() throws FileNotFoundException {
    ArrayList<String> result = new ArrayList<>();

    Scanner reader = new Scanner(FILE);

    while (reader.hasNextLine()) {
      result.add(reader.nextLine());
    }
    reader.close();

    return result;
  }
}

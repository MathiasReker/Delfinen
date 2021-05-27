package com.app.models.services;

import java.io.*;

public class ObjectService {
  FileService FILE_SERVICE;

  public ObjectService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
  }

  /**
   * Save objects to bin.
   *
   * @param objects Object[]
   * @throws IOException
   * @auther Andreas, Mathias
   */
  public void save(Object[] objects) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    objectOutputStream.writeObject(objects);
    byte[] competitionsInBytes = byteArrayOutputStream.toByteArray();
    objectOutputStream.close();
    byteArrayOutputStream.close();
    FILE_SERVICE.writeToBin(competitionsInBytes);
  }

  /**
   * Load objects from bin.
   *
   * @throws IOException
   * @auther Andreas, Mathias
   */
  public Object[] load() throws IOException, ClassNotFoundException {
    byte[] competitionsInByte = FILE_SERVICE.loadFromBin();
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(competitionsInByte);
    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
    Object[] result = (Object[]) objectInputStream.readObject();
    objectInputStream.close();
    byteArrayInputStream.close();

    return result;
  }
}

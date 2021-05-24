package com.app.models.services;

import java.io.*;

public class IOService {
  FileService FILE_SERVICE;

  public IOService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);

  }

  public void save(Object[] objects) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(objects);
    byte[] competitionsInBytes = baos.toByteArray();
    oos.flush();
    oos.close();
    baos.close();
    FILE_SERVICE.writeToBin(competitionsInBytes);
  }

  public Object[] load() throws IOException, ClassNotFoundException {
    byte[] competitionsInByte = FILE_SERVICE.loadFromBin();
    ByteArrayInputStream bais = new ByteArrayInputStream(competitionsInByte);
    ObjectInputStream ois = new ObjectInputStream(bais);
    Object[] result = (Object[]) ois.readObject();
    ois.close();
    bais.close();

    return result;
  }
}

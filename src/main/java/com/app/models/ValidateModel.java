package com.app.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidateModel {
  public static boolean isValidDate(String date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    simpleDateFormat.setLenient(false);
    try {
      simpleDateFormat.parse(date);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public static boolean isValidName(String text) {
    return text.matches(
        "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð"
            + " ,.'-]+$");
  }

  public static boolean isValidMail(String text) {
    return text.matches(
        "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
  }

  public static boolean isValidPhoneNumber(String text) {
    return text.matches("^([+](\\d{1,3})\\s?)?((\\(\\d{3,5}\\)|\\d{3,5})(\\s)?)\\d{3,8}$");
  }
}

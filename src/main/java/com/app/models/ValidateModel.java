package com.app.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidateModel {
  public static boolean isValidDate(String date) {
    try {
      LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isValidCompetitionTime(String time) {
    try {
      LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isValidResultTime(String time) {
    try {
      LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss:SSS"));
      return true;
    } catch (DateTimeParseException e) {
      e.printStackTrace();
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

  public static boolean isValidRange(int in, int min, int max) {
    return in <= max && in >= min;
  }
}

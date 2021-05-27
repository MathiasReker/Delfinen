package com.app.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class InputModel {
  public static boolean isValidBirthDate(String date) {
    try {
      int age = getAge(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));

      return age >= 1 && age <= 150;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isValidDate(String date) {
    try {
      LocalDate dateCheck = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      return dateCheck.isAfter(LocalDate.now().minusDays(1));
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  private static int getAge(LocalDate birthdate) {
    LocalDate currentDate = LocalDate.now();

    return Period.between(birthdate, currentDate).getYears();
  }

  public static boolean isValidCompetitionTime(String time) {
    try {
      LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isValidCompetitionResultTime(String time) {
    try {
      LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss:SS"));
      return true;
    } catch (DateTimeParseException e) {
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

  public static boolean isValidMemberId(String in, ArrayList<MemberModel> members) {
    for (MemberModel m : members) {
      if (m.getId().equals(in)) {
        return true;
      }
    }

    return false;
  }

  public static boolean isValidCompetitionId(String in, ArrayList<SwimEventModel> competitions) {
    for (SwimEventModel c : competitions) {
      if (c.getId().equals(in)) {
        return true;
      }
    }

    return false;
  }
}

package com.app.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class LeaderboardModel {
  private String competitionNames;
  private String memberNames;
  private LocalTime memberTimes;

  public LeaderboardModel(String competitionNames, String memberNames, LocalTime memberTimes) {
    setCompetitionNames(competitionNames);
    setMemberNames(memberNames);
    setMemberTimes(memberTimes);
  }

  public String getCompetitionNames() {
    return competitionNames;
  }

  public String getMemberNames() {
    return memberNames;
  }

  public LocalTime getMemberTimes() {
    return memberTimes;
  }

  public void setCompetitionNames(String competitionNames) {
      this.competitionNames = competitionNames;
  }

  public void setMemberNames(String memberNames) {
      this.memberNames = memberNames;
  }

  public void setMemberTimes(LocalTime memberTimes) {
      this.memberTimes = memberTimes;
  }
  /*
  private final int TOP_FIVE = 5;
  private String[] competitionNames = new String[5];
  private String[] memberNames = new String[5];
  private String[] memberTimes = new String[5];

  public LeaderboardModel(String[] competitionNames, String[] memberNames, String[] memberTimes) {
    setCompetitionNames(competitionNames);
    setMemberNames(memberNames);
    setMemberTimes(memberTimes);
  }

  public String[] getCompetitionNames() {
    return competitionNames;
  }

  public String[] getMemberNames() {
    return memberNames;
  }

  public String[] getMemberTimes() {
    return memberTimes;
  }

  public void setCompetitionNames(String[] competitionNames) {
    if (competitionNames.length > TOP_FIVE) {
      throw new IndexOutOfBoundsException("Need 5 members");
    } else {
      this.competitionNames = competitionNames;
    }
  }

  public void setMemberNames(String[] memberNames) {
    if (competitionNames.length > TOP_FIVE) {
      throw new IndexOutOfBoundsException("Need 5 members");
    } else {
      this.memberNames = memberNames;
    }
  }

  public void setMemberTimes(String[] memberTimes) {
    if (competitionNames.length > TOP_FIVE) {
      throw new IndexOutOfBoundsException("Need 5 members");
    } else {
      this.memberTimes = memberTimes;
    }
  }

   */

}

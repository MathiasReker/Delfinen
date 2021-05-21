package com.app.models;

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

}

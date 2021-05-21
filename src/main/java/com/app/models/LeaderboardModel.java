package com.app.models;

import java.time.LocalTime;

public class LeaderboardModel {
  private String memberNames;
  private LocalTime memberTimes;

  public LeaderboardModel(String memberNames, LocalTime memberTimes) {
    setMemberNames(memberNames);
    setMemberTimes(memberTimes);
  }

  public String getMemberNames() {
    return memberNames;
  }

  public LocalTime getMemberTimes() {
    return memberTimes;
  }

  public void setMemberNames(String memberNames) {
    this.memberNames = memberNames;
  }

  public void setMemberTimes(LocalTime memberTimes) {
    this.memberTimes = memberTimes;
  }

}

package com.nicelife729.test.bean;

import java.util.List;

public class Delta {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * delta序列
   */
  private List<Double> deltaList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Double> getDeltaList() {
    return deltaList;
  }

  public void setDeltaList(List<Double> deltaList) {
    this.deltaList = deltaList;
  }
}

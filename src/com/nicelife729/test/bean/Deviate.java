package com.nicelife729.test.bean;

import java.util.List;

public class Deviate {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 偏离序列
   */
  private List<Double> deviateList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Double> getDeviateList() {
    return deviateList;
  }

  public void setDeviateList(List<Double> deviateList) {
    this.deviateList = deviateList;
  }
}

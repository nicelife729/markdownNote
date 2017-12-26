package com.nicelife729.test.bean;

import java.util.List;

public class ChangeRate {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 换手率序列
   */
  private List<Double> changeRateList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Double> getChangeRateList() {
    return changeRateList;
  }

  public void setChangeRateList(List<Double> changeRateList) {
    this.changeRateList = changeRateList;
  }
}

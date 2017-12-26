package com.nicelife729.test.bean;

import java.util.List;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class Lever {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 杠杆比例序列
   */
  private List<Double> leverList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Double> getLeverList() {
    return leverList;
  }

  public void setLeverList(List<Double> leverList) {
    this.leverList = leverList;
  }
}

package com.nicelife729.test.bean;

import java.util.List;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class Open {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 敞口序列
   */
  private List<Double> openList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Double> getOpenList() {
    return openList;
  }

  public void setOpenList(List<Double> openList) {
    this.openList = openList;
  }
}

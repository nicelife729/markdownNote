package com.nicelife729.test.bean;

import java.util.List;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class Position {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 仓位序列
   */
  private List<Double> positionList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Double> getPositionList() {
    return positionList;
  }

  public void setPositionList(List<Double> positionList) {
    this.positionList = positionList;
  }
}

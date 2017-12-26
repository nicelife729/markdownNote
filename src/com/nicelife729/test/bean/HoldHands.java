package com.nicelife729.test.bean;

import java.util.List;

public class HoldHands {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 持仓手数序列
   */
  private List<Integer> holdHandsList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Integer> getHoldHandsList() {
    return holdHandsList;
  }

  public void setHoldHandsList(List<Integer> holdHandsList) {
    this.holdHandsList = holdHandsList;
  }
}

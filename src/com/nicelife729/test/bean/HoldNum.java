package com.nicelife729.test.bean;

import java.util.List;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class HoldNum {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 持股数量序列
   */
  private List<Integer> holdNumList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Integer> getHoldNumList() {
    return holdNumList;
  }

  public void setHoldNumList(List<Integer> holdNumList) {
    this.holdNumList = holdNumList;
  }
}

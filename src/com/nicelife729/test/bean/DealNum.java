package com.nicelife729.test.bean;

import java.util.List;

public class DealNum {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 成交次数序列
   */
  private List<Integer> dealNumList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Integer> getDealNumList() {
    return dealNumList;
  }

  public void setDealNumList(List<Integer> dealNumList) {
    this.dealNumList = dealNumList;
  }
}

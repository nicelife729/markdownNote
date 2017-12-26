package com.nicelife729.test.bean;

import java.util.List;

public class DealHands {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 成交量(成交手数)序列
   */
  private List<Integer> dealHandsList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Integer> getDealHandsList() {
    return dealHandsList;
  }

  public void setDealHandsList(List<Integer> dealHandsList) {
    this.dealHandsList = dealHandsList;
  }
}

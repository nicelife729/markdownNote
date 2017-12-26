package com.nicelife729.test.bean;

import java.util.List;

public class DealRate {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 报单成功率序列
   */
  private List<Double> dealRateList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Double> getDealRateList() {
    return dealRateList;
  }

  public void setDealRateList(List<Double> dealRateList) {
    this.dealRateList = dealRateList;
  }
}

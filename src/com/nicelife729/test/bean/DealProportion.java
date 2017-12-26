package com.nicelife729.test.bean;

import java.util.List;
import java.util.Map;

public class DealProportion {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 成交占比序列
   */
  private List<Map<String, Double>> dealProportionList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Map<String, Double>> getDealProportionList() {
    return dealProportionList;
  }

  public void setDealProportionList(
      List<Map<String, Double>> dealProportionList) {
    this.dealProportionList = dealProportionList;
  }
}

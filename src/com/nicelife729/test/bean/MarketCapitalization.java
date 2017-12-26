package com.nicelife729.test.bean;

import java.util.List;
import java.util.Map;

public class MarketCapitalization {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 市值序列
   */
  private List<Map<String, Double>> marketValueList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Map<String, Double>> getMarketValueList() {
    return marketValueList;
  }

  public void setMarketValueList(
      List<Map<String, Double>> marketValueList) {
    this.marketValueList = marketValueList;
  }
}

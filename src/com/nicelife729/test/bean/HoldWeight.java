package com.nicelife729.test.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class HoldWeight {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 权重序列
   */
  private List<Map<String, Double>> weightList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Map<String, Double>> getWeightList() {
    return weightList;
  }

  public void setWeightList(List<Map<String, Double>> weightList) {
    this.weightList = weightList;
  }
}

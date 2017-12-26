package com.nicelife729.test.bean;

import java.util.List;

public class OrderNum {
  /**
   * 交易日历序列
   */
  private List<String> calenderList;

  /**
   * 委托次数序列
   */
  private List<Integer> orderNumList;

  public List<String> getCalenderList() {
    return calenderList;
  }

  public void setCalenderList(List<String> calenderList) {
    this.calenderList = calenderList;
  }

  public List<Integer> getOrderNumList() {
    return orderNumList;
  }

  public void setOrderNumList(List<Integer> orderNumList) {
    this.orderNumList = orderNumList;
  }
}

package com.nicelife729.test.bean;

public class TradeActivenesWarpper {

  /**
   * 委托次数
   */
  private OrderNum orderNum;
  /**
   * 成交次数
   */
  private DealNum dealNum;
  /**
   * 报单成功率
   */
  private DealRate dealRate;
  /**
   * 换手率
   */
  private ChangeRate changeRate;

  public OrderNum getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(OrderNum orderNum) {
    this.orderNum = orderNum;
  }

  public DealNum getDealNum() {
    return dealNum;
  }

  public void setDealNum(DealNum dealNum) {
    this.dealNum = dealNum;
  }

  public DealRate getDealRate() {
    return dealRate;
  }

  public void setDealRate(DealRate dealRate) {
    this.dealRate = dealRate;
  }

  public ChangeRate getChangeRate() {
    return changeRate;
  }

  public void setChangeRate(ChangeRate changeRate) {
    this.changeRate = changeRate;
  }
}

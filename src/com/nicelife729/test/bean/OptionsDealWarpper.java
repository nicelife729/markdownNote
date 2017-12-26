package com.nicelife729.test.bean;

public class OptionsDealWarpper {

  /**
   * 成交结构
   */
  private DealProportion dealProportion;

  /**
   * 成交手数
   */
  private DealHands dealHands;

  public DealProportion getDealProportion() {
    return dealProportion;
  }

  public void setDealProportion(DealProportion dealProportion) {
    this.dealProportion = dealProportion;
  }

  public DealHands getDealHands() {
    return dealHands;
  }

  public void setDealHands(DealHands dealHands) {
    this.dealHands = dealHands;
  }
}

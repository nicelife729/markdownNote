package com.nicelife729.test.bean;

public class FuturesDealWarpper {

  /**
   * 品种成交占比
   */
  private DealProportion verityDealProportion;

  /**
   * 板块成交占比
   */
  private DealProportion plateDealProportion;

  public DealProportion getVerityDealProportion() {
    return verityDealProportion;
  }

  public void setVerityDealProportion(DealProportion verityDealProportion) {
    this.verityDealProportion = verityDealProportion;
  }

  public DealProportion getPlateDealProportion() {
    return plateDealProportion;
  }

  public void setPlateDealProportion(DealProportion plateDealProportion) {
    this.plateDealProportion = plateDealProportion;
  }
}

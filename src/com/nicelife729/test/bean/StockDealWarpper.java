package com.nicelife729.test.bean;

public class StockDealWarpper {

  /**
   * 行业成交占比
   */
  private DealProportion industryDealProportion;

  /**
   * 风格成交占比
   */
  private DealProportion styleDealProportion;

  public DealProportion getIndustryDealProportion() {
    return industryDealProportion;
  }

  public void setIndustryDealProportion(DealProportion industryDealProportion) {
    this.industryDealProportion = industryDealProportion;
  }

  public DealProportion getStyleDealProportion() {
    return styleDealProportion;
  }

  public void setStyleDealProportion(DealProportion styleDealProportion) {
    this.styleDealProportion = styleDealProportion;
  }
}

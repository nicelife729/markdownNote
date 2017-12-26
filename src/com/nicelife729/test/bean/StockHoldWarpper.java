package com.nicelife729.test.bean;

import java.util.List;

public class StockHoldWarpper {

  /**
   * 仓位
   */
  private Position position;

  /**
   * 敞口
   */
  private Open open;

  /**
   * 前10重仓股
   */
  private List<PositionsPercent> top10;

  /**
   * 其他股票百分比
   */
  private List<PositionsPercent> otherStocks;

  /**
   * 持股数
   */
  private HoldNum holdNum;

  /**
   * 行业权重
   */
  private HoldWeight industryHoldWeight;

  /**
   * 风格权重
   */
  private HoldWeight styleHoldWeight;

  /**
   * 行业偏离
   */
  private Deviate industyrDeviate;

  /**
   * 风格偏离
   */
  private Deviate styleDeviate;

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Open getOpen() {
    return open;
  }

  public void setOpen(Open open) {
    this.open = open;
  }

  public List<PositionsPercent> getTop10() {
    return top10;
  }

  public void setTop10(List<PositionsPercent> top10) {
    this.top10 = top10;
  }

  public List<PositionsPercent> getOtherStocks() {
    return otherStocks;
  }

  public void setOtherStocks(List<PositionsPercent> otherStocks) {
    this.otherStocks = otherStocks;
  }

  public HoldNum getHoldNum() {
    return holdNum;
  }

  public void setHoldNum(HoldNum holdNum) {
    this.holdNum = holdNum;
  }

  public HoldWeight getIndustryHoldWeight() {
    return industryHoldWeight;
  }

  public void setIndustryHoldWeight(HoldWeight industryHoldWeight) {
    this.industryHoldWeight = industryHoldWeight;
  }

  public HoldWeight getStyleHoldWeight() {
    return styleHoldWeight;
  }

  public void setStyleHoldWeight(HoldWeight styleHoldWeight) {
    this.styleHoldWeight = styleHoldWeight;
  }

  public Deviate getIndustyrDeviate() {
    return industyrDeviate;
  }

  public void setIndustyrDeviate(Deviate industyrDeviate) {
    this.industyrDeviate = industyrDeviate;
  }

  public Deviate getStyleDeviate() {
    return styleDeviate;
  }

  public void setStyleDeviate(Deviate styleDeviate) {
    this.styleDeviate = styleDeviate;
  }
}

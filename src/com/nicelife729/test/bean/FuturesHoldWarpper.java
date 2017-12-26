package com.nicelife729.test.bean;

public class FuturesHoldWarpper {

  /**
   * 仓位
   */
  private Position position;
  /**
   * 多空敞口
   */
  private Open longShortOpen;

  /**
   * 产品敞口
   */
  private Open productionOpen;

  /**
   * 杠杆
   */
  private Lever lever;

  /**
   * 品种权重
   */
  private HoldWeight varietyWeight;

  /**
   * 板块权重
   */
  private HoldWeight plateWeight;

  /**
   * 品种净多头市值累计值
   */
  private MarketCapitalization accVarietyMarketValue;

  /**
   * 板块净多头市值累计值
   */
  private MarketCapitalization accPlateMarketValue;

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Open getLongShortOpen() {
    return longShortOpen;
  }

  public void setLongShortOpen(Open longShortOpen) {
    this.longShortOpen = longShortOpen;
  }

  public Open getProductionOpen() {
    return productionOpen;
  }

  public void setProductionOpen(Open productionOpen) {
    this.productionOpen = productionOpen;
  }

  public Lever getLever() {
    return lever;
  }

  public void setLever(Lever lever) {
    this.lever = lever;
  }

  public HoldWeight getVarietyWeight() {
    return varietyWeight;
  }

  public void setVarietyWeight(HoldWeight varietyWeight) {
    this.varietyWeight = varietyWeight;
  }

  public HoldWeight getPlateWeight() {
    return plateWeight;
  }

  public void setPlateWeight(HoldWeight plateWeight) {
    this.plateWeight = plateWeight;
  }

  public MarketCapitalization getAccVarietyMarketValue() {
    return accVarietyMarketValue;
  }

  public void setAccVarietyMarketValue(
      MarketCapitalization accVarietyMarketValue) {
    this.accVarietyMarketValue = accVarietyMarketValue;
  }

  public MarketCapitalization getAccPlateMarketValue() {
    return accPlateMarketValue;
  }

  public void setAccPlateMarketValue(MarketCapitalization accPlateMarketValue) {
    this.accPlateMarketValue = accPlateMarketValue;
  }
}

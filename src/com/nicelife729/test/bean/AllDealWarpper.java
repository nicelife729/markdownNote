package com.nicelife729.test.bean;

public class AllDealWarpper {

  /**
   * 交易活跃性
   */
  private TradeActivenesWarpper tradeActivenes;

  /**
   * 股票成交
   */
  private StockDealWarpper stock;

  /**
   * 期货成交
   */
  private FuturesDealWarpper futures;

  /**
   * 期权成交
   */
  private OptionsDealWarpper options;

  public TradeActivenesWarpper getTradeActivenes() {
    return tradeActivenes;
  }

  public void setTradeActivenes(TradeActivenesWarpper tradeActivenes) {
    this.tradeActivenes = tradeActivenes;
  }

  public StockDealWarpper getStock() {
    return stock;
  }

  public void setStock(StockDealWarpper stock) {
    this.stock = stock;
  }

  public FuturesDealWarpper getFutures() {
    return futures;
  }

  public void setFutures(FuturesDealWarpper futures) {
    this.futures = futures;
  }

  public OptionsDealWarpper getOptions() {
    return options;
  }

  public void setOptions(OptionsDealWarpper options) {
    this.options = options;
  }
}

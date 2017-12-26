package com.nicelife729.test.bean;

public class AllHoldWarpper {

  /**
   * 股票持仓
   */
  private StockHoldWarpper stock;

  /**
   * 期货持仓
   */
  private FuturesHoldWarpper futures;

  /**
   * 期权持仓
   */
  private OptionsHoldWarpper options;

  public StockHoldWarpper getStock() {
    return stock;
  }

  public void setStock(StockHoldWarpper stock) {
    this.stock = stock;
  }

  public FuturesHoldWarpper getFutures() {
    return futures;
  }

  public void setFutures(FuturesHoldWarpper futures) {
    this.futures = futures;
  }

  public OptionsHoldWarpper getOptions() {
    return options;
  }

  public void setOptions(OptionsHoldWarpper options) {
    this.options = options;
  }
}

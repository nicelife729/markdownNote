package com.nicelife729.test.bean;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class PositionsPercent {
  /**
   * 股票代码
   */
  private String StockCode;
  /**
   * 股票列表中本只所占百分比
   */
  private Double percent;

  public String getStockCode() {
    return StockCode;
  }

  public void setStockCode(String stockCode) {
    StockCode = stockCode;
  }

  public Double getPercent() {
    return percent;
  }

  public void setPercent(Double percent) {
    this.percent = percent;
  }
}

package com.nicelife729.test;

import com.nicelife729.test.bean.HeavyPositions;
import com.nicelife729.test.bean.HoldNum;
import com.nicelife729.test.bean.Open;
import com.nicelife729.test.bean.Position;

import java.util.List;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class RiskOfStockController {
  /**
   * 获取产品的股票持仓仓位序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  private Position getPositionOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获取产品的股票持仓敞口序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  private Open getOpenOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获取产品查询时间段内的前10只重仓股
   * @param institutionGuid
   * @param fundCode
   * @param beginDate
   * @param endDate
   * @return
   */
  private List<HeavyPositions> getHeavyPositionOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获得产品查询时段内的持股数序列
   * @param institutionGuid
   * @param fundCode
   * @param beginDate
   * @param endDate
   * @return
   */
  private HoldNum getHoldNumOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }
}

package com.nicelife729.test;

import com.nicelife729.test.bean.HoldWeight;
import com.nicelife729.test.bean.Lever;
import com.nicelife729.test.bean.MarketCapitalization;
import com.nicelife729.test.bean.Open;
import com.nicelife729.test.bean.Position;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class FuturesHoldController {
  /**
   * 获取产品的持仓期货的仓位序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  Position getPositionOfFutures(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获取产品的期货持仓的多空敞口序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  Open getLongShortOpenOfFutures(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获取产品的期货持仓的产品敞口序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  Open getProductionOpenOfFutures(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获取产品的期货的杠杆
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  Lever getLeverOfFutures(String institutionGuid, String fundCode, String beginDate,
      String endDate) {
    return null;
  }
  /**
   * 获取产品的期货持仓的品种权重序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  HoldWeight getVarietyWeightOfFutures(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }
  
  /**
   * 获取产品的期货持仓的板块权重序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  HoldWeight getPlateWeightOfFutures(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获得产品的品种净多头市值累计值
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  MarketCapitalization getAccumulatedVarietyMarketValueOfFutures(String institutionGuid,
      String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获得产品的板块种净多头市值累计值
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
   MarketCapitalization getAccumulatedPlateMarketValueOfFutures(String institutionGuid,
      String fundCode, String beginDate, String endDate) {
    return null;
  }
}

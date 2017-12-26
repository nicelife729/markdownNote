package com.nicelife729.test;

import com.nicelife729.test.bean.Deviate;
import com.nicelife729.test.bean.PositionsPercent;
import com.nicelife729.test.bean.HoldNum;
import com.nicelife729.test.bean.HoldWeight;
import com.nicelife729.test.bean.Open;
import com.nicelife729.test.bean.Position;

import java.util.List;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class StockHoldController {
  /**
   * 获取产品的股票持仓仓位序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  Position getPositionOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
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
  Open getOpenOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获取产品查询时间段内的前10只重仓股
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  List<PositionsPercent> getHeavyPositionOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }
  
  /**
   * 获取产品查询时间段内的除了前10只重仓股之外其它股票的占比
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  List<PositionsPercent> getOtherPositionOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获得产品查询时段内的持股数序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  HoldNum getHoldNumOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获得产品的行业权重
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  HoldWeight getIndustryHoldWeightOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获得产品的风格权重
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  HoldWeight getStyleHoldWeightOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获得产品的行业偏离(累计值)
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  Deviate getIndustryDeviateOfStock(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获得产品的风格偏离(累计值)
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  Deviate getStyleDeviateOfStock(String institutionGuid, String fundCode, String beginDate,
      String endDate) {
    return null;
  }
}

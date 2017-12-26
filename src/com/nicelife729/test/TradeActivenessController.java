package com.nicelife729.test;

import com.nicelife729.test.bean.ChangeRate;
import com.nicelife729.test.bean.DealNum;
import com.nicelife729.test.bean.DealRate;
import com.nicelife729.test.bean.OrderNum;

public class TradeActivenessController {

  /**
   * 获取产品一段时间的委托次数序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  OrderNum getOrderNum(String institutionGuid, String fundCode, String beginDate,
      String endDate) {
    return null;
  }

  /**
   * 获取产品一段时间的成交次数序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  DealNum getDealNum(String institutionGuid, String fundCode, String beginDate,
      String endDate) {
    return null;
  }

  /**
   * 获取产品一段时间的报单成功率
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  DealRate getDealRate(String institutionGuid, String fundCode, String beginDate,
      String endDate) {
    return null;
  }

  /**
   * 获得产品一段时间的换手率
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  ChangeRate getChangeRate(String institutionGuid, String fundCode, String beginDate,
      String endDate) {
    return null;
  }
}

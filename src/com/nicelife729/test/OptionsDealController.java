package com.nicelife729.test;

import com.nicelife729.test.bean.DealHands;
import com.nicelife729.test.bean.DealProportion;

public class OptionsDealController {

  /**
   * 获取产品一段时间期权的成交结构
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  DealProportion getDealProportionOfOptions(String institutionGuid, String fundCode,
      String beginDate,
      String endDate) {

    return null;
  }

  /**
   * 获取产品一段时间期权的而成交量
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  DealHands getDealHandsOfOptions(String institutionGuid, String fundCode, String beginDate,
      String endDate) {
    return null;
  }
}

package com.nicelife729.test;

import com.nicelife729.test.bean.Delta;
import com.nicelife729.test.bean.HoldHands;
import com.nicelife729.test.bean.HoldWeight;
import com.nicelife729.test.bean.Position;

/**
 * Created by yoyoadm on 17-12-21.
 */
public class OptionsHoldController {

  /**
   * 获取产品的期权的持仓手数(持仓量)
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  HoldHands getHoldHandsOfOptions(String institutionGuid, String fundCode, String beginDate,
      String endDate) {
    return null;
  }

  /**
   * 获取产品的持仓期权的仓位序列
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  Position getPositionOfOptions(String institutionGuid, String fundCode, String beginDate, String endDate) {
    return null;
  }

  /**
   * 获取产品的持仓期权的合约结构
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  HoldWeight getHoldWeightOfOptions(String institutionGuid, String fundCode,
      String beginDate, String endDate) {
    return null;
  }


  /**
   * 获取产品的持仓期权的Delta
   * @param institutionGuid 机构uuid
   * @param fundCode 产品代码
   * @param beginDate 起点时间
   * @param endDate 结束时间
   * @return
   */
  Delta getDeltaOfOptions(String institutionGuid, String fundCode,
      String beginDate, String endDate) {
    return null;
  }
}

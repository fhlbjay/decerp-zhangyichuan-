package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Vipcard;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface VipcardMapper {
    int insert(Vipcard record);

    List<Vipcard> selectAll();

    void updateIntegral(Vipcard vipcard);

    void updateBlance(@Param("id") Long vipcardId, @Param("balance") BigDecimal currentcount);



    /**
     * 更新会员卡信息
     */
    void updateVipcard(Vipcard vipcard);

    BigDecimal selectBalanceById(Long vipcardId);

    void updateDiscount(@Param("vipcardId") Long vipcardId, @Param("discount") BigDecimal bigDecimal);

    /**
     * 得到完整的vipcard
     *
     * @param vipcardId
     * @return
     */
    Vipcard selectById(Long vipcardId);

    Vipcard selectVipcardIdBySn(String vipcardSn);
}
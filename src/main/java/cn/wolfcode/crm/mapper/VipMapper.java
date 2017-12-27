package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Vip;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.query.VipChartQueryObject;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface VipMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Vip record);

    Vip selectByPrimaryKey(Long id);

    List<Vip> selectAll();

    int updateByPrimaryKey(Vip record);

    Integer queryCount(QueryObject qo);

    List<Vip> queryList(QueryObject qo);

    void changeState(Long id);

    Integer selectBirthdayOfToday(String date);

    Integer selectBirthdayOfMonth(@Param("begindate") String begindate, @Param("enddate") String enddate);

    Integer selectVipNumber();

    BigDecimal selectConsumer();

    BigDecimal selectConsumptionAmount();

    Integer selectOrderBilNum();

    List<String> selectConsumptionAmountTop();

    List<Map<String, Object>> vipChart(VipChartQueryObject qo);

    void updateVipGrade(@Param("vipId") Long vipId, @Param("grade") String grade);

    Integer selectTotalNumber(IndexQueryObject qo);

    List<Map<String, Object>> dateChart(IndexQueryObject qo);

    Vip selectVipByVipcardId(@Param("vcd") Long vcd, @Param("password") String password);

    /**
     * 通过id 和密码 判断有没有这个人
     *
     * @param vipcardSn
     * @param password
     * @return
     */
    Vip selectVipByIdPassword(@Param("vipcardSn") String vipcardSn, @Param("password") String password);
}
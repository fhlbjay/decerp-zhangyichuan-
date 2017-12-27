package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Vipcard {
    private Long id;
    //累计积分
    private Long integral;
    //当前积分
    private Long currentintegral;
    //累计消费金额
    private BigDecimal consumptionamount;
    //储值余额
    private BigDecimal balance;
    //享有折扣
    private BigDecimal discount;
    //会员卡编号
    private String sn;


}
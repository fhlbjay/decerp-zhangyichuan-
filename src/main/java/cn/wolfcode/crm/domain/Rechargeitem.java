package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class Rechargeitem {
    private Long id;
    //支付方式
    private String payment;
    //充值前余额
    private BigDecimal oldmount;
    //充值金额
    private BigDecimal rechargemount;
    //充值备注
    private String remarks;
    //会员id
    private Long vipId;
    //会员卡Id
    private  Long vipcardId;
    //充值时间
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rechargetime;
    //当前余额
    private BigDecimal currentmount;
}
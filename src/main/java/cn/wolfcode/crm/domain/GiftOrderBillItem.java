package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class GiftOrderBillItem {
    private Integer id;
    //VIP对象
    private Vip vip;
    //礼品对象
    private Gift gift;
    //兑换数量
    private Integer number;
    //剩余积分
    private Long remainIntegral;
    //操作人员
    private String operation;
    //操作时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;


}
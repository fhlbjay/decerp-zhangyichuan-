package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class Orderbill {
    public static final int STATUS_NOMAL = 0;
    public static final int STATUS_END = 1;


    private Long id;

    private String sn;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT + 8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;

    private int status = STATUS_NOMAL;

    private BigDecimal totalAmount;

    private BigDecimal totalNumber;


    private Integer payWay;  //支付方式  1 支付宝2 微信3等等


    private Employee inputUser;  //录入人

    private Vip vip;  //会员账号

    private List<OrderbillItem> items = new ArrayList<>();


}

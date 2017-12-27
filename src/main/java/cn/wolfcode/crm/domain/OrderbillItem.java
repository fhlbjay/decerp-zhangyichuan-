package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OrderbillItem {

    private Long id;

    private BigDecimal costPrice;

    private BigDecimal number;

    private BigDecimal amount;
    private BigDecimal saleAmount;
    private String remark;

    private Product product;


    private Long billId;  //所属的订单编号
}

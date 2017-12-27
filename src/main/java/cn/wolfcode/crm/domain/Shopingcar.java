package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class Shopingcar {
    private Long id;

    private Long productid;

    private String name;

    private BigDecimal costPrice;

    private BigDecimal number;

    private BigDecimal amount;

    private String remark;

    private Long status;

    private BigDecimal totalamount;

    private Long vipId;

}
package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

//明细回显专用类
@Getter
@Setter
public class Bill {

    private Long id;
    private String sn;
    private String name;
    private BigDecimal costPrice;
    private BigDecimal number;
    private BigDecimal amount;
    private String remark;
}

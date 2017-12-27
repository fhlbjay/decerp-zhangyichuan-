package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegralDetail {
    private Long id;

    private Vip vip;

    private String operationType;

    private Integer amountChange;

    private String remark;
}
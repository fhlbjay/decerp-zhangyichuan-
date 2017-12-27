package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Permission {
    private Long id;

    private String name;

    private String expression;


}
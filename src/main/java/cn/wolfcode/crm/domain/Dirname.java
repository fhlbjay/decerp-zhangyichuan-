package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class Dirname {
    private Long id;

    private String name;

    private LinkageMenu parent;//一级目录


}
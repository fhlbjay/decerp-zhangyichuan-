package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter@Getter
public class Role {
    private Long id;

    private String name;

    private String sn;

    List<Permission> permissions = new ArrayList<>();

}
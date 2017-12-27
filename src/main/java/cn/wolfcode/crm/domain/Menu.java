package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class Menu {
    private Long id;

    private String text;


    private String url;

    private List<Menu> children = new ArrayList<>();

    private Menu parent ;

    private Permission permission;
}
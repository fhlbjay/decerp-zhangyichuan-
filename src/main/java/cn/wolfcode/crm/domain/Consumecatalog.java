package cn.wolfcode.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//支出分类
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class Consumecatalog {//支出分类
    private Long id;
    //大分类名称
    private String name;
    private Long parentId;
    private List<Consumecatalog> children;
}
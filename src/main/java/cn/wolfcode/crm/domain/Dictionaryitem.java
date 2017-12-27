package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Dictionaryitem {
    private Long id;

    private String name;

    private String intro;

    private Dictionary dictionary;

}
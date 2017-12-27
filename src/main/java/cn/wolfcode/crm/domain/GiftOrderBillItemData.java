package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
//SpringMVC list封装容器
public class GiftOrderBillItemData {
    List<GiftOrderBillItem> list=new ArrayList<>();
}

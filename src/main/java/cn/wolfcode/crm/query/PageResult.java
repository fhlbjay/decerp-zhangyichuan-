package cn.wolfcode.crm.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter@Setter@AllArgsConstructor
public class PageResult {

    //查询记录数
    private Integer total;
    //插叙结果
    private List<?> rows;

}

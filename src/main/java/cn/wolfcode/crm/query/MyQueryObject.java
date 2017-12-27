package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyQueryObject {
    //当前页,默认给1(在刚进页面的时候有用)
    private Integer currentPage = 1;
    //分页大小,默认给5
    private Integer pageSize = 3;

    //分页时,获取分页开始索引
    public Integer getBeginIndex() {
        return (currentPage - 1) * pageSize;
    }
}

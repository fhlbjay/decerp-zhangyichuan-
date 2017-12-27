package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class QueryObject {
    //当前页
    private Integer page=1;
    //每页显示记录数
    private Integer rows=10;
    //高級查詢關鍵字
    private String keyword;
    //父级菜单id
    private Long parentId;
    //vip的id
    private Long vipId;
    //获取分页开始索引
    public int  getBeginIndex(){
        return (page-1)*rows;
    }
}

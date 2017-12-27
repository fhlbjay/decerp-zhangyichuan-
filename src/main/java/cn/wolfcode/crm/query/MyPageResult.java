package cn.wolfcode.crm.query;


import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class MyPageResult {
    //页面传过来的
    private Integer currentPage;
    private Integer pageSize;
    //计算所得
    private Integer prevPage;
    private Integer nextPage;
    private Integer totalPage;
    //sql查询出来的
    private Integer totalCount;
    private List<?> list;

    //通过构造器返回页面查询修过
    public MyPageResult(Integer currentPage, Integer pageSize, Integer totalCount, List<?> result) {
        //页面qo获得
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        //通过sql查询所得,service层传过来的
        this.totalCount = totalCount;
        this.list = result;

        //计算所得
        //考虑是否整除
        totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        //考虑是否有上一页
        prevPage = currentPage - 1 > 0 ? currentPage - 1 : 1;
        //考虑是否有下一页
        nextPage = currentPage + 1 > totalPage ? totalPage : currentPage + 1;
        //如果totalPage为0,显然不合理,如果为0则设置为1;
        if (totalPage == 0) {
            totalPage = 1;
        }
    }

    //在count为0的时候,直接不计算,返回一个空集
    public MyPageResult(Integer pageSize) {
        this(1, pageSize, 0, Collections.EMPTY_LIST);
    }

    /*public Object getJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("total", this.totalCount);
        map.put("rows", JSON.toJSON(this.getList()));
         return map;
    }*/
}

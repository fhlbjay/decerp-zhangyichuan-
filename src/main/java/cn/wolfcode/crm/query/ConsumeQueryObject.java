package cn.wolfcode.crm.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter@Setter
public class ConsumeQueryObject extends QueryObject{
    //当前页
    private Integer page=1;
    //每页显示记录数
    private Integer rows=10;
    //高級查詢關鍵字
    private String keyword;
    //查询起始日期
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    //查询截止日期
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    public int  getBeginIndex(){
        return (page-1)*rows;
    }
}

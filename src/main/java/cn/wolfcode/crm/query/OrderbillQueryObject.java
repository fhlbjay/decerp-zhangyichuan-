package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class OrderbillQueryObject extends MyQueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Long vipId = -1L;
    private Integer status = -1;


    //时间查询时候我们需要查询截止日期的23:59:59   所以我们要在这里将getEndDate重写
    public Date getEndDate() {

        //判断是否为空  如果不为空 就设置时间   如果为空 就不设置
        if (endDate == null) {
            return null;

        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            return c.getTime();
        }

    }

}

package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class PurchasingOrderBillQueryObject extends QueryObject {
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    //重写get方法,返回当前日期的最后一秒
    public Date getEndDate() {
        if (endDate != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            //设置时间
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            return c.getTime();
        }
        return null;
    }

    private String keyword;
}

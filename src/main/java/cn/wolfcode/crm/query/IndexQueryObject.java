package cn.wolfcode.crm.query;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class IndexQueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public Integer getDays() {
        return days;
    }

    private Integer days = 7;

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getBeginDate(){
        Date currentTime = new Date();
        //设置开始时间
        int date1 = currentTime.getDate();
        currentTime.setDate(date1-(days/2));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);
        return  date;
    }
    public String getEndDate(){
        Date currentTime = new Date();
        //设置结束时间
        int date1 = currentTime.getDate();
        currentTime.setDate(date1+(days/2));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);
        return  date;
    }
    private String groupBy="v.name";
    public String getGroupBy (){
        return this.groupBy;
    }
    public Map<String, String> getGroupByMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("v.name", "会员名称");
        return map;
    }

}

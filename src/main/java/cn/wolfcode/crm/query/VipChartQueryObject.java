package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
@Getter@Setter
public class VipChartQueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String groupBy="v.name";
    public Map<String, String> getGroupByMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("v.name", "会员名称");
        map.put("rg.payment", "支付方式");
        map.put("rg.rechargetime", "充值时间");
        return map;
    }
}

package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter@Setter
public class ProductChartQueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String groupBy="p.name";
    public Map<String, String> getGroupByMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("p.name", "产品名称");
        map.put("e.username", "销售人员");
        map.put("DATE_FORMAT(ob.vdate,'%Y-%m')", "销售日期(月)");
        map.put("DATE_FORMAT(ob.vdate,'%Y-%m-%d')", "销售日期(日)");
        return map;
    }
}

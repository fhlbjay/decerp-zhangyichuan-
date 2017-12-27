package cn.wolfcode.crm.domain;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Gift {
    private Long id;
    //礼品编号
    private String sn;
    //礼品名
    private String name;
    //礼品兑换积分
    private Integer integral;
    //总数量
    private Integer count;
    //剩余数量
    private Integer surplus;
    //单位
    private String unit;
    //大图图片路径
    private String image;
    //小图图片路径
    //如何通过imagePath拼写成smallImagePath
    public String getSmallImagePath() {
        if(StringUtils.isEmpty(image)) {
            return "";
        }else {
            int index=image.lastIndexOf(".");
            return image.substring(0,index)+"_small"+image.substring(index);
        }
    }
    //单位列表
    static Map<String,String> units=new LinkedHashMap<>() ;
    //单位列表初始化
    static{
        units.put("个","个");
        units.put("台","台");
        units.put("块","块");
        units.put("条","条");
        units.put("件","件");
        units.put("套","套");
        units.put("箱","箱");
        units.put("m","m");
        units.put("M²","M²");
    }
    //获取单位列表方法
    public  Map<String,String>  getUnits(){
        return units;
    }

    public String getJsonString() {
        Map<String, Object> jsonMap=new HashMap<>();
        jsonMap.put("id", id);
        jsonMap.put("name", name);
        jsonMap.put("integral", integral);
        jsonMap.put("surplus", surplus);
        return JSON.toJSONString(jsonMap);
    }

}
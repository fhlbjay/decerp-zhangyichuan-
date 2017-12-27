package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Supplier {
    private Long id;

    private String name;

    //应付欠款
    private BigDecimal arrearage;

    //联系人
    private String linkman;

    private String tel;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;

    private Employee employee;

    private String qqNumber;

    private String address;

    //备注
    private String remark;

}
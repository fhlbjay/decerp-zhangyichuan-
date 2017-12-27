package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class Depot {
    private Long id;

    private String name;

    private String sn;

    private String linkman;

    private String tel;

    private Boolean status;

    private String address;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;

}
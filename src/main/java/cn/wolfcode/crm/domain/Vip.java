package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
public class Vip {
    private Long id;

    //会员名字
    private String name;
    //电话
    private String tel;
    //密码
    private String password;
    //会员会员卡状态 正常/丢失
    private Boolean state;
    //会员等级
    private String vipgrade;

    private String qq;

    private String weixin;

    private String email;
    //会员卡对象
    private Vipcard vipcard;

    //会员生日
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

}
package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Employee {

    //limin
    private Long id;

    private String username;

    private String realname;

    private String password;

    private String tel;

    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputtime;

    private boolean state;

    private Boolean admin;

    private  Department department;

    private List<Role> roles = new ArrayList<>();
}
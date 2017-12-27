package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dailyconsume {
    private Long id;
    //支出分类
    private Consumecatalog consumecatalog;
    //金额
    private BigDecimal amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    private Date date;
    private Employee consumer;
    private String memo;
}
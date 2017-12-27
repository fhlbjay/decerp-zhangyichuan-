package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductStock {
    private Long id;

    private BigDecimal price;

    private BigDecimal storeNumber;

    private BigDecimal amount;

    private Product product;

    private Depot depot;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;

}
package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class Inventory {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;

    private Product product;

    private Depot depot;

    private ProductStock productStock;

}
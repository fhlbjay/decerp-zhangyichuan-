package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PurchasingOrderBill {
    public static final int STATUS_NOMAL = 0;
    public static final int STATUS_AUDIT = 1;

    private Long id;

    private String sn;

    //业务时间
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;

    //审核状态
    private Integer status = STATUS_NOMAL;

    private BigDecimal totalAmount;

    private BigDecimal totalNumber;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;

    private Employee inputUser;

    private Employee auditor;

    private Supplier supplier;

    private Depot depot;

    //退回状态
    private Integer returnState = STATUS_NOMAL;

    //明细
    List<PurchasingOrderBillItem> items = new ArrayList<>();

}
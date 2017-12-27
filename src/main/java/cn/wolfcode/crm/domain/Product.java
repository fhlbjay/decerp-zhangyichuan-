package cn.wolfcode.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
@Getter@Setter
public class Product {
    private Long id;

    private String name;

    private String sn;//商品编号

    private BigDecimal salePrice;//商品售价

    private Unit unit;//商品单位

    private LinkageMenu root;//一级菜单
	private Dirname parent;//二级菜单
	private boolean state;//状态：上架，下架
    private BigDecimal stockQuantity;//库存数量
	@JsonFormat(pattern = "yyyy-MM-dd" ,timezone ="GTM+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inputTime;//入库时间
	private BigDecimal costPrice;

	private ProductStock productStock;
}
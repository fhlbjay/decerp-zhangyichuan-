package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Product;
import cn.wolfcode.crm.query.MyProductQueryObject;
import cn.wolfcode.crm.query.PageResult;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    void deleteByPrimaryKey(Long id);

    void insert(Product record);

    Product selectByPrimaryKey(Long id);

    List<Product> selectAll();

   void updateByPrimaryKey(Product record);

    PageResult query(MyProductQueryObject qo);

	void changeState(Long id);

	String selectProductAmount();

	int ProductAmount();

	BigDecimal sumAmount();
}

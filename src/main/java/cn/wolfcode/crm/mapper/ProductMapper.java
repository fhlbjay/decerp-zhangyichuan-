package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Product;
import cn.wolfcode.crm.query.MyProductQueryObject;

import java.util.List;

public interface ProductMapper {
    void deleteByPrimaryKey(Long id);

    void insert(Product record);

    Product selectByPrimaryKey(Long id);

    List<Product> selectAll();

    void updateByPrimaryKey(Product record);
	int queryCount(MyProductQueryObject qo);

	List<Product> queryList(MyProductQueryObject qo);

	void changeState(Long id);

	String selectProductAmount();

	int ProductAmount();

}
package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Shopingcar;
import cn.wolfcode.crm.query.QueryObject;

import java.math.BigDecimal;
import java.util.List;

public interface ShopingcarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Shopingcar record);

    Shopingcar selectByPrimaryKey(Long id);

    List<Shopingcar> selectAll();

    int updateByPrimaryKey(Shopingcar record);

    Shopingcar selectByproductKey(Long id);

    Integer queryCount(QueryObject qo);

    List<Shopingcar> queryList(QueryObject qo);

    BigDecimal selectTotalAmount();

    BigDecimal selectTotalNumber();

    void deleteAll();

    void deleteByProductId(Long productId);

    BigDecimal selectSunVipId();

    BigDecimal selectCountVipId();
}
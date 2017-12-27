package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.ProductStock;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {

    int insert(ProductStock record);

    int updateByPrimaryKey(ProductStock record);

    Integer queryCount(@Param("queryObject") QueryObject queryObject,@Param("id")Long id);

    List<ProductStock> queryList(@Param("queryObject")QueryObject queryObject,@Param("id") Long id);

    ProductStock selectByDepotIdAndProductId(@Param("depotId")Long id, @Param("productId")Long id2);

    Integer selectNumber(@Param("pId") Long pId,@Param("dId") Long dId);

    void changeNumber(@Param("psId") Long psId,@Param("storeNumber") Integer number);

}
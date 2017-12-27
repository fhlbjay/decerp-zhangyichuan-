package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Orderbill;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface ITempbillService {
    void deleteByPrimaryKey(Long id);

    void insert(Orderbill record);

    Orderbill selectByPrimaryKey(Long id);

    List<Orderbill> selectAll();

    void updateByPrimaryKey(Orderbill record);

    MyPageResult query(MyQueryObject qo);

    void audit(Long id);


    PageResult cashGetTemp(QueryObject qo);

    void shopadd(Long id);

    PageResult shopAll(QueryObject qo);

    void clearall();

    /**
     * 挂单
     *
     * @param vipid
     */
    void saveTempBill(Long vipid);

    void getTempbill(Long id);

    /**
     * 删除一个挂单
     *
     * @param id
     */
    void deleteOneTempbill(Long id);

    Map<String, Object> amountForm(Long id);

    /**
     * 删除其中一个
     *
     * @param productId
     */
    void removeOne(Long productId);

    Map<String,Object> amountFormGetemp();
}

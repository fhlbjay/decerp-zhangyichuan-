package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Orderbill;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderbillService {
    void deleteByPrimaryKey(Long id);

    void insert(Orderbill record);

    Orderbill selectByPrimaryKey(Long id);

    List<Orderbill> selectAll();

    void updateByPrimaryKey(Orderbill record);

    MyPageResult query(MyQueryObject qo);

    void audit(Long id);

    BigDecimal selectTotalConsum(IndexQueryObject qo);

    Integer selectTotalNum(IndexQueryObject qo);


    /**
     * 通过vipid  存储数据
     *
     * @param vipid
     */
    void saveBill(Long vipid);

    Boolean judgeShopcar();

    /**
     * 检验密码
     * @param vipcardSn
     * @param password
     */
    void checkVip(String vipcardSn, String password);
/*
    *//**
     * 挂单方法
     *
     * @param record
     *//*
    void cadhTempbill(Orderbill record);

    PageResult cadhGetTempbill(QueryObject qo);*/
}

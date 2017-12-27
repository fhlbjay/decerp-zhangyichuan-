package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Bill;

import java.util.List;

public interface BillMapper {

    Integer queryCount(Long id);

    List<Bill> queryList(Long id);
}
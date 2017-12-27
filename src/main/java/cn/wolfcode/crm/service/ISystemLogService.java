package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.SystemLog;

import java.util.List;

public interface ISystemLogService {
    int deleteByPrimaryKey(Long id);

    int insert(SystemLog record);

    SystemLog selectByPrimaryKey(Long id);

    List<SystemLog> selectAll();

    int updateByPrimaryKey(SystemLog record);


}

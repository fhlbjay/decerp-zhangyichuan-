package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;

import java.util.List;

public interface IPermissionService {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    PageResult query(QueryObject qo);

    List<Permission> selectPermissionByRoleId(Long id);

    void loadPermission();

    List<String> selectPermissionByEmployeeId(Long id);

}

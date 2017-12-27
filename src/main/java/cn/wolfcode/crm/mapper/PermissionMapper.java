package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.query.QueryObject;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    int queryCount(QueryObject qo);

    List<?> queryList(QueryObject qo);

    List<Permission> selectPermissionByRoleId(Long id);

    List<String> selectAllExpression();

    List<String> selectPermissionByEmployeeId(Long id);

}
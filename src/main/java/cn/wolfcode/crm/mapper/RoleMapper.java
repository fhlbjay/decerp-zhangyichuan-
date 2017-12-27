package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    int queryCount(QueryObject qo);

    List<?> queryList(QueryObject qo);

    void inserRelations(@Param("roleId") Long id,@Param("PermissionId") Long id1);

    void batchInsertRelations(@Param("roleId") Long id, @Param("permissions") List<Permission> permissions);

    void deleteRelations(Long id);

    List<String> selectRoleByEmployeeId(Long id);

}
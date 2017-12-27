package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    Integer queryCount(QueryObject qo);

    List <Employee> queryList(QueryObject qo);

    void changeState(Long id);

    void batchInsertRelations(@Param("employeeId") Long id, @Param("roles") List<Role> roles);

    void deleteRelations(Long id);

    List<Long> getRoleIdByemployeeId(Long id);

    Employee getEmployeeByUsername(String principal);

    String selectUserName(String username);

}
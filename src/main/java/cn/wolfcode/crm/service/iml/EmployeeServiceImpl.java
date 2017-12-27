package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.EmployeeMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    EmployeeMapper mapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        //删除关系
        mapper.deleteRelations(id);
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Employee record) {
        //对密码进行加密
        record.setPassword(new Md5Hash(record.getPassword(),record.getUsername(),2).toString());
	    record.setState(true);
	    //检查用户名是否存在
        String username = record.getUsername();
        String usernameInSql = mapper.selectUserName(username);
        if (usernameInSql != null) {
            throw new RuntimeException("用户名已存在,请重新输入");
        }
        int insert = mapper.insert(record);
        //int i = 1/0; 测试事务
        //维护关系
        List<Role> roles = record.getRoles();
        if (roles.size()>0){
            mapper.batchInsertRelations(record.getId(),roles);
        }
        return insert;
    }

    @Override
    public Employee selectByPrimaryKey(Long id) {
        Employee employee = mapper.selectByPrimaryKey(id);
        return employee;
    }

    @Override
    public List<Employee> selectAll() {
        List<Employee> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Employee record) {
        //先删除关系,再重新维护关系
        mapper.deleteRelations(record.getId());
        //维护关系
        List<Role> roles = record.getRoles();
        if (roles.size()>0){
            mapper.batchInsertRelations(record.getId(),roles);
        }
        mapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public PageResult query(QueryObject qo) {
        Integer count = mapper.queryCount(qo);
        if(count>0){
            return new PageResult(count,mapper.queryList(qo));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }

    @Override
    public void changeState(Long id) {
        mapper.changeState(id);
    }

    @Override
    public List<Long> getRoleIdByemployeeId(Long id) {

        return mapper.getRoleIdByemployeeId(id);
    }

    @Override
    public Employee getEmployeeByUsername(String principal) {
        return mapper.getEmployeeByUsername(principal);
    }
}

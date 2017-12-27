package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.RoleMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    RoleMapper mapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        //删除关系
        mapper.deleteRelations(id);
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Role record) {
        int insert = mapper.insert(record);
        //维护关系
        List<Permission> permissions = record.getPermissions();
        if (permissions.size()>0) {

            /*for (Permission permission : permissions) { //执行了多条insert sql
                mapper.inserRelations(record.getId(),permission.getId());
            }*/
            mapper.batchInsertRelations(record.getId(),permissions);
        }
        return 0;
    }

    @Override
    public Role selectByPrimaryKey(Long id) {
        Role role = mapper.selectByPrimaryKey(id);
        return role;
    }

    @Override
    public List<Role> selectAll() {
        List<Role> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        //删除关系
        mapper.deleteRelations(record.getId());
        //重新维护关系
        List<Permission> permissions = record.getPermissions();
        if(permissions.size()>0){

                mapper.batchInsertRelations(record.getId(),permissions);

        }
        mapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public PageResult query(QueryObject qo) {

        int count = mapper.queryCount(qo);
        if(count>0){
            return new PageResult(count,mapper.queryList(qo));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }

    @Override
    public List<String> selectRoleByEmployeeId(Long id) {
        return mapper.selectRoleByEmployeeId(id);
    }
}

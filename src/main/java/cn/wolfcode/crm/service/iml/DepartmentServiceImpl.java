package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.mapper.DepartmentMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    DepartmentMapper mapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Department record) {
        mapper.insert(record);
        return 0;
    }

    @Override
    public Department selectByPrimaryKey(Long id) {
        Department department = mapper.selectByPrimaryKey(id);
        return department;
    }

    @Override
    public List<Department> selectAll() {
        List<Department> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Department record) {
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
	public Department selectDept(String contents) {
		return mapper.selectDept(contents);
	}
}

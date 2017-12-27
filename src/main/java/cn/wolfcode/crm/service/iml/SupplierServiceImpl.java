package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Supplier;
import cn.wolfcode.crm.mapper.SupplierMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ISupplierService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    SupplierMapper mapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Supplier record) {
        record.setArrearage(new BigDecimal(0));
        record.setInputTime(new Date());
        record.setEmployee((Employee)SecurityUtils.getSubject().getPrincipal());
        mapper.insert(record);
        return 0;
    }

    @Override
    public Supplier selectByPrimaryKey(Long id) {
        Supplier supplier = mapper.selectByPrimaryKey(id);
        return supplier;
    }

    @Override
    public List<Supplier> selectAll() {
        List<Supplier> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Supplier record) {
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
}

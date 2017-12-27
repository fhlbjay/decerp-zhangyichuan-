package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Depot;
import cn.wolfcode.crm.mapper.DepotMapper;
import cn.wolfcode.crm.mapper.ProductStockMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DepotServiceImpl implements IDepotService {
    @Autowired
    DepotMapper mapper;
    @Autowired
    ProductStockMapper productStockMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Depot record) {
        record.setInputTime(new Date());
        mapper.insert(record);
        return 0;
    }

    @Override
    public Depot selectByPrimaryKey(Long id) {
        Depot depot = mapper.selectByPrimaryKey(id);
        return depot;
    }

    @Override
    public List<Depot> selectAll() {
        List<Depot> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Depot record) {
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
    public void changeStatus(Long id) {
        mapper.changeStatus(id);
    }

    @Override
    public PageResult getStock(QueryObject qo, Long id) {
        Integer count = productStockMapper.queryCount(qo,id);
        if(count>0){
            return new PageResult(count,productStockMapper.queryList(qo,id));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }
}

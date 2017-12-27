package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Consumecatalog;
import cn.wolfcode.crm.mapper.ConsumecatalogMapper;
import cn.wolfcode.crm.service.IConsumecatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Sieg on 2017/12/22.
 */
@Service
public class ConsumecatalogServiceImpl implements IConsumecatalogService {
    @Autowired
    private ConsumecatalogMapper mapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Consumecatalog record, Long parentId) {
        System.err.println("");
        record.setParentId(parentId);
        return mapper.insert(record);
    }

    @Override
    public Consumecatalog selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Consumecatalog> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Consumecatalog record) {
        Consumecatalog newOne = mapper.selectByPrimaryKey(record.getId());
        newOne.setName(record.getName());
        return mapper.updateByPrimaryKey(newOne);
    }

    @Override
    public List<Consumecatalog> selectForParent() {
        return mapper.selectForParent();
    }

    @Override
    public List<Consumecatalog> selectForChildren(Long id) {
        return mapper.selectForChildren(id);
    }

    @Override
    public BigDecimal countAmountByDay(){
        return mapper.countAmountByDay();
    }

    @Override
    public BigDecimal countAmountByMonth() {
        return mapper.countAmountByMonth();
    }

    @Override
    public BigDecimal countAmountByYear() {
        return mapper.countAmountByYear();
    }

    public List<Map<String,Object>> selectChartsByMonth(){
        return mapper.selectChartsByMonth();
    }

    public List<Map<String,Object>> selectChartsByName(){
        return mapper.selectChartsByName();
    }

}

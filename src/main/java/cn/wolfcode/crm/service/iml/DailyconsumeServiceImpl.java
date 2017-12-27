package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Consumecatalog;
import cn.wolfcode.crm.domain.Dailyconsume;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.mapper.ConsumecatalogMapper;
import cn.wolfcode.crm.mapper.DailyconsumeMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDailyconsumeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sieg on 2017/12/21.
 */
@Service
public class DailyconsumeServiceImpl implements IDailyconsumeService {
    @Autowired
    private DailyconsumeMapper mapper;
    @Autowired
    private ConsumecatalogMapper catalogmapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Dailyconsume record) {
        System.err.println("insert - record : " +  record);

        //获取支出分类的信息(name 和 id)
        Consumecatalog consumecatalog = catalogmapper.selectByPrimaryKey(record.getConsumecatalog().getId());
        record.setConsumecatalog(consumecatalog);

        //获得当前录入人员的信息
        Subject consumer = SecurityUtils.getSubject();
        //将当前录入人员的姓名放进支出类的Consumer当中
        record.setConsumer((Employee) consumer.getPrincipal());
        return mapper.insert(record);
    }

    @Override
    public Dailyconsume selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Dailyconsume> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Dailyconsume record) {
        System.err.println("updateByPrimaryKey - record : " +  record);
        System.err.println("consumer ");
        Employee consumer = mapper.selectConsumerByConsumeId(record.getId());
        System.err.println("consumer = "+consumer);

        record.setConsumer(consumer);
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public PageResult query(QueryObject qo) {

        int count = mapper.queryCount(qo);
        if(count>0){
            return new PageResult(count,mapper.queryList(qo));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }

}

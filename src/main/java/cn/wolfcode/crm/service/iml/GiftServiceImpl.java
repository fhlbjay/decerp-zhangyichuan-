package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Gift;
import cn.wolfcode.crm.mapper.GiftMapper;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;
import cn.wolfcode.crm.service.IGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftServiceImpl implements IGiftService {
    @Autowired
    GiftMapper mapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Gift record) {
        mapper.insert(record);
        return 0;
    }

    @Override
    public Gift selectByPrimaryKey(Long id) {
        Gift gift = mapper.selectByPrimaryKey(id);
        return gift;
    }

    @Override
    public List<Gift> selectAll() {
        List<Gift> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Gift record) {
        mapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public MyPageResult query(MyQueryObject qo) {
        Integer totalCount=mapper.queryCount(qo);
        if(totalCount==0) {
            return new MyPageResult(qo.getPageSize());
        }
        List<Gift> result= mapper.queryList(qo);
        return new MyPageResult(qo.getCurrentPage(), qo.getPageSize(), totalCount, result);
    }
}

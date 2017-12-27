package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.mapper.SystemLogMapper;
import cn.wolfcode.crm.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogServiceImpl implements ISystemLogService {
    @Autowired
    SystemLogMapper logMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return logMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(cn.wolfcode.crm.domain.SystemLog record) {
        return logMapper.insert(record);
    }

    @Override
    public cn.wolfcode.crm.domain.SystemLog selectByPrimaryKey(Long id) {
        return logMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<cn.wolfcode.crm.domain.SystemLog> selectAll() {
        return logMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(cn.wolfcode.crm.domain.SystemLog record) {
        return  logMapper.updateByPrimaryKey(record);
    }


}

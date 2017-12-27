package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.mapper.MenuMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    MenuMapper mapper ;
    @Override
    public int deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Menu record) {
        mapper.insert(record);
        return 0;
    }

    @Override
    public Menu selectByPrimaryKey(Long id) {
        Menu menu = mapper.selectByPrimaryKey(id);
        return menu;
    }

    @Override
    public List<Menu> selectAll() {
        List<Menu> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Menu record) {
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
    public List<Menu> getMenus(Long id) {
        return mapper.getRootMenu(id);
    }

    @Override
    public Long getParentIdById(Long parentId) {
        return mapper.getParentIdById(parentId);
    }
}

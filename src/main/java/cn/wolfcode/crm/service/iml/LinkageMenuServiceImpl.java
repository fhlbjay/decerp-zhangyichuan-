package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.LinkageMenu;
import cn.wolfcode.crm.mapper.LinkageMenuMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ILinkageMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LinkageMenuServiceImpl implements ILinkageMenuService {
	@Autowired
	private LinkageMenuMapper mapper;

	@Override
	public void deleteByPrimaryKey(Long id) {
		mapper.deleteDirnameByRootId(id);
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void insert(LinkageMenu record) {
		mapper.insert(record);
	}

	@Override
	public LinkageMenu selectByPrimaryKey(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<LinkageMenu> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public LinkageMenu seleRoot(String name) {
		return mapper.seleRoot(name);
	}

	@Override
	public void updateByPrimaryKey(LinkageMenu record) {
		mapper.updateByPrimaryKey(record);
	}

	@Override
	public PageResult query(QueryObject qo) {
		int count = mapper.queryCount(qo);
		if (count==0){
		   return  new PageResult(count, Collections.emptyList());
		}

		return new PageResult(count,mapper.queryList(qo));
	}
}

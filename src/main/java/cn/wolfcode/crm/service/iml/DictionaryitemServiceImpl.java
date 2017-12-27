package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Dictionaryitem;
import cn.wolfcode.crm.mapper.DictionaryitemMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.service.IDictionaryitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DictionaryitemServiceImpl implements IDictionaryitemService {
	@Autowired
	private DictionaryitemMapper mapper;

	@Override
	public void deleteByPrimaryKey(Long id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void insert(Dictionaryitem record) {
		mapper.insert(record);
	}



	@Override
	public void updateByPrimaryKey(Dictionaryitem record) {
			mapper.updateByPrimaryKey(record);
	}

	@Override
	public PageResult query(String sn) {
		int count = mapper.queryCount(sn);
		if (count==0){
		    return  new PageResult(count, Collections.EMPTY_LIST);
		}
		return new PageResult(count,mapper.queryList(sn));
	}
}

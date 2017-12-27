package cn.wolfcode.crm.service.iml;import cn.wolfcode.crm.domain.Dictionary;import cn.wolfcode.crm.mapper.DictionaryMapper;import cn.wolfcode.crm.mapper.DictionaryitemMapper;import cn.wolfcode.crm.query.PageResult;import cn.wolfcode.crm.query.QueryObject;import cn.wolfcode.crm.service.IDictionaryService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import java.util.Collections;import java.util.List;/** * Created by felix on 2017/12/21. */@Servicepublic class DictionaryServiceImpl implements IDictionaryService {	@Autowired	private DictionaryMapper mapper;	@Autowired	private DictionaryitemMapper itemMapper;	@Override	public void deleteByPrimaryKey(Long id) {		itemMapper.deleteDictionaryitemByDictionaryId(id);		mapper.deleteByPrimaryKey(id);	}	@Override	public void insert(Dictionary record) {		mapper.insert(record);	}	@Override	public Dictionary selectByPrimaryKey(Long id) {		return mapper.selectByPrimaryKey(id);	}	@Override	public List<Dictionary> selectAll() {		return mapper.selectAll();	}	@Override	public void updateByPrimaryKey(Dictionary record) {		mapper.updateByPrimaryKey(record);	}	@Override	public PageResult query(QueryObject qo) {		int count = mapper.queryCount(qo);		if (count==0){		    return  new PageResult(count, Collections.EMPTY_LIST);		}		return new PageResult(count,mapper.queryList(qo));	}}
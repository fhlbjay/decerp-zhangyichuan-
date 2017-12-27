package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Dirname;

import java.util.List;

public interface DirnameMapper {
    void  deleteByPrimaryKey(Long id);

    void  insert(Dirname record);

    Dirname selectByPrimaryKey(Long id);

    List<Dirname> selectAll();

    void updateByPrimaryKey(Dirname record);

    Dirname selectParentByName(String name);

	List<Dirname> selectDirnameBymenuId(Long id);
	List<Dirname> selectDirnemByParentId(Long id);
}
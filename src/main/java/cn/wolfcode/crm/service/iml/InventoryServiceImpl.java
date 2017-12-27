package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Inventory;
import cn.wolfcode.crm.domain.InventoryItem;
import cn.wolfcode.crm.mapper.InventoryItemMapper;
import cn.wolfcode.crm.mapper.InventoryMapper;
import cn.wolfcode.crm.mapper.ProductStockMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IInventoryService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

@Service
public class InventoryServiceImpl implements IInventoryService {
    @Autowired
    InventoryMapper mapper;
    @Autowired
    ProductStockMapper productStockMapper;
    @Autowired
    InventoryItemMapper inventoryItemMapper;

    @Override
    public int insert(Inventory inventory) {
        return mapper.insert(inventory);
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
    public String selectNumber(Long pId, Long dId,Long iId, Integer number) {
        Integer storeNumber = productStockMapper.selectNumber(pId,dId);
        if (storeNumber == null){
            storeNumber = 0;
        }
        if (storeNumber != number){
            return "盘点失败,实际库存数量为"+storeNumber;
        }else {
            mapper.update(iId);
            InventoryItem inventoryItem = new InventoryItem();
            inventoryItem.setInputTime(new Date());
            inventoryItem.setOldNumber(new BigDecimal(number));
            inventoryItem.setNowNumber(new BigDecimal(storeNumber));
            inventoryItem.setInputUser((Employee)SecurityUtils.getSubject().getPrincipal());
            inventoryItem.setInventoryId(iId);
            inventoryItemMapper.insert(inventoryItem);
            return "数量一致,盘点成功";
        }
    }

    @Override
    public void changeNumber(Long pId, Long dId,Long psId, Long iId, Integer number,Integer newNumber) {
        productStockMapper.changeNumber(psId,number);
        mapper.update(iId);
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setInputTime(new Date());
        inventoryItem.setOldNumber(new BigDecimal(number));
        inventoryItem.setNowNumber(new BigDecimal(newNumber));
        inventoryItem.setInputUser((Employee) SecurityUtils.getSubject().getPrincipal());
        inventoryItem.setInventoryId(iId);
        inventoryItemMapper.insert(inventoryItem);
    }

    @Override
    public PageResult getItems(QueryObject queryObject, Long id) {
        int count = inventoryItemMapper.queryCount(queryObject,id);
        if(count>0){
            return new PageResult(count,inventoryItemMapper.queryList(queryObject,id));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }
}

package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.ProductStock;
import cn.wolfcode.crm.domain.PurchasingOrderBill;
import cn.wolfcode.crm.domain.PurchasingOrderBillItem;
import cn.wolfcode.crm.mapper.BillMapper;
import cn.wolfcode.crm.mapper.ProductStockMapper;
import cn.wolfcode.crm.mapper.PurchasingOrderBillItemMapper;
import cn.wolfcode.crm.mapper.PurchasingOrderBillMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPurchasingOrderBillService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PurchasingOrderBillServiceImpl implements IPurchasingOrderBillService {
    @Autowired
    PurchasingOrderBillMapper purchasingOrderBillMapper;
    @Autowired
    PurchasingOrderBillItemMapper purchasingOrderBillItemMapper;
    @Autowired
    BillMapper billMapper;
    @Autowired
    ProductStockMapper productStockMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        purchasingOrderBillMapper.deleteByPrimaryKey(id);
        purchasingOrderBillItemMapper.deleteByBillId(id);
        return 0;
    }

    @Override
    public int insert(PurchasingOrderBill orderBill) {
        // 设置订单信息
        orderBill.setInputUser((Employee)SecurityUtils.getSubject().getPrincipal());

        // 获取明细信息
        List<PurchasingOrderBillItem> items = orderBill.getItems();
        // 计算总价格和总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (PurchasingOrderBillItem item : items) {
            //totalAmount = totalAmount.add(item.getCostPrice().multiply(item.getNumber()));
            totalAmount = totalAmount.add(item.getAmount());
            totalNumber = totalNumber.add(item.getNumber());
        }
        orderBill.setTotalAmount(totalAmount);
        orderBill.setTotalNumber(totalNumber);
        // 避免状态值被改变重新设置
        orderBill.setStatus(PurchasingOrderBill.STATUS_NOMAL);

        purchasingOrderBillMapper.insert(orderBill);

        // 保存明细
        for (PurchasingOrderBillItem item : items) {
            item.setAmount(item.getAmount());
            item.setBillId(orderBill.getId());
            purchasingOrderBillItemMapper.insert(item);
        }
        return 0;
    }

    @Override
    public PurchasingOrderBill selectByPrimaryKey(Long id) {
        PurchasingOrderBill depot = purchasingOrderBillMapper.selectByPrimaryKey(id);
        return depot;
    }

    @Override
    public List<PurchasingOrderBill> selectAll() {
        List<PurchasingOrderBill> list = purchasingOrderBillMapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(PurchasingOrderBill orderBill) {
        //如果当前订单已经审核,则不修改
        PurchasingOrderBill oldOrderBill = purchasingOrderBillMapper.selectByPrimaryKey(orderBill.getId());
        if (oldOrderBill.getStatus() == PurchasingOrderBill.STATUS_NOMAL) {
            // 获取明细信息
            List<PurchasingOrderBillItem> items = orderBill.getItems();
            // 计算总价格和总数量
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalNumber = BigDecimal.ZERO;
            //先删除原来的明细,再保存现在的明细
            purchasingOrderBillItemMapper.deleteByBillId(orderBill.getId());
            for (PurchasingOrderBillItem item : items) {
                totalAmount = totalAmount.add(item.getAmount());
                totalNumber = totalNumber.add(item.getNumber());

                // 保存明细,此时订单id是有的,所以可以写在一个循环里,但保存方法中不能写在一个循环里,只有订单保存了之后才会有订单id
                item.setAmount(item.getAmount());
                item.setBillId(orderBill.getId());
                purchasingOrderBillItemMapper.insert(item);
            }
            orderBill.setTotalAmount(totalAmount);
            orderBill.setTotalNumber(totalNumber);

            purchasingOrderBillMapper.updateByPrimaryKey(orderBill);

        }
        return 0;
    }

    @Override
    public PageResult query(QueryObject qo) {

        Integer count = purchasingOrderBillMapper.queryCount(qo);
        if(count>0){
            return new PageResult(count,purchasingOrderBillMapper.queryList(qo));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }

    //明细
    @Override
    public PageResult getItemsById(Long id) {
        Integer count = billMapper.queryCount(id);
        if(count>0){
            return new PageResult(count,billMapper.queryList(id));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }

    @Override
    public void audit(Long id) {
        PurchasingOrderBill orderBill = purchasingOrderBillMapper.selectByPrimaryKey(id);
        if (orderBill.getStatus() == PurchasingOrderBill.STATUS_NOMAL) {
            orderBill.setAuditor((Employee) SecurityUtils.getSubject().getPrincipal());
            orderBill.setAuditTime(new Date());
            orderBill.setStatus(PurchasingOrderBill.STATUS_AUDIT);
            purchasingOrderBillMapper.audit(orderBill);

            //入库
            //添加库存信息
            System.out.println("---------------------------------------");
            List<PurchasingOrderBillItem> items = orderBill.getItems();
            for (PurchasingOrderBillItem item : items) {
                //根据仓库id和商品id去库存中查询
                ProductStock productStock = productStockMapper.selectByDepotIdAndProductId(orderBill.getDepot().getId(),item
                        .getProduct().getId());
                if (productStock == null) {
                    //如果不存在就插入
                    System.out.println("---------------------------------------");
                    ProductStock stock = new ProductStock();
                    stock.setPrice(item.getCostPrice());
                    stock.setStoreNumber(item.getNumber());
                    stock.setAmount(item.getAmount());
                    stock.setProduct(item.getProduct());
                    stock.setDepot(orderBill.getDepot());
                    stock.setInputTime(new Date());
                    productStockMapper.insert(stock);
                } else {
                    //如果存在就更新
                    productStock.setAmount(productStock.getAmount().add(item.getAmount()));
                    productStock.setStoreNumber(productStock.getStoreNumber().add(item.getNumber()));
                    productStock.setPrice(productStock.getAmount().divide(productStock.getStoreNumber(),2, BigDecimal.ROUND_HALF_UP));
                    productStock.setInputTime(new Date());
                    productStockMapper.updateByPrimaryKey(productStock);
                }
            }
        }
    }

    //退货
    @Override
    public void returnBill(Long id) {
        PurchasingOrderBill orderBill = purchasingOrderBillMapper.selectByPrimaryKey(id);
        if (orderBill.getReturnState() == PurchasingOrderBill.STATUS_NOMAL) {
            orderBill.setReturnState(PurchasingOrderBill.STATUS_AUDIT);
            purchasingOrderBillMapper.returnBill(orderBill);

            //修改库存信息
            List<PurchasingOrderBillItem> items = orderBill.getItems();
            for (PurchasingOrderBillItem item : items) {
                //根据仓库id和商品id去库存中查询
                ProductStock productStock = productStockMapper.selectByDepotIdAndProductId(orderBill.getDepot().getId(), item
                        .getProduct().getId());
                if (productStock == null) {
                    //不存在
                    throw new RuntimeException("商品" + item.getProduct().getName() + "在" + orderBill.getDepot().getName() + "中不存在");
                }
                if (productStock.getStoreNumber().compareTo(item.getNumber()) < 0) {
                    //库存不足
                    throw new RuntimeException("商品" + item.getProduct().getName() + "的库存[" + productStock.getStoreNumber() + "]不足" + item.getNumber());
                }
                //修改库存
                productStock.setStoreNumber(productStock.getStoreNumber().subtract(item.getNumber()));
                productStock.setAmount(productStock.getStoreNumber().multiply(productStock.getPrice()));
                productStockMapper.updateByPrimaryKey(productStock);

            }
        }
    }

    @Override
    public PageResult queryReturn(QueryObject qo) {
        Integer count = purchasingOrderBillMapper.queryReturnCount(qo);
        if(count>0){
            return new PageResult(count,purchasingOrderBillMapper.queryReturnList(qo));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }

    @Override
    public List<PurchasingOrderBill> selectAllBill() {
        return purchasingOrderBillMapper.selectAllBill();
    }

}

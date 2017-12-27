package cn.wolfcode.crm.service.iml;


import cn.wolfcode.crm.domain.*;
import cn.wolfcode.crm.mapper.*;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.ITempbillService;
import cn.wolfcode.crm.util.JudgeNumber;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TempbillServiceImpl implements ITempbillService {


    @Autowired
    private VipMapper vipMapper;
    @Autowired
    private VipcardMapper vipcardMapper;
    @Autowired
    private TempbillMapper tempbillMapper;
    @Autowired
    private TempbillItemMapper tempbillItemMapper;

    @Autowired
    private ShopingcarMapper shopingcarMapper;
    @Autowired
    private ProductMapper productMapper;


    public void deleteByPrimaryKey(Long id) {

    }

    /**
     * 存入挂单表 并不需要计算会员积分以及礼品积分
     *
     * @param bill
     */
    public void insert(Orderbill bill) {
        //录入时候判断挂单表中是否需要删除
        if (bill.getStatus() == 0) {
            tempbillMapper.deleteByPrimaryKey(bill.getId());
        }
        bill.setId(null);


        //录入人
        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        bill.setInputUser(employee);


        //存入订单和订单明细
        List<OrderbillItem> items = bill.getItems();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (OrderbillItem item : items) {

            BigDecimal costPrice = JudgeNumber.judgeNumber(item.getCostPrice());
            BigDecimal number = JudgeNumber.judgeNumber(item.getNumber());


            totalAmount = totalAmount.add(costPrice.multiply(number));
            totalNumber = totalNumber.add(number);

        }
        bill.setTotalAmount(totalAmount);
        bill.setTotalNumber(totalNumber);
        tempbillMapper.insert(bill);
        for (OrderbillItem item : items) {
            //设置单据明细中的小计
            item.setAmount(item.getCostPrice().multiply(item.getNumber()));
            item.setBillId(bill.getId());
            tempbillItemMapper.insert(item);
        }


    }

    public Orderbill selectByPrimaryKey(Long id) {
        return null;
    }

    public List<Orderbill> selectAll() {
        return null;
    }

    public void updateByPrimaryKey(Orderbill record) {

    }

    public MyPageResult query(MyQueryObject qo) {
        return null;
    }

    public void audit(Long id) {

    }

    @Override
    public PageResult cashGetTemp(QueryObject qo) {
        Integer count = tempbillMapper.queryDataCount(qo);
        if (count == 0) {
            return new PageResult(0, Collections.EMPTY_LIST);

        }
        List<Orderbill> list = tempbillMapper.queryDataList(qo);
        return new PageResult(count, list);
    }

    //将临时物品加入  临时表

    /**
     * 产品id
     *
     * @param id
     */
    public void shopadd(Long id) {
        Shopingcar shopingcar = shopingcarMapper.selectByproductKey(id);
        Product product = productMapper.selectByPrimaryKey(id);
        BigDecimal salePrice = product.getSalePrice();
        if (shopingcar == null) {
            Shopingcar shopcar = new Shopingcar();
            shopcar.setProductid(id);
            shopcar.setName(product.getName());
            shopcar.setNumber(new BigDecimal("1"));
            shopcar.setCostPrice(salePrice);
            shopcar.setAmount(salePrice.multiply(new BigDecimal("1")));
            shopcar.setStatus(0L);
            shopingcarMapper.insert(shopcar);
        } else {
            //如果表中有 那么就要相加  然后更新
            BigDecimal number = shopingcar.getNumber();
            BigDecimal costprice = shopingcar.getCostPrice();
            BigDecimal newNumber = shopingcar.getNumber().add(new BigDecimal("1"));
            shopingcar.setNumber(newNumber);
            shopingcar.setAmount(costprice.multiply(newNumber));
            shopingcarMapper.updateByPrimaryKey(shopingcar);

        }


    }

    @Override
    public PageResult shopAll(QueryObject qo) {
        Integer count = shopingcarMapper.queryCount(qo);
        if (count > 0) {
            return new PageResult(count, shopingcarMapper.queryList(qo));
        }
        return new PageResult(count, Collections.EMPTY_LIST);

    }

    @Override
    public void clearall() {
        shopingcarMapper.deleteAll();
    }

    /**
     * 挂单
     *
     * @param vipid
     */
    public void saveTempBill(Long vipid) {
        Orderbill bill = new Orderbill();
        if (vipid != null) {
            Vip vip = vipMapper.selectByPrimaryKey(vipid);
            bill.setVip(vip);
        }
        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();
        bill.setInputUser(employee);
        List<Shopingcar> shopingcars = shopingcarMapper.selectAll();
        bill.setVdate(new Date());
        BigDecimal totalAmount = shopingcarMapper.selectTotalAmount();
        bill.setTotalAmount(totalAmount);

        BigDecimal totalNumber = shopingcarMapper.selectTotalNumber();
        bill.setTotalNumber(totalNumber);
        bill.setStatus(Orderbill.STATUS_NOMAL);

        tempbillMapper.insert(bill);


        //存订单明细
        for (Shopingcar shopingcar : shopingcars) {
            OrderbillItem item = new OrderbillItem();
            item.setCostPrice(shopingcar.getCostPrice());
            item.setNumber(shopingcar.getNumber());
            item.setAmount(shopingcar.getAmount());
            item.setRemark(shopingcar.getRemark());
            Product product = productMapper.selectByPrimaryKey(shopingcar.getProductid());
            item.setProduct(product);
            item.setBillId(bill.getId());
            tempbillItemMapper.insert(item);
        }

        shopingcarMapper.deleteAll();

    }

    @Override
    public void getTempbill(Long id) {
        //先删除shopingcar中的所有东西
        shopingcarMapper.deleteAll();

        //拿到临时订单  r 然后存入购物车
        Orderbill bill = tempbillMapper.selectByPrimaryKey(id);
        List<OrderbillItem> items = tempbillItemMapper.selectByTempbillId(id);
        for (OrderbillItem item : items) {

            Shopingcar shopingcar = new Shopingcar();
            shopingcar.setProductid(item.getProduct().getId());
            shopingcar.setName(item.getProduct().getName());
            shopingcar.setCostPrice(item.getCostPrice());
            shopingcar.setNumber(item.getNumber());
            shopingcar.setAmount(item.getAmount());
            shopingcar.setRemark(item.getRemark());
            shopingcar.setTotalamount(bill.getTotalAmount());

            if (bill.getVip() != null) {
                shopingcar.setVipId(bill.getVip().getId());
            }
            shopingcarMapper.insert(shopingcar);
        }

        //取单完成之后将  挂单清楚掉

        tempbillMapper.deleteByPrimaryKey(id);
        tempbillItemMapper.deleteByOrederbillKey(id);


    }

    //删除一个挂单
    public void deleteOneTempbill(Long id) {

        tempbillMapper.deleteByPrimaryKey(id);
        tempbillItemMapper.deleteByOrederbillKey(id);
    }

    /**
     * 通过计算将需要显示的值带入
     *
     * @return
     */
    public Map<String, Object> amountForm(Long id) {
        BigDecimal discount;
        Map<String, Object> map = new HashMap<>();
        if (id != null) {
            Vip vip = vipMapper.selectByPrimaryKey(id);
            Long vipcardId = vip.getVipcard().getId();
            Vipcard vipcard = vipcardMapper.selectById(vipcardId);
            discount = vipcard.getDiscount();//得到折扣


//            map.put("vipid", vipcard.getSn());
            map.put("vipid", id);
            map.put("vipName", vip.getName());
            map.put("vitMoney", vipcard.getBalance());
            map.put("vitCut", vipcard.getDiscount());

        } else {
            discount = new BigDecimal("1");
        }

        //总数  折扣了多少  应付 实付
        BigDecimal tempTotalAmount = shopingcarMapper.selectTotalAmount(); //应付
        BigDecimal totalNumber = shopingcarMapper.selectTotalNumber(); //总数量
        BigDecimal multiply = tempTotalAmount.multiply(discount);  //乘以折扣
        BigDecimal totalAmount = multiply.setScale(1, BigDecimal.ROUND_HALF_UP);  //实际付钱
        BigDecimal cutAmount = tempTotalAmount.subtract(totalAmount);  //得到折扣金额


        map.put("totalNumber", totalNumber);
        map.put("cutAmount", cutAmount);
        map.put("tempTotalAmount", tempTotalAmount);
        map.put("totalAmount", totalAmount);


        return map;
    }

    //删除其中一个
    public void removeOne(Long productId) {
        shopingcarMapper.deleteByProductId(productId);
    }

    @Override
    public Map<String, Object> amountFormGetemp() {
        BigDecimal discount;
        Map<String, Object> map = new HashMap<>();

        BigDecimal sunVipId = shopingcarMapper.selectSunVipId();
        if (sunVipId == null) {
            sunVipId = BigDecimal.ZERO;
        }
        if (sunVipId.compareTo(BigDecimal.ZERO) == 1) {

            BigDecimal countVipId = shopingcarMapper.selectCountVipId();
            Long id = sunVipId.divide(countVipId).longValue();

            Vip vip = vipMapper.selectByPrimaryKey(id);
            Long vipcardId = vip.getVipcard().getId();
            Vipcard vipcard = vipcardMapper.selectById(vipcardId);
            discount = vipcard.getDiscount();//得到折扣


            map.put("vipid", id);
            map.put("vipName", vip.getName());
            map.put("vitMoney", vipcard.getBalance());
            map.put("vitCut", vipcard.getDiscount());
        } else {
            discount = new BigDecimal("1");
        }


        //总数  折扣了多少  应付 实付
        BigDecimal tempTotalAmount = shopingcarMapper.selectTotalAmount(); //应付
        BigDecimal totalNumber = shopingcarMapper.selectTotalNumber(); //总数量
        if (tempTotalAmount == null) {
            tempTotalAmount = BigDecimal.ZERO;
        }
        System.out.println(tempTotalAmount);
        System.out.println(discount);
        BigDecimal multiply = tempTotalAmount.multiply(discount);  //乘以折扣
        BigDecimal totalAmount = multiply.setScale(1, BigDecimal.ROUND_HALF_UP);  //实际付钱
        BigDecimal cutAmount = tempTotalAmount.subtract(totalAmount);  //得到折扣金额


        map.put("totalNumber", totalNumber);
        map.put("cutAmount", cutAmount);
        map.put("tempTotalAmount", tempTotalAmount);
        map.put("totalAmount", totalAmount);


        return map;
    }


}

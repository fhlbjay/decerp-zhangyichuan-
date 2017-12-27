package cn.wolfcode.crm.service.iml;


import cn.wolfcode.crm.domain.*;
import cn.wolfcode.crm.mapper.*;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Orderbill;
import cn.wolfcode.crm.domain.OrderbillItem;
import cn.wolfcode.crm.mapper.OrderbillItemMapper;
import cn.wolfcode.crm.mapper.OrderbillMapper;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;
import cn.wolfcode.crm.service.IOrderbillService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderbillServiceImpl implements IOrderbillService {

    @Autowired
    private OrderbillMapper mapper;
    @Autowired
    private OrderbillItemMapper orderbillItemMapper;
    @Autowired
    private VipMapper vipMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private VipcardMapper vipcardMapper;
    @Autowired
    private ShopingcarMapper shopingcarMapper;
    @Autowired
    private IntegralDetailMapper integralDetailMapper;


    @Override
    public void deleteByPrimaryKey(Long id) {
        //先删除表单中的数据
        mapper.deleteByPrimaryKey(id);

        //在删除明细
        orderbillItemMapper.deleteByOrederbillKey(id);

    }

    @Override
    public void insert(Orderbill bill) {


        Employee employee = (Employee) SecurityUtils.getSubject().getPrincipal();

        //录入人
        bill.setInputUser(employee);


        List<OrderbillItem> items = bill.getItems();
        System.out.println(items);
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        for (OrderbillItem item : items) {
            totalAmount = totalAmount.add(item.getCostPrice().multiply(item.getNumber()));
            totalNumber = totalAmount.add(item.getNumber());

        }
        bill.setStatus(Orderbill.STATUS_NOMAL);
        bill.setTotalAmount(totalAmount);
        bill.setTotalNumber(totalNumber);
        mapper.insert(bill);
        for (OrderbillItem item : items) {
            System.out.println(item.getProduct());
            //设置单据明细中的小计
            item.setAmount(item.getCostPrice().multiply(item.getNumber()));
            item.setBillId(bill.getId());
            orderbillItemMapper.insert(item);
        }

    }

    @Override
    public Orderbill selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Orderbill> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public void updateByPrimaryKey(Orderbill bill) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
        //先删除旧的数据 然后保存新的数据
        orderbillItemMapper.deleteByOrederbillKey(bill.getId());
        List<OrderbillItem> items = bill.getItems();

        for (OrderbillItem item : items) {
            System.out.println(item);
            totalAmount = totalAmount.add(item.getCostPrice().multiply(item.getNumber()));
            totalNumber = totalAmount.add(item.getNumber());

            //设置单据明细中的小计
            item.setAmount(item.getCostPrice().multiply(item.getNumber()));
            item.setBillId(bill.getId());
            orderbillItemMapper.insert(item);


        }
        bill.setTotalAmount(totalAmount);
        bill.setTotalNumber(totalNumber);
        mapper.updateByPrimaryKey(bill);
    }

    @Override
    public MyPageResult query(MyQueryObject qo) {
        Integer count = mapper.queryForCount(qo);
        if (count == 0) {

            return new MyPageResult(0);
        }
        List<Orderbill> list = mapper.queryForList(qo);

        return new MyPageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

    @Override
    public void audit(Long id) {
        Orderbill old = mapper.selectByPrimaryKey(id);
       /* if (old.getStatus() == Orderbill.STATUS_NOMAL) {
            old.setAuditor(UserContext.getEmployee());
            old.setAuditTime(new Date());
            old.setStatus(Orderbill.STATUS_AUDIT);
            mapper.audit(old);

        }*/


    }


    @Override
    public void saveBill(Long vipid) {
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
        bill.setStatus(Orderbill.STATUS_END);

        mapper.insert(bill);


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
            item.setSaleAmount(shopingcar.getAmount().multiply(new BigDecimal("1.8")));
            orderbillItemMapper.insert(item);
        }

        if (vipid != null) {
            //vip积分增加
            Vip vip = vipMapper.selectByPrimaryKey(vipid);
            Long vipcardId = vip.getVipcard().getId();
            Vipcard vipcard = vipcardMapper.selectById(vipcardId);

            //得到余额
            BigDecimal balance = vipcardMapper.selectBalanceById(vipcardId);
            if (totalAmount.compareTo(balance) >= 0) {
                balance = new BigDecimal("0");
                vipcard.setBalance(balance);
            } else {
                balance = balance.subtract(totalAmount);
                vipcard.setBalance(balance);
            }

            //增长积分
            long totalAmountBalance = totalAmount.longValue();
            vipcard.setCurrentintegral(vipcard.getCurrentintegral() + totalAmountBalance);
            vipcard.setIntegral(vipcard.getIntegral() + totalAmountBalance);
            vipcard.setConsumptionamount(vipcard.getConsumptionamount().add(totalAmount));


            vipcardMapper.updateVipcard(vipcard);
            //增长礼品积分
            IntegralDetail integralDetail = new IntegralDetail();
            integralDetail.setVip(vip);
            integralDetail.setOperationType("增加积分");
            integralDetail.setAmountChange(totalAmount.intValue());
            integralDetail.setRemark("订单消费");
            integralDetailMapper.insert(integralDetail);
        }


        shopingcarMapper.deleteAll();


    }

    @Override
    public Boolean judgeShopcar() {
        List<Shopingcar> shopingcars = shopingcarMapper.selectAll();
        if (shopingcars == null) {
            return false;
        }
        return true;

    }

    @Override
    public void checkVip(String vipcardSn, String password) {
        //校验密码 先找出东西
       /* Vipcard vipcard = vipcardMapper.selectVipcardIdBySn(vipcardSn);
        //找到会员卡
        Vip vip = vipMapper.selectVipByVipcardId(vipcard.getId(), password);*/
        Vip vip =  vipMapper.selectVipByIdPassword(vipcardSn,password);



        if (vip == null) {
            throw new RuntimeException("密码或者账号不正确");
        }


    }

    public BigDecimal selectTotalConsum(IndexQueryObject qo) {
        return mapper.selectTotalConsum(qo);
    }

    @Override
    public Integer selectTotalNum(IndexQueryObject qo) {
        return mapper.selectTotalNum(qo);
    }

}

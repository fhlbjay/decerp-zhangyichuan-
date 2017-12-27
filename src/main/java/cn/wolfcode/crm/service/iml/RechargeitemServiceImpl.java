package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Rechargeitem;
import cn.wolfcode.crm.mapper.RechargeitemMapper;
import cn.wolfcode.crm.mapper.VipMapper;
import cn.wolfcode.crm.mapper.VipcardMapper;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IRechargeitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class RechargeitemServiceImpl implements IRechargeitemService {
    @Autowired
    RechargeitemMapper mapper;
    @Autowired
    VipcardMapper vipcardMapper;
    @Autowired
    VipMapper vipMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Rechargeitem record) {
        //充值前余额 需要根据vipcardId查询出来
        Long vipcardId = record.getVipcardId();
        //会员id
        Long vipId = record.getVipId();
        BigDecimal oldmount = vipcardMapper.selectBalanceById(vipcardId);
        //充值金额
        BigDecimal rechargemount = record.getRechargemount();
        //当前余额
        BigDecimal currentcount = oldmount.add(rechargemount);
        //充值明细更新余额 设置充值时间
        record.setOldmount(oldmount);
        record.setRechargetime(new Date());
        record.setCurrentmount(currentcount);
        //会员卡更新余额
        vipcardMapper.updateBlance(vipcardId,currentcount);
        //查询该会员累计充值金额
        BigDecimal totalRecharge = mapper.selectTotalRechargeByVipId(record.getVipId());
        BigDecimal a = new BigDecimal("1000");
        BigDecimal b = new BigDecimal("3000");
        BigDecimal c = new BigDecimal("6000");
        BigDecimal d = new BigDecimal("18000");
        BigDecimal e = new BigDecimal("52000");
        if (totalRecharge.compareTo(a)>0) {
            //累计充值大于1000 升级 青铜会员
            vipMapper.updateVipGrade(vipId,"青铜会员");
            //更新该会员的会员卡折扣
            vipcardMapper.updateDiscount(vipcardId,new BigDecimal("0.90"));
        }
        if (totalRecharge.compareTo(b)>0) {
            //累计充值大于3000 升级 青铜会员
            vipMapper.updateVipGrade(vipId,"白银会员");
            //更新该会员的会员卡折扣
            vipcardMapper.updateDiscount(vipcardId,new BigDecimal("0.85"));
        }
        if (totalRecharge.compareTo(c)>0) {
            //累计充值大于6000 升级 青铜会员
            vipMapper.updateVipGrade(vipId,"黄金会员");
            //更新该会员的会员卡折扣
            vipcardMapper.updateDiscount(vipcardId,new BigDecimal("0.80"));
        }
        if (totalRecharge.compareTo(d)>0) {
            //累计充值大于6000 升级 青铜会员
            vipMapper.updateVipGrade(vipId,"白金会员");
            //更新该会员的会员卡折扣
            vipcardMapper.updateDiscount(vipcardId,new BigDecimal("0.75"));
        }
        if (totalRecharge.compareTo(e)>0) {
            //累计充值大于6000 升级 青铜会员
            vipMapper.updateVipGrade(vipId,"钻石会员");
            //更新该会员的会员卡折扣
            vipcardMapper.updateDiscount(vipcardId,new BigDecimal("0.70"));
        }
        mapper.insert(record);
        return 0;
    }
    @Override
    public Rechargeitem selectByPrimaryKey(Long id) {
        Rechargeitem rechargeitem = mapper.selectByPrimaryKey(id);
        return rechargeitem;
    }

    @Override
    public List<Rechargeitem> selectAll() {
        List<Rechargeitem> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Rechargeitem record) {
        mapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public PageResult query(QueryObject qo) {

        Integer count = mapper.queryCount(qo);
        if(count>0){
            return new PageResult(count,mapper.queryList(qo));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }

    @Override
    public List<Rechargeitem> selectRechargeitemByVipId(QueryObject qo, Long id) {

        return mapper.selectRechargeitemByVipId(id);
    }

    @Override
    public BigDecimal selectTotalRecharge(IndexQueryObject qo) {
        return mapper.selectTotalRecharge(qo);
    }
}

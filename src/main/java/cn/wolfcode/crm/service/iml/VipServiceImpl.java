package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.Vip;
import cn.wolfcode.crm.domain.Vipcard;
import cn.wolfcode.crm.mapper.VipMapper;
import cn.wolfcode.crm.mapper.VipcardMapper;
import cn.wolfcode.crm.query.IndexQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class VipServiceImpl implements IVipService {
    @Autowired
    VipMapper mapper;
    @Autowired
    VipcardMapper vipcardMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Vip record) {
        //拿到新建的会员卡编码
        String sn = record.getVipcard().getSn();
        //创建新的会员卡
        Vipcard vipcard =new Vipcard();
        //给会员卡设置编码
        vipcard.setSn(sn);
        //根据会员的等级设置会员卡的折扣
        //获得会员的等级
        String vipgrade = record.getVipgrade();
        //设置折扣
        if("普通会员".equals(vipgrade)){
            vipcard.setDiscount(new BigDecimal(0.95));
        }else if ("青铜会员".equals(vipgrade)){
            vipcard.setDiscount(new BigDecimal(0.9));
        }else if ("白银会员".equals(vipgrade)){
            vipcard.setDiscount(new BigDecimal(0.85));
        }else if ("黄金会员".equals(vipgrade)){
            vipcard.setDiscount(new BigDecimal(0.8));
        }else if ("白金会员".equals(vipgrade)){
            vipcard.setDiscount(new BigDecimal(0.75));
        }else if ("钻石会员".equals(vipgrade)){
            vipcard.setDiscount(new BigDecimal(0.7));
        }
        //会员卡新增至数据库
        vipcardMapper.insert(vipcard);
        //拿到新增会员卡后的id
        Long vipcardId = vipcard.getId();
        //给会员的会员卡设置会员卡id
        record.getVipcard().setId(vipcardId);
        //设置会员的会员卡状态
        record.setState(true);
        //保存会员
        mapper.insert(record);
        return 0;
    }

    @Override
    public Vip selectByPrimaryKey(Long id) {
        Vip vip = mapper.selectByPrimaryKey(id);
        return vip;
    }

    @Override
    public List<Vip> selectAll() {
        List<Vip> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Vip record) {
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
    public void changeState(Long id) {
        mapper.changeState(id);
    }

    @Override
    public Integer selectBirthdayOfToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);
        return mapper.selectBirthdayOfToday(date);
    }

    @Override
    public Integer selectBirthdayOfMonth() {
        Date currentTime = new Date();
        //设置本月开始时间
        Date beginDate = new Date();
        beginDate.setDate(1);
        //设置本月结束时间
        Date endDate = new Date();
        endDate.setDate(30);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);
        String begindate = formatter.format(beginDate);
        String enddate = formatter.format(endDate);
        return mapper.selectBirthdayOfMonth(begindate,enddate);
    }

    @Override
    public Integer selectVipNumber() {
        return mapper.selectVipNumber();
    }

    @Override
    public BigDecimal selectConsumer() {
        return mapper.selectConsumer();
    }

    @Override
    public BigDecimal selectConsumptionAmount() {
        return mapper.selectConsumptionAmount();
    }

    @Override
    public Integer selectOrderBilNum() {
        return mapper.selectOrderBilNum();
    }

    @Override
    public List<String> selectConsumptionAmountTop() {
        return mapper.selectConsumptionAmountTop();
    }

    @Override
    public Integer selectTotalNumber(IndexQueryObject qo) {

        return mapper.selectTotalNumber(qo);
    }
}

package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.*;
import cn.wolfcode.crm.mapper.*;
import cn.wolfcode.crm.query.MyGiftExchangeRecordOject;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;
import cn.wolfcode.crm.service.IGiftOrderBillItemService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GiftOrderBillItemServiceImpl implements IGiftOrderBillItemService {
    @Autowired
    GiftOrderBillItemMapper mapper;
    @Autowired
    VipMapper vipMapper;
    @Autowired
    GiftMapper giftMapper;
    @Autowired
    VipcardMapper vipcardMapper;
    @Autowired
    IntegralDetailMapper integralDetailMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(List<GiftOrderBillItem> record) {
        if(record.size()==0){
            throw new RuntimeException("礼品为空");
        }
        for (GiftOrderBillItem giftOrderBillItem : record) {
            //插入之前判断积分是否合理
            //获取到VIPId
            Long vipId = giftOrderBillItem.getVip().getId();
            //拿到VIP信息
            Vip vip = vipMapper.selectByPrimaryKey(vipId);
            //拿到单前积分
            Long currentintegral = vip.getVipcard().getCurrentintegral();
            //拿到Gift信息
            Long giftId = giftOrderBillItem.getGift().getId();
            if(giftId==null){
                throw new RuntimeException("请选择礼品");
            }
            Gift gift = giftMapper.selectByPrimaryKey(giftId);
            //判断积分是否充足
            //客户提交交换礼品数
            Integer giftExchangeNumber=giftOrderBillItem.getNumber();
            //礼品所需兑换积分
            Integer giftIntegral=gift.getIntegral();
            //兑换总积分
            Integer toalCount=giftExchangeNumber*giftIntegral;
            if(giftExchangeNumber==null||giftExchangeNumber==0){
                throw new RuntimeException("请填写礼品数量");
            }
            if(currentintegral<toalCount){
                throw new RuntimeException("积分不足");
            }
            //判断数据是否充足
            if(giftExchangeNumber>gift.getCount()){
                throw new RuntimeException("礼品:"+gift.getName()+"发生变动,请重新选择该礼品");
            }
            //没有问题则开始做操作,
            //求剩余积分
            Long remainPoint=currentintegral-giftExchangeNumber*giftIntegral;
            /*1. 更新积分卡上的积分*/
            Vipcard vipcard = vip.getVipcard();
            vipcard.setCurrentintegral(remainPoint);
            vipcardMapper.updateIntegral(vipcard);
            //设置剩余积分到明细表中
            giftOrderBillItem.setRemainIntegral(remainPoint);
            /*2. 更新礼品剩余数量*/
            //剩余数量
            Integer remainNumber=gift.getSurplus()-giftOrderBillItem.getNumber();
            gift.setSurplus(remainNumber);
            giftMapper.updateByPrimaryKey(gift);
            //---------------------------------------------------------
            //获取当前登录用户
            Employee principal =(Employee)SecurityUtils.getSubject().getPrincipal();
            giftOrderBillItem.setOperation(principal.getUsername());
            //设置时间
            giftOrderBillItem.setDate(new Date());
            /*3.插入明细表*/
            mapper.insert(giftOrderBillItem);
            //----------------------------------------
            //兑换完毕后,插入积分变动明细表
            //记录积分变动操作记录
            IntegralDetail integralDetail=new IntegralDetail();
            //设置VIP对象
            integralDetail.setVip(vip);
            //设置变动积分
            integralDetail.setAmountChange(giftExchangeNumber*giftIntegral);
            //设置操作类型
            integralDetail.setOperationType("扣除积分");
            //备注
            integralDetail.setRemark("兑换礼品");
            //插入数据库
            integralDetailMapper.insert(integralDetail);
        }
        return 0;
    }

    @Override
    public GiftOrderBillItem selectByPrimaryKey(Long id) {
        GiftOrderBillItem gift = mapper.selectByPrimaryKey(id);
        return gift;
    }

    @Override
    public List<GiftOrderBillItem> selectAll() {
        List<GiftOrderBillItem> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(GiftOrderBillItem record) {
        mapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public MyPageResult query(MyQueryObject qo) {
        Integer totalCount=mapper.queryCount(qo);
        if(totalCount==0) {
            return new MyPageResult(qo.getPageSize());
        }
        List<GiftOrderBillItem> result= mapper.queryList(qo);
        return new MyPageResult(qo.getCurrentPage(), qo.getPageSize(), totalCount, result);
    }

    @Override
    public MyPageResult queryRecord(MyQueryObject qo) {
        Integer totalCount=mapper.queryRecordCount(qo);
        if(totalCount==0) {
            return new MyPageResult(qo.getPageSize());
        }
        List<GiftOrderBillItem> result= mapper.queryRecordList(qo);
        return new MyPageResult(qo.getCurrentPage(), qo.getPageSize(), totalCount, result);
    }

    @Override
    public List<GiftOrderBillItem> queryExpordRecord(MyGiftExchangeRecordOject qo) {
        return mapper.queryExpordRecord(qo);
    }
}

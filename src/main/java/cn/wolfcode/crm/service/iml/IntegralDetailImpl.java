package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.domain.IntegralDetail;
import cn.wolfcode.crm.domain.Vip;
import cn.wolfcode.crm.domain.Vipcard;
import cn.wolfcode.crm.mapper.IntegralDetailMapper;
import cn.wolfcode.crm.mapper.VipMapper;
import cn.wolfcode.crm.mapper.VipcardMapper;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.query.MyQueryObject;
import cn.wolfcode.crm.service.IIntegralDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntegralDetailImpl implements IIntegralDetailService {
    @Autowired
    IntegralDetailMapper mapper;
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
    public int insert(IntegralDetail record) {
        mapper.insert(record);
        return 0;
    }

    @Override
    public IntegralDetail selectByPrimaryKey(Long id) {
        IntegralDetail gift = mapper.selectByPrimaryKey(id);
        return gift;
    }

    @Override
    public List<IntegralDetail> selectAll() {
        List<IntegralDetail> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(IntegralDetail record) {
        mapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public MyPageResult query(MyQueryObject qo) {
        Integer totalCount = mapper.queryRecordCount(qo);
        if (totalCount == 0) {
            return new MyPageResult(qo.getPageSize());
        }
        List<IntegralDetail> result = mapper.queryRecordList(qo);
        return new MyPageResult(qo.getCurrentPage(), qo.getPageSize(), totalCount, result);
    }

    @Override
    public void clearIntegral(Long vipId) {
        //获取VIP积分
        Vip vip=vipMapper.selectByPrimaryKey(vipId);
        Vipcard vipcard = vip.getVipcard();
        Long currentintegral = vipcard.getCurrentintegral();
        //清楚VIP卡上的积分
        vipcard.setCurrentintegral(0L);
        vipcardMapper.updateIntegral(vipcard);
        //-----------------------------------------------------
        //记录积分变动操作记录
        vip.setId(vipId);
        IntegralDetail integralDetail=new IntegralDetail();
        //设置VIP对象
        integralDetail.setVip(vip);
        //设置变动积分
        integralDetail.setAmountChange(currentintegral.intValue());
        //设置操作类型
        integralDetail.setOperationType("扣除积分");
        //备注
        integralDetail.setRemark("积分清零");
        //插入数据库
        mapper.insert(integralDetail);
    }

    @Override
    public void changeIntegral(Long vipId, Long amount, Integer changeType) {
        //拿到VIP对象
        Vip vip=vipMapper.selectByPrimaryKey(vipId);
        //拿到vipcard对象和积分
        Vipcard vipcard = vip.getVipcard();
        Long currentintegral = vipcard.getCurrentintegral();
        //定义操作类型
        String type="";
        //获取累计积分
        Long integral = vipcard.getIntegral();
        if(changeType==0){
            currentintegral=currentintegral-amount;
            type="扣除积分";
        }else {
            currentintegral=currentintegral+amount;
            type="增加积分";
            //累计积分增加
            integral=integral+amount;
        }
        if(currentintegral<0){
            throw new RuntimeException("会员积分不足");
        }
        //变动卡积分入数据库
        vipcard.setCurrentintegral(currentintegral);
        vipcard.setIntegral(integral);
        vipcardMapper.updateIntegral(vipcard);

        //------------------------------------------
        //记录积分变动操作记录
        vip.setId(vipId);
        IntegralDetail integralDetail=new IntegralDetail();
        //设置VIP对象
        integralDetail.setVip(vip);
        //设置变动积分
        integralDetail.setAmountChange(amount.intValue());
        //设置操作类型
        integralDetail.setOperationType(type);
        //备注
        integralDetail.setRemark(type);
        //插入数据库
        mapper.insert(integralDetail);

    }
}

package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.GiftOrderBillItem;
import cn.wolfcode.crm.domain.GiftOrderBillItemData;
import cn.wolfcode.crm.query.MyGiftExchangeRecordOject;
import cn.wolfcode.crm.query.MyGiftOrderBillItemQueryObject;
import cn.wolfcode.crm.query.MyGiftQueryObject;
import cn.wolfcode.crm.query.MyPageResult;
import cn.wolfcode.crm.service.IGiftOrderBillItemService;
import cn.wolfcode.crm.service.IGiftService;
import cn.wolfcode.crm.util.JsonResult;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("giftOrder")
public class GiftOrderController {
    @Autowired
    IGiftService giftService;
    @Autowired
    IGiftOrderBillItemService giftOrderBillItemService;
    //跳转到里面兑换页面
    @RequestMapping("list")
    public String list() {
        return "giftOrder/list";
    }

    //跳转礼品兑换记录页面
    @RequestMapping("giftExchangeRecord")
    public String giftExchangeRecord() {
        return "giftOrder/giftExchangeRecord";
    }

    @RequestMapping("selectGiftList")
    public String selectGiftList(Model model, @ModelAttribute("qo") MyGiftQueryObject qo) {
        MyPageResult result = giftService.query(qo);
        model.addAttribute("result", result);
        return "giftOrder/giftList";
    }

    @ResponseBody
    @RequestMapping("selectGiftOrderBillItem")
    public Object selectGiftOrderBillItem(MyGiftOrderBillItemQueryObject qo) {
        MyPageResult result = giftOrderBillItemService.query(qo);
        return result;
    }
    //礼物兑换
    @ResponseBody
    @RequestMapping("giftExchange")
    public Object giftExchange(GiftOrderBillItemData data) {
        List<GiftOrderBillItem> list = data.getList();
        try {
                giftOrderBillItemService.insert(list);
        } catch (Exception e) {
            return new JsonResult(e.getMessage());
        }
        return new JsonResult();
    }

    //礼物兑换记录表
    @ResponseBody
    @RequestMapping("selectGiftExchangeRecord")
    public Object selectGiftExchangeRecord(MyGiftExchangeRecordOject qo) {
        MyPageResult result = giftOrderBillItemService.queryRecord(qo);
        return result;
    }

    @RequestMapping("export")
    public void export(HttpServletRequest request, HttpServletResponse response,MyGiftExchangeRecordOject qo) throws Exception {

        //设置为下载方式
        response.setContentType("application/x-msdownload");
        //get乱码请求处理
        String keyword = qo.getKeyword();
        qo.setKeyword(new String(keyword.getBytes("iso-8859-1"),"utf-8"));
        String fileName="兑换记录表.xls";

        //判断浏览器类型,设置文件下载响应头
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("IE")) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        } else {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        }
        //创建xls文件
        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
        //创建工作簿
        WritableSheet sheet = workbook.createSheet("兑换记录表", 0);

        //添加标题
        sheet.addCell(new Label(0,0,"会员卡号"));
        sheet.addCell(new Label(1,0,"会员名称"));
        sheet.addCell(new Label(2,0,"礼品名称"));
        sheet.addCell(new Label(3,0,"兑换数量"));
        sheet.addCell(new Label(4,0,"消费积分"));
        sheet.addCell(new Label(5,0,"操作时间"));
        sheet.addCell(new Label(6,0,"操作人员"));
        //查询出符合数据
        List<GiftOrderBillItem> list = giftOrderBillItemService.queryExpordRecord(qo);
        //定义格式转化类型
        for (int i = 0,j=1; i < list.size(); i++,j++) {
            GiftOrderBillItem giftOrderBillItem = list.get(i);
            //往工作簿中添加内容
            sheet.addCell(new Label(0,j,giftOrderBillItem.getVip().getVipcard().getSn().toString()));
            sheet.addCell(new Label(1,j,giftOrderBillItem.getVip().getName()));
            sheet.addCell(new Label(2,j,giftOrderBillItem.getGift().getName()));
            sheet.addCell(new Label(3,j,giftOrderBillItem.getNumber().toString()));
            sheet.addCell(new Label(4,j,(giftOrderBillItem.getGift().getIntegral()*giftOrderBillItem.getNumber())+""));
            //设置要获取到什么样的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取String类型的时间
            sheet.addCell(new Label(5,j,sdf.format(giftOrderBillItem.getDate())));
            sheet.addCell(new Label(6,j,giftOrderBillItem.getOperation()));

        }

        //写入数据
        workbook.write();

        //关闭资源
        workbook.close();
    }
}



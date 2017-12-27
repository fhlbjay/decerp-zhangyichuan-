package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Vip;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IVipService;
import cn.wolfcode.crm.util.JsonResult;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("vip")
public class VipController {
	@Autowired
	private IVipService vipService;

	@RequestMapping("list")
	@RequiresPermissions("vip:list")
	@PermissionName("会员查看")
	public String list(@ModelAttribute("qo") QueryObject queryObject) {
		return "vip";
	}
	@RequestMapping("recharge")
	@RequiresPermissions("vip:recharge")
	@PermissionName("会员充值页面")
	public String recharge(@ModelAttribute("qo") QueryObject queryObject) {
		return "recharge";
	}

	@RequestMapping("view")
	@RequiresPermissions("vip:view")
	@PermissionName("会员列表")
	@ResponseBody
	public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
		PageResult result = vipService.query(queryObject);
		return result;
	}

	@RequestMapping("selectAll")
	@RequiresPermissions("vip:selectAll")
	@PermissionName("会员列表")
	@ResponseBody
	public List<Vip> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
		List<Vip> vips = vipService.selectAll();
		return vips;
	}

	@RequestMapping("selectBirthdayOfToday")
	@RequiresPermissions("vip:selectBirthdayOfToday")
	@PermissionName("会员列表")
	@ResponseBody
	public Integer selectBirthdayOfToday(@ModelAttribute("qo") QueryObject queryObject) {
		Integer integer = vipService.selectBirthdayOfToday();
		return integer;
	}
	@RequestMapping("selectBirthdayOfMonth")
	@RequiresPermissions("vip:selectBirthdayOfMonth")
	@PermissionName("会员列表")
	@ResponseBody
	public Integer selectBirthdayOfMonth(@ModelAttribute("qo") QueryObject queryObject) {
		Integer integer = vipService.selectBirthdayOfMonth();
		return integer;
	}

	@RequestMapping("selectVipNumber")
	@RequiresPermissions("vip:selectVipNumber")
	@PermissionName("会员列表")
	@ResponseBody
	public Integer selectVipNumber(@ModelAttribute("qo") QueryObject queryObject) {
		Integer integer = vipService.selectVipNumber();
		return integer;
	}

	@RequestMapping("selectConsumer")
	@RequiresPermissions("vip:selectConsumer")
	@PermissionName("会员列表")
	@ResponseBody
	public BigDecimal selectConsumer(@ModelAttribute("qo") QueryObject queryObject) {
		BigDecimal bigDecimal = vipService.selectConsumer();
		return bigDecimal;
	}

	@RequestMapping("selectConsumptionAmount")
	@RequiresPermissions("vip:selectConsumptionAmount")
	@PermissionName("会员列表")
	@ResponseBody
	public BigDecimal selectConsumptionAmount(@ModelAttribute("qo") QueryObject queryObject) {
		BigDecimal bigDecimal = vipService.selectConsumptionAmount();
		return bigDecimal;
	}
	@RequestMapping("selectOrderBilNum")
	@RequiresPermissions("vip:selectOrderBilNum")
	@PermissionName("会员列表")
	@ResponseBody
	public Integer selectOrderBilNum(@ModelAttribute("qo") QueryObject queryObject) {
		Integer bigDecimal = vipService.selectOrderBilNum();
		return bigDecimal;
	}

    @RequestMapping("selectConsumptionAmountTop")
    @RequiresPermissions("vip:selectConsumptionAmountTop")
    @PermissionName("会员列表")
    @ResponseBody
    public List<String> selectConsumptionAmountTop(@ModelAttribute("qo") QueryObject queryObject) {
        List<String> list = vipService.selectConsumptionAmountTop();
        return list;
    }
	@RequestMapping("saveOrUpdate")
	@RequiresPermissions("vip:saveOrUpdate")
	@PermissionName("会员保存或更新")
	@ResponseBody
	public JsonResult saveOrUpdate(Vip vip) {
		try {
			if (vip.getId()!= null) {
				vipService.updateByPrimaryKey(vip);
			} else {
				vipService.insert(vip);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@PermissionName("会员删除")
	@RequiresPermissions("vip:delete")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				vipService.deleteByPrimaryKey(id);
			}
		return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return new JsonResult("删除失败");
	}
	@RequestMapping("changeState")
	@PermissionName("更新会员卡状态")
	@RequiresPermissions("department:changeState")
	@ResponseBody
	public JsonResult changeState(Long id) {
		vipService.changeState(id);
		return new JsonResult();
	}
	@RequestMapping("exportXls")
	public void exportXls(HttpServletResponse response) throws Exception {
		//文件下载响应头
		response.setHeader("Content-Disposition", "attachment;filename=vip.xls");
		//创建xls文件
		WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
		//创建工作簿
		WritableSheet sheet = workbook.createSheet("day01", 0);
		//获取真实的会员数据
		List<Vip> vips = vipService.selectAll();
		//添加标题
		sheet.addCell(new Label(0, 0, "会员卡号"));
		sheet.addCell(new Label(1, 0, "会员姓名"));
		sheet.addCell(new Label(2, 0, "会员等级"));
		sheet.addCell(new Label(3, 0, "电话"));
		sheet.addCell(new Label(4, 0, "邮箱"));
		sheet.addCell(new Label(5, 0, "qq"));
		sheet.addCell(new Label(6, 0, "微信"));
		sheet.addCell(new Label(7, 0, "会员可用积分"));
		sheet.addCell(new Label(8, 0, "可用余额"));
		sheet.addCell(new Label(9, 0, "会员卡状态"));
		sheet.addCell(new Label(10, 0, "会员生日"));
		for (int i = 0, j = 1; i < vips.size(); i++, j++) {
			Vip vip = vips.get(i);

            /*//创建文本单元格
            Label label = new Label(0,0,"");*/

			//往工作簿中添加内容

			sheet.addCell(new Label(0, j, vip.getVipcard().getSn()));
			sheet.addCell(new Label(1, j, vip.getName()));
			sheet.addCell(new Label(2, j, vip.getVipgrade()));
			sheet.addCell(new Label(3, j, vip.getTel()));
			sheet.addCell(new Label(4, j, vip.getEmail()));
			sheet.addCell(new Label(5, j, vip.getQq()));
			sheet.addCell(new Label(6, j, vip.getWeixin()));
			if(vip.getVipcard().getIntegral()!=null){
				sheet.addCell(new Label(7, j, vip.getVipcard().getIntegral().toString()));
			}else {
				sheet.addCell(new Label(7, j, "null"));
			}
			if (vip.getVipcard().getBalance() != null) {
				sheet.addCell(new Label(8, j, vip.getVipcard().getBalance().toString()));
			}else {
				sheet.addCell(new Label(8, j, "null"));
			}
			if (vip.getState() != null) {
				sheet.addCell(new Label(9, j, vip.getState().toString()));
			}else {
				sheet.addCell(new Label(9, j, "null"));
			}
			sheet.addCell(new Label(10, j,   new SimpleDateFormat("yyyy-MM-dd").format(vip.getBirthday())));
		}
		//写入数据
		workbook.write();

		//关闭资源
		workbook.close();
	}

	@RequestMapping("importXls")
	@ResponseBody
	public JsonResult importXls(MultipartFile file) throws Exception {
		Workbook workbook = null;
		try {
			//读取xls文件
			workbook = Workbook.getWorkbook(file.getInputStream());
			//读取某个工作簿
			Sheet sheet = workbook.getSheet(0);
			//获取总行数
			int rows = sheet.getRows();
			for (int i = 0; i < rows; i++) {
				Vip vip = new Vip();
				vip.setName(sheet.getCell(0, i).getContents());
				vip.setTel(sheet.getCell(1, i).getContents());
				vip.setEmail(sheet.getCell(2, i).getContents());
				vipService.insert(vip);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult("导入错误");
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return new JsonResult();
	}

}

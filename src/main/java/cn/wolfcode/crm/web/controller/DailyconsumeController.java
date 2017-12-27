package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Consumecatalog;
import cn.wolfcode.crm.domain.Dailyconsume;
import cn.wolfcode.crm.query.ConsumeQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDailyconsumeService;
import cn.wolfcode.crm.service.IEmployeeService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("dailyconsume")
public class DailyconsumeController {
    @Autowired
    private IDailyconsumeService dailyconsumeService;
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("list")
    @RequiresPermissions("dailyconsume:list")
    @PermissionName("日常支出查看")
    public String list(@ModelAttribute("qo") QueryObject queryObject) {
        return "dailyconsume";
    }

    @RequestMapping("view")
    @RequiresPermissions("dailyconsume:view")
    @PermissionName("日常支出列表")
    @ResponseBody
    public PageResult view(@ModelAttribute("qo") ConsumeQueryObject queryObject) {
        PageResult result = dailyconsumeService.query(queryObject);
        System.err.println(result.getRows());
        return result;
    }

    @RequestMapping("selectAll")
    @RequiresPermissions("dailyconsume:selectAll")
    @PermissionName("日常支出列表")
    @ResponseBody
    public List<Dailyconsume> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
        List<Dailyconsume> dailyconsumes = dailyconsumeService.selectAll();
        return dailyconsumes;
    }

    @RequestMapping("saveOrUpdate")
    @RequiresPermissions("dailyconsume:saveOrUpdate")
    @PermissionName("日常支出保存或更新")
    @ResponseBody
    public JsonResult saveOrUpdate(Dailyconsume dailyconsume) {
        try {

            if (dailyconsume.getId() != null) {
                dailyconsumeService.updateByPrimaryKey(dailyconsume);
            } else {
                dailyconsumeService.insert(dailyconsume);
            }
        } catch (Exception e) {
            return new JsonResult("操作失败");
        }
        return new JsonResult();
    }

    @RequestMapping("delete")
    @PermissionName("日常支出删除")
    @RequiresPermissions("dailyconsume:delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        try {
            if (id != null) {
                dailyconsumeService.deleteByPrimaryKey(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("删除失败");
        }
        return new JsonResult();
    }

    @RequestMapping("downloadTemplate")
    @PermissionName("日常支出模板下载")
    @RequiresPermissions("dailyconsume:downloadConsumeTemplate")
    public void downloadTemplate(HttpServletResponse response) throws Exception {

        //文件下载响应头
        response.setHeader("Content-Disposition", "attachment;filename=template.xls");
        //创建xls文件
        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
        //创建工作簿
        WritableSheet sheet = workbook.createSheet("template01", 0);
        //添加标题
        sheet.addCell(new Label(0, 0, "支出名称"));
        sheet.addCell(new Label(1, 0, "支出金额"));
        sheet.addCell(new Label(2, 0, "支出日期"));
        sheet.addCell(new Label(3, 0, "支出备注"));
        sheet.addCell(new Label(4, 0, "员工id"));
        sheet.addCell(new Label(5, 0, "支出编码"));
        //添加例子
        sheet.addCell(new Label(0, 1, "XXXX支出"));
        sheet.addCell(new Label(1, 1, "9999"));
        sheet.addCell(new Label(2, 1, "1999-12-12"));
        sheet.addCell(new Label(3, 1, "111"));
        sheet.addCell(new Label(4, 1, "1"));
        sheet.addCell(new Label(5, 1, "67"));

        //写入数据
        workbook.write();
        //关闭资源
        workbook.close();

    }

    @RequestMapping("importXls")
    @PermissionName("日常支出数据导入")
    @RequiresPermissions("dailyconsume:importXls")
    @ResponseBody
    public JsonResult importXls(MultipartFile file) throws Exception {
        try {
            //读取xls文件
            Workbook workbook = Workbook.getWorkbook(file.getInputStream());
            //读取摸个工作簿
            Sheet sheet = workbook.getSheet(0);
            //获取总行数
            int rows = sheet.getRows();
            //循环遍历

            for (int i = 1; i < rows; i++) {
                Dailyconsume dailyconsume = new Dailyconsume();
                dailyconsume.setConsumecatalog(new Consumecatalog(Long.valueOf(sheet.getCell(5, i).getContents()), null, null, null));
                dailyconsume.setAmount(new BigDecimal(sheet.getCell(1, i).getContents()));
                //保存日期
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-DD-mm");
                try {
                    dailyconsume.setDate(sdf.parse(sheet.getCell(2, i).getContents()));
                } catch (ParseException pe) {
                    return new JsonResult("日期格式转换异常,请检查日期格式!");
                }
                dailyconsume.setMemo(sheet.getCell(3, i).getContents());
                dailyconsume.setConsumer(employeeService.selectByPrimaryKey(Long.getLong(sheet.getCell(4, i).getContents())));
                dailyconsumeService.insert(dailyconsume);
            }

            //返回JsonResult
            return new JsonResult();

        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("文件上传失败!");
        }
    }

}

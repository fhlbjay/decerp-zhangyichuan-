package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.util.JsonResult;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
  private   IEmployeeService employeeService;
    @Autowired
	private IDepartmentService departmentService;
    @RequestMapping("view")
    @RequiresPermissions("employee:view")
    @PermissionName("员工列表")
    @ResponseBody
    public PageResult view(@ModelAttribute("qo") QueryObject queryObject) {
        //使用if-else方式做权限判断
        Subject subject = SecurityUtils.getSubject();
        //在任意地方获取员工对象
        System.out.println(subject.getPrincipal());
        if (subject.hasRole("admin")) {
            System.out.println("有admin角色");
        } else {
            System.out.println("没有admin角色");
        }
        PageResult result = employeeService.query(queryObject);
        return result;
    }

    @RequestMapping("list")
    @PermissionName("员工查看")
    @RequiresPermissions("employee:list")
    public String list(@ModelAttribute("qo") QueryObject queryObject) {
        return "employee";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Employee employee) {
        try {

            if (employee.getId() != null) {
                employeeService.updateByPrimaryKey(employee);
            } else {
                employeeService.insert(employee);
            }
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(e.getMessage());
        }
    }

    @RequestMapping("changeState")
    @PermissionName("更新员工状态")
    @RequiresPermissions("employee:changeState")
    @ResponseBody
    public JsonResult changeState(Long id) {
        employeeService.changeState(id);
        return new JsonResult();
    }

    @RequestMapping("getRoleIdByemployeeId")
    @RequiresPermissions("department:getRoleIdByemployeeId")
    @PermissionName("员工id查询角色id")
    @ResponseBody
    public List<Long> getRoleIdByemployeeId(Long id) {
        return employeeService.getRoleIdByemployeeId(id);

    }

    @RequestMapping("delete")
    @RequiresPermissions("department:delete")
    @PermissionName("员工删除")
    @ResponseBody
    public JsonResult delete(Long id) {
        try {
            if (id != null) {
                employeeService.deleteByPrimaryKey(id);
            }
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("删除失败");
        }
     }
	@RequestMapping("exportXls")
	@RequiresPermissions("employee:exportXls")
	public void export(HttpServletResponse response,QueryObject queryObject) throws Exception {
		//文件下载响应头
		response.setHeader("Content-Disposition", "attachment;filename=employee.xls");
		//创建xls文件
		WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
		//创建工作簿
		WritableSheet sheet = workbook.createSheet("employee", 0);

		//添加标题
		sheet.addCell(new Label(0,0,"姓名"));
		sheet.addCell(new Label(1,0,"真实姓名"));
		sheet.addCell(new Label(2,0,"电话"));
		sheet.addCell(new Label(3,0,"邮箱"));
		sheet.addCell(new Label(4,0,"入职日期"));
		sheet.addCell(new Label(5,0,"状态"));
		sheet.addCell(new Label(6,0,"部门"));
		sheet.addCell(new Label(7,0,"管理员"));
		//获取真实的员工数据
		List<Employee> employees = (List<Employee>) employeeService.query(queryObject).getRows();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0,j=1; i < employees.size(); i++,j++) {
			Employee employee = employees.get(i);
			//往工作簿中添加内容
			sheet.addCell(new Label(0,j,employee.getUsername()==null?"null":employee.getUsername()));
			sheet.addCell(new Label(1,j,employee.getRealname()==null?"null":employee.getRealname()));
			sheet.addCell(new Label(2,j,employee.getTel()==null?"null":employee.getTel()));
			sheet.addCell(new Label(3,j,employee.getEmail()==null?"null":employee.getEmail()));
			if (employee.getInputtime()!=null){
				String InputTime=sdf.format(employee.getInputtime());
				sheet.addCell(new Label(4,j,InputTime==null?null:InputTime));
			}else {
				sheet.addCell(new Label(4,j,null));
			}
			if (employee.isState()){

				sheet.addCell(new Label(5,j,"在职"));
			}else{
				sheet.addCell(new Label(5,j,"离职"));

			}

			sheet.addCell(new Label(6,j,employee.getDepartment()==null?null:employee.getDepartment().getName()));
			if (employee.getAdmin()!=null){
				sheet.addCell(new Label(7,j,"是"));
			}else{
				sheet.addCell(new Label(7,j,"否"));
			}
		}

		//写入数据
		workbook.write();
		//关闭资源
		workbook.close();
	}
	/*@RequestMapping("importXls")
	@ResponseBody
	@RequiresPermissions("employee:importXls")
	public JsonResult importXls(MultipartFile file) throws Exception {
		//读取xls文件
		Workbook workbook = Workbook.getWorkbook(file.getInputStream());
		//读取某个工作簿
		Sheet sheet = workbook.getSheet(0);
		//获取总行数
		int rows = sheet.getRows();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 1; i < rows; i++) {
			Employee employee = new Employee();
			employee.setUsername(sheet.getCell(0,i).getContents());
			employee.setRealname(sheet.getCell(1,i).getContents());
			employee.setTel(sheet.getCell(2,i).getContents());
			employee.setEmail(sheet.getCell(3,i).getContents());
			if (sheet.getCell(4,i).getContents()!=null && !sheet.getCell(4,i).getContents().equals("")){
				System.out.println(sheet.getCell(4,i).getContents());
				employee.setInputtime(sdf.parse(sheet.getCell(4,i).getContents()));
			}
			if (sheet.getCell(5,i).getContents().equals("是")){
				employee.setState(true);
			}else {
				employee.setState(false);
			}
			Department department = departmentService.selectDept(sheet.getCell(6, i).getContents());
			employee.setDepartment(department);
			if (sheet.getCell(7,i).getContents().equals("是")){
				employee.setAdmin(true);
			}else {
				employee.setAdmin(false);
			}
			employeeService.insert(employee);
		}

		workbook.close();
		return new JsonResult();
	}*/
}

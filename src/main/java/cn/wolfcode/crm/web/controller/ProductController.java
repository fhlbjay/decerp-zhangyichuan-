package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.*;
import cn.wolfcode.crm.mapper.OrderbillItemMapper;
import cn.wolfcode.crm.query.MyProductQueryObject;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IDirnameService;
import cn.wolfcode.crm.service.ILinkageMenuService;
import cn.wolfcode.crm.service.IProductService;
import cn.wolfcode.crm.service.IUnitService;
import cn.wolfcode.crm.util.JsonResult;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("product")
public class ProductController {
	@Autowired
	private IProductService productService;
	@Autowired
	private ILinkageMenuService linkageMenuService;
	@Autowired
	private IDirnameService dirnameService;
	@Autowired
	private IUnitService unitService;
	@Autowired
	private OrderbillItemMapper mapper;
	@RequestMapping("list")
	@RequiresPermissions("product:list")
	@PermissionName("商品查看")
	public String list(Model model,@ModelAttribute("qo") QueryObject queryObject) {
		String selectProductAmount = productService.selectProductAmount();
		model.addAttribute("selectProductAmount",selectProductAmount);
		int productAmount = productService.ProductAmount();
		model.addAttribute("productAmount",productAmount);
		List<Map<String, Object>> maps = mapper.selectTop();
		model.addAttribute("maps",maps);
		BigDecimal sumAmount = productService.sumAmount();
		model.addAttribute("sumAmount",sumAmount);
		return "product/product";
	}

	@RequestMapping("view")
	@RequiresPermissions("product:view")
	@PermissionName("商品视图")
	@ResponseBody
	public PageResult view(MyProductQueryObject queryObject) {
			System.out.println(queryObject.getKeyword());
			System.out.println(queryObject.getRootId());
			System.out.println(queryObject.getParentId());
		PageResult result = productService.query(queryObject);
		return result;
	}

	@RequestMapping("selectAll")
	@RequiresPermissions("product:selectAll")
	@PermissionName("商品列表")
	@ResponseBody
	public List<Product> selectAll(@ModelAttribute("qo") QueryObject queryObject) {
		List<Product> products = productService.selectAll();
		return products;
	}

	@RequestMapping("saveOrUpdate")
	@RequiresPermissions("product:saveOrUpdate")
	@PermissionName("商品保存或更新")
	@ResponseBody
	public JsonResult saveOrUpdate(Product product) {
		try {
			if (product.getId() != null) {
				productService.updateByPrimaryKey(product);
			} else {
				productService.insert(product);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("操作失败");
	}

	@RequestMapping("delete")
	@PermissionName("商品删除")
	@RequiresPermissions("product:delete")
	@ResponseBody
	public JsonResult delete(Long id) {
		try {
			if (id != null) {
				productService.deleteByPrimaryKey(id);
			}
			return new JsonResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult("删除失败");
	}
	@RequestMapping("changeState")
	@PermissionName("更新商品状态")
	@RequiresPermissions("product:changeState")
	@ResponseBody
	public JsonResult changeState(Long id) {
		productService.changeState(id);
		return new JsonResult();
	}
	@RequestMapping("exportXls")
	@RequiresPermissions("product:exportXls")
	public void export(HttpServletResponse response,MyProductQueryObject queryObject) throws Exception {
		//文件下载响应头
		response.setHeader("Content-Disposition", "attachment;filename=product.xls");
		//创建xls文件
		WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
		//创建工作簿
		WritableSheet sheet = workbook.createSheet("product", 0);
		//添加标题
		sheet.addCell(new Label(0, 0, "商品名称"));
		sheet.addCell(new Label(1, 0, "商品编码"));
		sheet.addCell(new Label(2, 0, "一级类型"));
		sheet.addCell(new Label(3, 0, "二级类型"));
		sheet.addCell(new Label(4, 0, "进价"));
		sheet.addCell(new Label(5, 0, "售价"));
		sheet.addCell(new Label(6, 0, "单位"));
		sheet.addCell(new Label(7, 0, "库存数量"));
		sheet.addCell(new Label(8, 0, "入库时间"));
		sheet.addCell(new Label(9, 0, "商品状态"));
		//获取真实的员工数据
		List<Product> products = (List<Product>) productService.query(queryObject).getRows();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0, j = 1; i < products.size(); i++, j++) {
			Product product = products.get(i);
			//往工作簿中添加内容
			sheet.addCell(new Label(0, j, product.getName() == null ? "null" : product.getName()));
			sheet.addCell(new Label(1, j, product.getSn() == null ? "null" : product.getSn()));
			sheet.addCell(new Label(2, j, product.getRoot() == null ? null : product.getRoot().getName()));
			sheet.addCell(new Label(3, j, product.getParent() == null ? "null" : product.getParent().getName()));
			sheet.addCell(new Label(4, j, product.getCostPrice() == null ? null : product.getCostPrice().toString()));
			sheet.addCell(new Label(5, j, product.getSalePrice() == null ? null : product.getSalePrice().toString()));
			sheet.addCell(new Label(6, j, product.getUnit() == null ? null : product.getUnit().getName()));
			sheet.addCell(new Label(7, j, product.getStockQuantity() == null ? null : product.getStockQuantity().toString()));
			if (product.getInputTime() != null) {
				String InputTime = sdf.format(product.getInputTime());
				sheet.addCell(new Label(8, j, InputTime == null ? null : InputTime));
			} else {
				sheet.addCell(new Label(8, j, null));
			}
			if(product.isState()){
				sheet.addCell(new Label(9, j, ""));
			}else {
				sheet.addCell(new Label(9, j, "已下架"));
			}
		}
		//写入数据
		workbook.write();

		//关闭资源
		workbook.close();
	}

	@RequestMapping("importXls")
	@PermissionName("商品文件上传")
	@ResponseBody
	@RequiresPermissions("product:importXls")
	public JsonResult importXls(MultipartFile file) throws Exception {
		//读取xls文件
		Workbook workbook = Workbook.getWorkbook(file.getInputStream());
		//读取某个工作簿
		Sheet sheet = workbook.getSheet(0);
		//获取总行数
		int rows = sheet.getRows();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 1; i < rows; i++) {
			Product product = new Product();
			product.setName(sheet.getCell(0, i).getContents());
			product.setSn(sheet.getCell(1, i).getContents());
			LinkageMenu root = linkageMenuService.seleRoot(sheet.getCell(2, i).getContents());
			product.setRoot(root);
			Dirname dirname = dirnameService.selectParentByName(sheet.getCell(3, i).getContents());
			product.setParent(dirname);
			product.setCostPrice(new BigDecimal(sheet.getCell(4, i).getContents()));
			product.setSalePrice(new BigDecimal(sheet.getCell(5, i).getContents()));
			Unit unit = unitService.selectUnitByName(sheet.getCell(6, i).getContents());
			product.setUnit(unit);
			product.setStockQuantity(new BigDecimal(sheet.getCell(7, i).getContents()));
			if (sheet.getCell(8, i).getContents() != null && !sheet.getCell(8, i).getContents().equals("")) {
				product.setInputTime(sdf.parse(sheet.getCell(8, i).getContents()));
			}
			if(sheet.getCell(9,i).getContents().equals("")){
			product.setState(true);
			}else {
			product.setState(false);
			}
			productService.insert(product);
		}

		workbook.close();
		return new JsonResult();
	}

}

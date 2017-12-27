package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyProductQueryObject  {
	//当前页
	private Integer page=1;
	//每页显示记录数
	private Integer rows=10;
	private String keyword;//高级查询商品名称和编码
	private Integer rootId;
	private Integer parentId;
	public int  getBeginIndex(){
		return (page-1)*rows;
	}

}

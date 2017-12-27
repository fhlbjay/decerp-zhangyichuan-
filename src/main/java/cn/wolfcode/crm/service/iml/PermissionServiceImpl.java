package cn.wolfcode.crm.service.iml;

import cn.wolfcode.crm.annotion.PermissionName;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.query.PageResult;
import cn.wolfcode.crm.query.QueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    PermissionMapper mapper;
    @Autowired
    ApplicationContext ctx;
    @Override
    public int deleteByPrimaryKey(Long id) {
        mapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public int insert(Permission record) {
        mapper.insert(record);
        return 0;
    }

    @Override
    public Permission selectByPrimaryKey(Long id) {
        Permission permission = mapper.selectByPrimaryKey(id);
        return permission;
    }

    @Override
    public List<Permission> selectAll() {
        List<Permission> list = mapper.selectAll();
        return list;
    }

    @Override
    public int updateByPrimaryKey(Permission record) {
        mapper.updateByPrimaryKey(record);
        return 0;
    }

    @Override
    public PageResult query(QueryObject qo) {

        int count = mapper.queryCount(qo);
        if(count>0){
            return new PageResult(count,mapper.queryList(qo));
        }
        return new PageResult(count, Collections.EMPTY_LIST);
    }

    @Override
    public List<Permission> selectPermissionByRoleId(Long id) {
        return  mapper.selectPermissionByRoleId(id);

    }

    @Override
    public void loadPermission() {
        //查询出所有权限表达式
        List<String> expressions= mapper.selectAllExpression();
        //先从mvc容器中拿到所有的controller
        Map<String, Object> beans = ctx.getBeansWithAnnotation(Controller.class);
        Collection<Object> controllers = beans.values();
        //遍历controllers集合 拿到每一个controller
        for (Object controller : controllers) {
            //通过反射拿到每一个controller的所有方法
            // System.out.println(controller.getClass().getSuperclass());
            Method[] methods = controller.getClass().getSuperclass().getDeclaredMethods();
            //遍历所有方法 查看是否贴有权限标签
            for (Method method : methods) {
                RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
                if (annotation != null) {//拥有权限标签
                    //拿到权限表达式
                    String[] value = annotation.value();
                    String expression = value[0];
                    if(!expressions.contains(expression)){
                        //拿到权限名称
                        PermissionName annotation1 = method.getAnnotation(PermissionName.class);
                        String permissionName = annotation1.value();
                        //创建权限对象,设置其值并保存
                        Permission permission = new Permission();
                        permission.setExpression(expression);
                        permission.setName(permissionName);
                        mapper.insert(permission);
                    }
                }
            }
        }

    }

    @Override
    public List<String> selectPermissionByEmployeeId(Long id) {
        return mapper.selectPermissionByEmployeeId(id);
    }
}

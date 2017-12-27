package cn.wolfcode.crm.realm;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.service.IRoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IPermissionService permissionService;
    /**
     * 复写认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       //获取用户输入的用户名
        String principal = (String) authenticationToken.getPrincipal();
        //查询数据库是否存在该用户
        Employee employee = employeeService.getEmployeeByUsername(principal);
        if(employee==null){
            return null;
        }
        //如果不为空,就拿我们返回的对象中的正确的凭证,使用凭证匹配器来做密码匹配
        //(身份信息,正确的凭证,盐,realm的名字)
        return new SimpleAuthenticationInfo(employee,employee.getPassword(), ByteSource.Util.bytes(principal),this.getName());
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权信息的对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //当前登录用户
        Employee employee = (Employee)principalCollection.getPrimaryPrincipal();

        //判断如果是管理员
        if(employee.getAdmin()!=null && employee.getAdmin()){
            //角色集合
            info.addRoles(Arrays.asList("admin"));
            //权限集合
            info.addStringPermissions(Arrays.asList("*:*"));
        }else {
            //从数据库查询出来的,当前登录用户的权限信息
            //查询当前登录用户拥有的角色名称集合
            List<String> roles = roleService.selectRoleByEmployeeId(employee.getId());
            info.addRoles(roles);
            //查询当前登录用户拥有的权限名称集合
            List<String> permissions = permissionService.selectPermissionByEmployeeId(employee.getId());
            info.addStringPermissions(permissions);
        }

        return info;
    }

}

package cn.wolfcode.crm.util;

import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.domain.Permission;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */
public class MenuUtil {

    /**
     * 对菜单进行权限过滤
     * @param menus
     * @return
     */
    public static List<Menu> checkPermssion(List<Menu> menus){
        Subject subject = SecurityUtils.getSubject();
        Iterator<Menu> iterator = menus.iterator();
        while(iterator.hasNext()){
            Menu menu = iterator.next();
            //如果菜单有关联权限,则需要进行权限验证,如果没有关联权限,代表所有人都能看
            Permission permission = menu.getPermission();
            if(permission!=null){
                //如果当前登录用户没有权限查看的菜单,那么就从集合中移除掉
                if(!subject.isPermitted(permission.getExpression())){
                    iterator.remove();
                    continue;
                }
            }
            //如果有子菜单,就需要对子菜单也进行权限过滤
            if(menu.getChildren().size()>0){
                checkPermssion(menu.getChildren());
            }
        }
        return menus;
    }
}

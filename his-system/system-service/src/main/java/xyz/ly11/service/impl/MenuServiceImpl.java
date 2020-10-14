package xyz.ly11.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.SimpleUser;
import xyz.ly11.mapper.MenuMapper;
import xyz.ly11.domain.Menu;
import xyz.ly11.service.MenuService;
/**
 * @author by 29794
 * @date 2020/10/9 2:07
 */
@Service
public class MenuServiceImpl implements MenuService{

    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<Menu> selectMenuTree(boolean isAdmin, SimpleUser simpleUser) {
        QueryWrapper<Menu> qw=new QueryWrapper<>();
        qw.eq(Menu.COL_STATUS, Constants.STATUS_TRUE);
        qw.in(Menu.COL_MENU_TYPE,Constants.MENU_TYPE_M,Constants.MENU_TYPE_C);
        qw.orderByAsc(Menu.COL_PARENT_ID);
        if(isAdmin){
            return menuMapper.selectList(qw);
        }else{
            //根据用户id查询用户拥有的菜单信息
            return menuMapper.selectList(qw);
        }
    }


}

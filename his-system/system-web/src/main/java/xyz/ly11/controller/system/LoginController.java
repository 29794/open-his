package xyz.ly11.controller.system;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.ly11.constants.Constants;
import xyz.ly11.constants.HttpStatus;
import xyz.ly11.domain.Menu;
import xyz.ly11.domain.SimpleUser;
import xyz.ly11.dto.LoginBodyDTO;
import xyz.ly11.service.MenuService;
import xyz.ly11.vo.ActiveUser;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.MenuTreeVO;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by 29794
 * @date 2020/10/9 20:49
 */
@Slf4j
@RestController
public class LoginController {

    final
    MenuService menuService;

    public LoginController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 用户登录
     *
     * @param loginBodyDTO
     * @param request
     * @return
     */
    @PostMapping("/login/doLogin")
    public AjaxResult login(@RequestBody @Validated LoginBodyDTO loginBodyDTO, HttpServletRequest request) {
        AjaxResult ajax = AjaxResult.success();
        String username = loginBodyDTO.getUsername();
        String password = loginBodyDTO.getPassword();
        //构造用户名和密码的token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            //得到会话的token==也就是redis里面存的
            Serializable webToken = subject.getSession().getId();
            ajax.put(Constants.TOKEN, webToken);
        } catch (AuthenticationException e) {
            log.error("用户名或密码不正确", e);
            ajax = AjaxResult.error(HttpStatus.ERROR, "用户名或密码不正确");
        }
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/login/getInfo")
    public AjaxResult getInfo() {
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("username", activeUser.getUser().getUserName());
        ajax.put("picture", activeUser.getUser().getPicture());
        ajax.put("roles", activeUser.getRoles());
        ajax.put("permissions", activeUser.getPermissions());
        return ajax;
    }

    /**
     * 用户退出
     */
    @PostMapping("/login/logout")
    public AjaxResult logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return AjaxResult.success("用户退出成功");
    }

    /**
     * 获取应该显示的菜单信息
     *
     * @return 菜单信息
     */
    @GetMapping("/login/getMenus")
    public AjaxResult getMenus() {
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        boolean isAdmin = activeUser.getUser().getUserType().equals(Constants.USER_ADMIN);
        SimpleUser simpleUser = null;
        if (!isAdmin) {
            simpleUser = new SimpleUser(activeUser.getUser().getUserId(), activeUser.getUser().getUserName());
        }
        List<Menu> menus = menuService.selectMenuTree(isAdmin, simpleUser);
        List<MenuTreeVO> menuVos = new ArrayList<>();
        for (Menu menu : menus) {
            menuVos.add(new MenuTreeVO(menu.getMenuId().toString(), menu.getPath()));
        }
        return AjaxResult.success(menuVos);
    }

}

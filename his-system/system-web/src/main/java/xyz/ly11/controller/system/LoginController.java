package xyz.ly11.controller.system;

import cn.hutool.core.date.DateUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.ly11.aspect.annotation.Log;
import xyz.ly11.aspect.enums.BusinessType;
import xyz.ly11.constants.Constants;
import xyz.ly11.constants.HttpStatus;
import xyz.ly11.domain.LoginInfo;
import xyz.ly11.domain.Menu;
import xyz.ly11.domain.SimpleUser;
import xyz.ly11.dto.LoginBodyDTO;
import xyz.ly11.service.MenuService;
import xyz.ly11.service.impl.LoginInfoServiceImpl;
import xyz.ly11.utils.AddressUtils;
import xyz.ly11.utils.IpUtils;
import xyz.ly11.utils.ShiroSecurityUtils;
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

    final LoginInfoServiceImpl loginInfoService;

    public LoginController(MenuService menuService, LoginInfoServiceImpl loginInfoService) {
        this.menuService = menuService;
        this.loginInfoService = loginInfoService;
    }

    /**
     * 用户登录
     *
     * @param loginBodyDTO 登录信息
     * @param request      请求
     * @return 登录结果
     */
    @PostMapping("/login/doLogin")
    @Log(title = "用户登录", businessType = BusinessType.OTHER)
    public AjaxResult login(@RequestBody @Validated LoginBodyDTO loginBodyDTO, HttpServletRequest request) {
        AjaxResult ajax = AjaxResult.success();
        String username = loginBodyDTO.getUsername();
        String password = loginBodyDTO.getPassword();
        //构造用户名和密码的token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登陆信息
        LoginInfo loginInfo = createLoginInfo(request);
        loginInfo.setLoginAccount(loginBodyDTO.getUsername());
        try {
            subject.login(token);
            //得到会话的token==也就是redis里面存的
            Serializable webToken = subject.getSession().getId();
            ajax.put(Constants.TOKEN, webToken);
            loginInfo.setLoginStatus(Constants.LOGIN_SUCCESS);
            loginInfo.setUserName(ShiroSecurityUtils.getCurrentUserName());
            loginInfo.setMsg("登陆成功");
        } catch (Exception e) {
            log.error("用户名或密码不正确", e);
            ajax = AjaxResult.error(HttpStatus.ERROR, "用户名或密码不正确");
            loginInfo.setLoginStatus(Constants.LOGIN_ERROR);
            loginInfo.setMsg("用户名或密码不正确");
        }
        loginInfoService.insertLoginInfo(loginInfo);
        return ajax;

    }

    /**
     * 得到用户的登陆信息
     *
     * @param request 登录请求
     * @return 掉路日志
     */
    private LoginInfo createLoginInfo(HttpServletRequest request) {
        LoginInfo loginInfo = new LoginInfo();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(request);
        String address = AddressUtils.getRealAddressByIP(ip);
        loginInfo.setIpAddr(ip);
        loginInfo.setLoginLocation(address);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        loginInfo.setOs(os);
        loginInfo.setBrowser(browser);
        loginInfo.setLoginTime(DateUtil.date());
        loginInfo.setLoginType(Constants.LOGIN_TYPE_SYSTEM);
        return loginInfo;
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
    @Log(title = "用户退出", businessType = BusinessType.OTHER)
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
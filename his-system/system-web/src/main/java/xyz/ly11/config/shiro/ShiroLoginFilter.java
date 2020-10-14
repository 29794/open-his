package xyz.ly11.config.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import xyz.ly11.constants.HttpStatus;
import xyz.ly11.vo.AjaxResult;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author by 29794
 * @date 2020/10/9 20:03
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {
    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     * @param request 请求
     * @param response 相应
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception 异常
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        AjaxResult ajaxResult=AjaxResult.fail();
        ajaxResult.put("code", HttpStatus.UNAUTHORIZED);
        ajaxResult.put("msg", "登录认证失效，请重新登录!");
        httpServletResponse.getWriter().write(JSON.toJSON(ajaxResult).toString());
        return false;
    }
}


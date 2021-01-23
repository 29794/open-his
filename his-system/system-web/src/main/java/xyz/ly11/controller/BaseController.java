package xyz.ly11.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import xyz.ly11.vo.AjaxResult;

/**
 * @author 29794
 * @date 1/22/2021 23:49
 */
@DefaultProperties(defaultFallback = "fallback")
public class BaseController {
    /**
     * 如远程服务不可用，或者出现异常，回调的方法
     *
     * @return 响应
     */
    public AjaxResult fallback() {
        return AjaxResult.toAjax(-1);
    }

}
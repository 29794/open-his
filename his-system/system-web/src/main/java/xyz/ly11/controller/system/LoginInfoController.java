package xyz.ly11.controller.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.dto.LoginInfoDTO;
import xyz.ly11.service.LoginInfoService;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

/**
 * @author 29794
 * @date 12/13/2020 19:13
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("system/loginInfo")
public class LoginInfoController {

    final
    private LoginInfoService loginInfoService;

    /**
     * 分页查询
     */
    @GetMapping("listForPage")
    public AjaxResult listForPage(LoginInfoDTO loginInfoDTO) {
        DataGridView gridView = loginInfoService.listForPage(loginInfoDTO);
        return AjaxResult.success("查询成功", gridView.getData(), gridView.getTotal());
    }

    /**
     * 删除
     */
    @DeleteMapping("deleteLoginInfoByIds/{infoIds}")
    public AjaxResult deleteLoginInfoByIds(@PathVariable Long[] infoIds) {
        return AjaxResult.toAjax(this.loginInfoService.deleteLoginInfoByIds(infoIds));
    }

    /**
     * 清空删除
     */
    @DeleteMapping("clearLoginInfo")
    public AjaxResult clearLoginInfo() {
           return AjaxResult.toAjax(this.loginInfoService.clearLoginInfo());
    }
}

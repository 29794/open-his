package xyz.ly11.controller.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.dto.OperLogDTO;
import xyz.ly11.service.OperLogService;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

/**
 * @author 29794
 * @date 11/25/2020 21:51
 */
@Slf4j
@RestController
@RequestMapping("/system/operLog")
public class OperLogController {

    final
    OperLogService operLogService;

    public OperLogController(OperLogService operLogService) {
        this.operLogService = operLogService;
    }

    /**
     * 分页查询
     */
    @GetMapping("listForPage")
    public AjaxResult listForPage(OperLogDTO operLogDTO) {
        DataGridView gridView = this.operLogService.listForPage(operLogDTO);
        return AjaxResult.success("查询成功", gridView.getData(), gridView.getTotal());
    }

    /**
     * 删除
     */
    @DeleteMapping("deleteOperLogByIds/{infoIds}")
    public AjaxResult deleteOperLogByIds(@PathVariable Long[] infoIds) {

        return AjaxResult.toAjax(this.operLogService.deleteOperLogByIds(infoIds));
    }

    /**
     * 清空删除
     */
    @DeleteMapping("clearAllOperLog")
    public AjaxResult clearAllOperLog() {
        return AjaxResult.toAjax(this.operLogService.clearAllOperLog());
    }


}

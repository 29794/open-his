package xyz.ly11.controller.system;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.ly11.aspect.annotation.Log;
import xyz.ly11.aspect.enums.BusinessType;
import xyz.ly11.dto.UserDTO;
import xyz.ly11.service.UserService;
import xyz.ly11.utils.ShiroSecurityUtils;
import xyz.ly11.vo.AjaxResult;
import xyz.ly11.vo.DataGridView;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 29794
 * @date 1/19/2021 19:59
 * 用户接口
 */
@Slf4j
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    /**
     * 分页查询
     *
     * @param userDTO 分页数据
     * @return 查询结果
     */
    @GetMapping("/listUserForPage")
    public AjaxResult listUserForPage(UserDTO userDTO) {
        DataGridView dataGridView = this.userService.listUserForPage(userDTO);
        return AjaxResult.success("查询成功", dataGridView.getData(), dataGridView.getTotal());
    }

    /**
     * 新增用户
     *
     * @param userDTO 用户数据
     * @return 新增结果
     */
    @PostMapping("/addUser")
    @Log(title = "添加用户", businessType = BusinessType.INSERT)
    public AjaxResult addUser(@Validated UserDTO userDTO) {
        userDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.userService.addUser(userDTO));
    }

    @PostMapping("/updateUser")
    @Log(title = "修改用户", businessType = BusinessType.UPDATE)
    public AjaxResult updateUser(UserDTO userDTO) {
        userDTO.setSimpleUser(ShiroSecurityUtils.getCurrentSimpleUser());
        return AjaxResult.toAjax(this.userService.updateUser(userDTO));
    }

    /**
     * 根据用户id查询用户数据
     *
     * @param userId 用户的 id
     * @return 查询的结果
     */
    @GetMapping("/getUserById/{userId:\\d+}")
    public AjaxResult getUserById(@PathVariable @Validated @NotNull(message = "用户id不能为空") Long userId) {
        return AjaxResult.success(this.userService.getOne(userId));
    }

    /**
     * 根据崖胡的id删除用户
     *
     * @param userIds 用户的id中
     * @return 删除用户的结果
     */
    @Log(title = "删除用户", businessType = BusinessType.DELETE)
    @GetMapping("/deleteUserByIds/{userIds:\\d+}")
    public AjaxResult deleteUserByIds(@PathVariable @Validated @NotEmpty(message = "需要删除的用户id不为空") Long[] userIds) {
        return AjaxResult.toAjax(this.userService.deleteUserByIds(userIds));
    }

    /**
     * 查询所有可用的用户
     *
     * @return 查询结果
     */
    @GetMapping("/selectAllUser")
    public AjaxResult selectAllUser() {
        return AjaxResult.success(this.userService.getAllUsers());
    }

    /**
     * 重置用户的密码
     *
     * @param userIds 用户的id
     * @return 重置结果
     */
    @PostMapping("/resetPwd/{userIds:\\d+}")
    public AjaxResult resetPwd(@PathVariable Long[] userIds) {
        if (userIds.length > 0) {
            this.userService.resetPassWord(userIds);
            return AjaxResult.success("重置成功");
        }
        return AjaxResult.fail("重置失败，请选择需要重置的用户！");
    }


}
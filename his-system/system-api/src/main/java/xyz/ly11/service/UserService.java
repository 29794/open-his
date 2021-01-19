package xyz.ly11.service;

import xyz.ly11.domain.User;
import xyz.ly11.dto.UserDTO;
import xyz.ly11.vo.DataGridView;

import java.util.List;

/**
 * @author by 29794
 * @date 2020/10/9 2:00
 * 用户的业务接口
 */
public interface UserService {

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return user
     */
    User queryUserByPhone(String phone);

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户编号
     * @return user
     */
    User getOne(Long userId);

    /**
     * 分页查询用户
     *
     * @param userDTO 分页查询数据
     * @return 查询结果
     */
    DataGridView listUserForPage(UserDTO userDTO);

    /**
     * 添加用户
     *
     * @param userDTO 用户数据
     * @return 添加的结果
     */
    int addUser(UserDTO userDTO);

    /**
     * 修改用户
     *
     * @param userDTO 修改的用户数据
     * @return 修改结果
     */
    int updateUser(UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param userIds 用户id
     * @return 删除结果
     */
    int deleteUserByIds(Long[] userIds);

    /**
     * 查询所有可用的用户
     *
     * @return 查询结果
     */
    List<User> getAllUsers();

    /**
     * 重置用户密码
     *
     * @param userIds 用户id
     */
    void resetPassWord(Long[] userIds);


}
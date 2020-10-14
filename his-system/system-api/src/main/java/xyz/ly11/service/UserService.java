package xyz.ly11.service;

import xyz.ly11.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author by 29794
 * @date 2020/10/9 2:00
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

}

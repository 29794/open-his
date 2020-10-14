package xyz.ly11.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.ly11.domain.User;
import xyz.ly11.mapper.UserMapper;
import xyz.ly11.service.UserService;
/**
 * @author by 29794
 * @date 2020/10/9 2:00
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User queryUserByPhone(String phone) {
        QueryWrapper<User> qw=new QueryWrapper<>();
        qw.eq(User.COL_PHONE,phone);
        return this.userMapper.selectOne(qw);
    }

    @Override
    public User getOne(Long userId) {
        return this.userMapper.selectById(userId);
    }

}

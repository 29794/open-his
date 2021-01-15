package xyz.ly11.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.ly11.domain.LoginInfo;
import xyz.ly11.dto.LoginInfoDTO;
import xyz.ly11.mapper.LoginInfoMapper;
import xyz.ly11.service.LoginInfoService;
import xyz.ly11.vo.DataGridView;

import java.util.Arrays;
import java.util.List;

/**
 * @author 29794
 * @date 12/2/2020 23:28
 * 系统登录访问的业务实现
 */
@Service
@RequiredArgsConstructor
public class LoginInfoServiceImpl implements LoginInfoService {

    final
    private LoginInfoMapper loginInfoMapper;

    @Override
    public int insertLoginInfo(LoginInfo loginInfo) {
        return this.loginInfoMapper.insert(loginInfo);
    }

    @Override
    public DataGridView listForPage(LoginInfoDTO loginInfoDTO) {
        Page<LoginInfo> page = new Page<>(loginInfoDTO.getPageNum(), loginInfoDTO.getPageSize());
        // 实际开发中不推荐使用
        QueryWrapper<LoginInfo> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(loginInfoDTO.getUserName()), LoginInfo.COL_USER_NAME, loginInfoDTO.getUserName());
        qw.like(StringUtils.isNotBlank(loginInfoDTO.getIpAddr()), LoginInfo.COL_IP_ADDR, loginInfoDTO.getIpAddr());
        qw.like(StringUtils.isNotBlank(loginInfoDTO.getLoginAccount()), LoginInfo.COL_LOGIN_ACCOUNT, loginInfoDTO.getLoginAccount());
        qw.eq(StringUtils.isNotBlank(loginInfoDTO.getLoginStatus()), LoginInfo.COL_LOGIN_STATUS, loginInfoDTO.getLoginStatus());
        qw.eq(StringUtils.isNotBlank(loginInfoDTO.getLoginType()), LoginInfo.COL_LOGIN_TYPE, loginInfoDTO.getLoginType());
        qw.ge(null != loginInfoDTO.getBeginTime(), LoginInfo.COL_LOGIN_TIME, loginInfoDTO.getBeginTime());
        qw.le(null != loginInfoDTO.getEndTime(), LoginInfo.COL_LOGIN_TIME, loginInfoDTO.getEndTime());
        qw.orderByDesc(LoginInfo.COL_LOGIN_TIME);
        this.loginInfoMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public int deleteLoginInfoByIds(Long[] infoIds) {
        List<Long> ids = Arrays.asList(infoIds);
        if (ids.size() > 0) {
            return this.loginInfoMapper.deleteBatchIds(ids);
        }
        return 0;
    }

    @Override
    public int clearLoginInfo() {
        return this.loginInfoMapper.delete(null);
    }

}
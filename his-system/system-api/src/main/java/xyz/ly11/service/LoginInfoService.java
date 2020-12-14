package xyz.ly11.service;

import xyz.ly11.domain.LoginInfo;
import xyz.ly11.dto.LoginInfoDTO;
import xyz.ly11.vo.DataGridView;

/**
 * @author 29794
 * @date 12/2/2020 23:24
 * 登录日志的业务接口
 */
public interface LoginInfoService {

    /**
     * 添加
     *
     * @param loginInfo 登录日志的实体信息
     * @return 影响条数
     */
    int insertLoginInfo(LoginInfo loginInfo);

    /**
     * 分页查询
     *
     * @param loginInfoDTO 登录日志的数据传输对象
     * @return 页面数据
     */
    DataGridView listForPage(LoginInfoDTO loginInfoDTO);

    /**
     * 删除登陆日志
     *
     * @param infoIds 登录日志的id
     * @return 影响的行数
     */
    int deleteLoginInfoByIds(Long[] infoIds);

    /**
     * 清空登陆日志
     *
     * @return 影响的行数
     */
    int clearLoginInfo();


}


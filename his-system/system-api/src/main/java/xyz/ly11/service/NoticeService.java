package xyz.ly11.service;

import xyz.ly11.domain.Notice;
import xyz.ly11.dto.NoticeDTO;
import xyz.ly11.vo.DataGridView;

/**
 * @author 29794
 * @date 1/20/2021 21:13
 * 通知公告服务接口
 */
public interface NoticeService {

    /**
     * 分页查询
     *
     * @param noticeDTO 分页数据
     * @return 查询结果
     */
    DataGridView listNoticePage(NoticeDTO noticeDTO);

    /**
     * 根据id 查询通告
     *
     * @param noticeId 通告id
     * @return 查询结果
     */
    Notice getOne(Long noticeId);


    /**
     * 新增
     *
     * @param noticeDTO 新增通告
     * @return 新增结果
     */
    int addNotice(NoticeDTO noticeDTO);

    /**
     * 修改
     *
     * @param noticeDTO 修改通告
     * @return 修改结果
     */
    int updateNotice(NoticeDTO noticeDTO);

    /**
     * 根据id删除通过
     *
     * @param noticeIds 需要删除的通告的id
     * @return 删除结果
     */
    int deleteNoticeByIds(Long[] noticeIds);

}
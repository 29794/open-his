package xyz.ly11.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.ly11.domain.Notice;
import xyz.ly11.dto.NoticeDTO;
import xyz.ly11.mapper.NoticeMapper;
import xyz.ly11.service.NoticeService;
import xyz.ly11.vo.DataGridView;

import java.util.Arrays;
import java.util.List;

/**
 * @author 29794
 * @date 1/20/2021 21:13
 * 通过接口的具体实现
 */
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    final NoticeMapper noticeMapper;

    @Override
    public DataGridView listNoticePage(NoticeDTO noticeDTO) {
        Page<Notice> page = new Page<>(noticeDTO.getPageNum(), noticeDTO.getPageSize());
        QueryWrapper<Notice> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(noticeDTO.getNoticeTitle()), Notice.COL_NOTICE_TITLE, noticeDTO.getNoticeTitle());
        qw.like(StringUtils.isNotBlank(noticeDTO.getCreateBy()), Notice.COL_CREATE_BY, noticeDTO.getCreateBy());
        qw.eq(StringUtils.isNotBlank(noticeDTO.getNoticeType()), Notice.COL_NOTICE_TYPE, noticeDTO.getNoticeType());
        qw.eq(StringUtils.isNotBlank(noticeDTO.getStatus()), Notice.COL_STATUS, noticeDTO.getStatus());
        qw.orderByDesc(Notice.COL_CREATE_TIME);
        this.noticeMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Notice getOne(Long noticeId) {
        return this.noticeMapper.selectById(noticeId);
    }

    @Override
    public int addNotice(NoticeDTO noticeDTO) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDTO, notice);
        notice.setCreateTime(DateUtil.date());
        notice.setCreateBy(noticeDTO.getSimpleUser().getUserName());
        return this.noticeMapper.insert(notice);
    }

    @Override
    public int updateNotice(NoticeDTO noticeDTO) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDTO, notice);
        notice.setUpdateTime(DateUtil.date());
        notice.setUpdateBy(noticeDTO.getSimpleUser().getUserName());
        return this.noticeMapper.updateById(notice);
    }

    @Override
    public int deleteNoticeByIds(Long[] noticeIds) {
        List<Long> ids = Arrays.asList(noticeIds);
        if (ids.size() > 0) {
            return this.noticeMapper.deleteBatchIds(ids);
        }
        return 0;

    }
}
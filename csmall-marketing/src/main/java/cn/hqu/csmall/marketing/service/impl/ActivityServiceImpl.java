package cn.hqu.csmall.marketing.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.util.PageInfoToPageDataConverter;
import cn.hqu.csmall.marketing.mapper.ActivityMapper;
import cn.hqu.csmall.marketing.pojo.entity.Activity;
import cn.hqu.csmall.marketing.pojo.param.ActivityAddNewParam;
import cn.hqu.csmall.marketing.pojo.param.ActivityUpdateParam;
import cn.hqu.csmall.marketing.pojo.vo.ActivityListItemVO;
import cn.hqu.csmall.marketing.pojo.vo.ActivityStandardVO;
import cn.hqu.csmall.marketing.service.IActivityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j @Service
public class ActivityServiceImpl implements IActivityService {
    @Autowired private ActivityMapper mapper;

    @Override
    public void addNew(ActivityAddNewParam param) {
        QueryWrapper<Activity> qw = new QueryWrapper<>(); qw.eq("title", param.getTitle());
        if (mapper.selectCount(qw) > 0) throw new ServiceException(ServiceCode.ERROR_CONFLICT, "活动名称已存在");
        Activity a = new Activity(); BeanUtils.copyProperties(param, a);
        a.setStatus(0); a.setGmtCreate(LocalDateTime.now()); a.setGmtModified(LocalDateTime.now());
        mapper.insert(a);
    }

    @Override
    public void delete(Long id) {
        if (mapper.selectById(id) == null) throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "活动不存在");
        mapper.deleteById(id);
    }

    @Override
    public void updateById(Long id, ActivityUpdateParam param) {
        if (mapper.selectById(id) == null) throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "活动不存在");
        QueryWrapper<Activity> qw = new QueryWrapper<>(); qw.eq("title", param.getTitle()).ne("id", id);
        if (mapper.selectCount(qw) > 0) throw new ServiceException(ServiceCode.ERROR_CONFLICT, "活动名称已存在");
        Activity a = new Activity(); BeanUtils.copyProperties(param, a); a.setId(id); a.setGmtModified(LocalDateTime.now());
        mapper.updateById(a);
    }

    @Override
    public void start(Long id) {
        Activity a = mapper.selectById(id);
        if (a == null) throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "活动不存在");
        if (a.getStatus() != 0) throw new ServiceException(ServiceCode.ERROR_CONFLICT, "仅未开始的活动可以开始");
        Activity u = new Activity(); u.setId(id); u.setStatus(1); u.setStartTime(LocalDateTime.now()); u.setGmtModified(LocalDateTime.now());
        mapper.updateById(u);
    }

    @Override
    public void end(Long id) {
        Activity a = mapper.selectById(id);
        if (a == null) throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "活动不存在");
        if (a.getStatus() != 1) throw new ServiceException(ServiceCode.ERROR_CONFLICT, "仅进行中的活动可以结束");
        Activity u = new Activity(); u.setId(id); u.setStatus(2); u.setEndTime(LocalDateTime.now()); u.setGmtModified(LocalDateTime.now());
        mapper.updateById(u);
    }

    @Override
    public PageData<ActivityListItemVO> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageInfoToPageDataConverter.convert(new PageInfo<>(mapper.list()));
    }
    @Override public PageData<ActivityListItemVO> list(Integer pageNum) { return list(pageNum, 5); }

    @Override
    public PageData<ActivityListItemVO> search(String title, Long id, Integer pageNum, Integer pageSize) {
        if ((title == null || title.trim().isEmpty()) && id == null) return PageData.empty();
        PageHelper.startPage(pageNum, pageSize);
        return PageInfoToPageDataConverter.convert(new PageInfo<>(mapper.search(title, id)));
    }
    @Override public PageData<ActivityListItemVO> search(String title, Long id, Integer pageNum) { return search(title, id, pageNum, 5); }

    @Override
    public ActivityStandardVO getStandardById(Long id) {
        ActivityStandardVO vo = mapper.getStandardById(id);
        if (vo == null) throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "活动不存在");
        return vo;
    }
}

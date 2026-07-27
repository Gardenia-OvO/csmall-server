package cn.hqu.csmall.marketing.service;

import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.marketing.pojo.param.ActivityAddNewParam;
import cn.hqu.csmall.marketing.pojo.param.ActivityUpdateParam;
import cn.hqu.csmall.marketing.pojo.vo.ActivityListItemVO;
import cn.hqu.csmall.marketing.pojo.vo.ActivityStandardVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IActivityService {
    void addNew(ActivityAddNewParam param);
    void delete(Long id);
    void updateById(Long id, ActivityUpdateParam param);
    void start(Long id);
    void end(Long id);
    PageData<ActivityListItemVO> list(Integer pageNum, Integer pageSize);
    PageData<ActivityListItemVO> list(Integer pageNum);
    PageData<ActivityListItemVO> search(String title, Long id, Integer pageNum, Integer pageSize);
    PageData<ActivityListItemVO> search(String title, Long id, Integer pageNum);
    ActivityStandardVO getStandardById(Long id);
}

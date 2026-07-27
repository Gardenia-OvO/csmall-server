package cn.hqu.csmall.marketing.mapper;

import cn.hqu.csmall.marketing.pojo.entity.Activity;
import cn.hqu.csmall.marketing.pojo.vo.ActivityListItemVO;
import cn.hqu.csmall.marketing.pojo.vo.ActivityStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityMapper extends BaseMapper<Activity> {
    List<ActivityListItemVO> list();
    ActivityStandardVO getStandardById(Long id);
    List<ActivityListItemVO> search(@Param("title") String title, @Param("id") Long id);
}

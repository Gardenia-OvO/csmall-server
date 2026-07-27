package cn.hqu.csmall.merchant.mapper;

import cn.hqu.csmall.merchant.pojo.entity.Merchant;
import cn.hqu.csmall.merchant.pojo.vo.MerchantListItemVO;
import cn.hqu.csmall.merchant.pojo.vo.MerchantStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantMapper extends BaseMapper<Merchant> {

    List<MerchantListItemVO> list();

    MerchantStandardVO getStandardById(Long id);

    List<MerchantListItemVO> search(@Param("name") String name, @Param("id") Long id);
}

package cn.hqu.csmall.order.mapper;

import cn.hqu.csmall.order.pojo.entity.Order;
import cn.hqu.csmall.order.pojo.vo.OrderListItemVO;
import cn.hqu.csmall.order.pojo.vo.OrderStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderMapper extends BaseMapper<Order> {
    List<OrderListItemVO> list();
    OrderStandardVO getStandardById(Long id);
    List<OrderListItemVO> search(String orderNo, Long id);
}

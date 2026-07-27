package cn.hqu.csmall.order.service;

import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.order.pojo.param.OrderAddNewParam;
import cn.hqu.csmall.order.pojo.vo.OrderListItemVO;
import cn.hqu.csmall.order.pojo.vo.OrderStandardVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IOrderService {
    void addNew(OrderAddNewParam param);
    void delete(Long id);
    void ship(Long id);
    void complete(Long id);
    void cancel(Long id);
    PageData<OrderListItemVO> list(Integer pageNum, Integer pageSize);
    PageData<OrderListItemVO> list(Integer pageNum);
    PageData<OrderListItemVO> search(String orderNo, Long id, Integer pageNum, Integer pageSize);
    PageData<OrderListItemVO> search(String orderNo, Long id, Integer pageNum);
    OrderStandardVO getStandardById(Long id);
}

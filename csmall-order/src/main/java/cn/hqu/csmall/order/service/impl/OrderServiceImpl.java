package cn.hqu.csmall.order.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.util.PageInfoToPageDataConverter;
import cn.hqu.csmall.order.mapper.OrderMapper;
import cn.hqu.csmall.order.pojo.entity.Order;
import cn.hqu.csmall.order.pojo.param.OrderAddNewParam;
import cn.hqu.csmall.order.pojo.vo.OrderListItemVO;
import cn.hqu.csmall.order.pojo.vo.OrderStandardVO;
import cn.hqu.csmall.order.service.IOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired private OrderMapper orderMapper;

    @Override
    public void addNew(OrderAddNewParam param) {
        log.debug("开始处理【新增订单】的业务，参数为:{}", param);
        Order order = new Order();
        BeanUtils.copyProperties(param, order);
        order.setTotalAmount(param.getUnitPrice().multiply(BigDecimal.valueOf(param.getQuantity())));
        order.setOrderNo("ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + String.format("%04d", new Random().nextInt(10000)));
        order.setStatus(0); // 待付款
        order.setGmtCreate(LocalDateTime.now());
        order.setGmtModified(LocalDateTime.now());
        orderMapper.insert(order);
        log.debug("新增订单成功，订单号:{}", order.getOrderNo());
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除订单】的业务，id:{}", id);
        Order exist = orderMapper.selectById(id);
        if (exist == null) { throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "订单数据不存在"); }
        orderMapper.deleteById(id);
        log.debug("删除订单完成");
    }

    @Override
    public void ship(Long id) {
        updateStatus(id, 2, "发货", 1);
    }

    @Override
    public void complete(Long id) {
        updateStatus(id, 3, "完成", 2);
    }

    @Override
    public void cancel(Long id) {
        updateStatus(id, 4, "取消", 0);
    }

    private void updateStatus(Long id, int newStatus, String action, int expectedStatus) {
        log.debug("开始处理【{}订单】的业务，id:{}", action, id);
        Order exist = orderMapper.selectById(id);
        if (exist == null) { throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "订单数据不存在"); }
        if (exist.getStatus() != expectedStatus) {
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, "订单状态不正确，无法" + action);
        }
        Order update = new Order();
        update.setId(id); update.setStatus(newStatus); update.setGmtModified(LocalDateTime.now());
        orderMapper.updateById(update);
    }

    @Override
    public PageData<OrderListItemVO> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageInfoToPageDataConverter.convert(new PageInfo<>(orderMapper.list()));
    }

    @Override
    public PageData<OrderListItemVO> list(Integer pageNum) { return list(pageNum, 5); }

    @Override
    public PageData<OrderListItemVO> search(String orderNo, Long id, Integer pageNum, Integer pageSize) {
        if ((orderNo == null || orderNo.trim().isEmpty()) && id == null) return PageData.empty();
        PageHelper.startPage(pageNum, pageSize);
        return PageInfoToPageDataConverter.convert(new PageInfo<>(orderMapper.search(orderNo, id)));
    }

    @Override
    public PageData<OrderListItemVO> search(String orderNo, Long id, Integer pageNum) { return search(orderNo, id, pageNum, 5); }

    @Override
    public OrderStandardVO getStandardById(Long id) {
        OrderStandardVO vo = orderMapper.getStandardById(id);
        if (vo == null) throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "订单不存在");
        return vo;
    }
}

package cn.hqu.csmall.passport.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.util.PageInfoToPageDataConverter;
import cn.hqu.csmall.passport.mapper.AdminRoleMapper;
import cn.hqu.csmall.passport.mapper.RoleMapper;
import cn.hqu.csmall.passport.pojo.entity.AdminRole;
import cn.hqu.csmall.passport.pojo.entity.Role;
import cn.hqu.csmall.passport.pojo.param.RoleAddNewParam;
import cn.hqu.csmall.passport.pojo.param.RoleUpdateParam;
import cn.hqu.csmall.passport.pojo.vo.RoleListItemVO;
import cn.hqu.csmall.passport.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Transactional
    @Override
    public void addNew(RoleAddNewParam roleAddNewParam) {
        log.debug("开始处理【新增角色】的业务，参数：{}", roleAddNewParam);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", roleAddNewParam.getName());
        int count = roleMapper.selectCount(queryWrapper);
        log.debug("根据角色名称查询角色表中是否有同名角色，查询结果：{}", count);
        if (count > 0) {
            String message = "角色名称被占用，请更换名称后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleAddNewParam, role);
        // 默认启用
        if (role.getEnable() == null) {
            role.setEnable(1);
        }
        role.setGmtCreate(LocalDateTime.now());
        role.setGmtModified(LocalDateTime.now());
        log.debug("准备将新的角色数据插入数据库中，角色信息：{}", role);
        roleMapper.insert(role);
        log.debug("新的角色数据插入数据库中，完成！");
    }

    @Override
    public PageData<RoleListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询角色列表】的业务，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<RoleListItemVO> list = roleMapper.list();
        PageInfo<RoleListItemVO> pageInfo = new PageInfo<>(list);
        PageData<RoleListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【查询角色列表】的业务完成，结果：{}", pageData);
        return pageData;
    }

    @Override
    public PageData<RoleListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【查询角色列表】的业务，页码：{}，每页记录数(默认)：{}", pageNum, pageSize);
        return list(pageNum, pageSize);
    }

    @Override
    public void update(RoleUpdateParam roleUpdateParam) {
        log.debug("开始处理【修改角色】的业务，参数：{}", roleUpdateParam);
        // 检查角色是否存在
        Role existRole = roleMapper.selectById(roleUpdateParam.getId());
        if (existRole == null) {
            String message = "修改角色失败，角色数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        // 如果修改了名称，检查是否与其他角色重复
        if (roleUpdateParam.getName() != null && !roleUpdateParam.getName().isEmpty()) {
            QueryWrapper<Role> nameQuery = new QueryWrapper<>();
            nameQuery.eq("name", roleUpdateParam.getName());
            nameQuery.ne("id", roleUpdateParam.getId());
            int count = roleMapper.selectCount(nameQuery);
            if (count > 0) {
                String message = "修改角色失败，角色名称已存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }
        // 构造更新对象
        Role role = new Role();
        BeanUtils.copyProperties(roleUpdateParam, role);
        role.setGmtModified(LocalDateTime.now());
        log.debug("准备更新角色数据，角色信息：{}", role);
        roleMapper.updateById(role);
        log.debug("处理【修改角色】的业务完成！");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除角色】的业务，id为:{}", id);
        // 检查角色是否存在
        Role existRole = roleMapper.selectById(id);
        if (existRole == null) {
            String message = "删除角色失败，角色数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        // 检查是否有管理员关联了该角色
        QueryWrapper<AdminRole> roleQuery = new QueryWrapper<>();
        roleQuery.eq("role_id", id);
        int count = adminRoleMapper.selectCount(roleQuery);
        log.debug("根据角色ID查询管理员角色关联数量：{}", count);
        if (count > 0) {
            String message = "删除角色失败，该角色存在关联管理员！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        // 执行删除
        roleMapper.deleteById(id);
        log.debug("处理【根据id删除角色】的业务完成！");
    }
}

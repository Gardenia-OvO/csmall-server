package cn.hqu.csmall.passport.service.impl;

import cn.hqu.csmall.passport.ex.ServiceException;
import cn.hqu.csmall.passport.mapper.AdminMapper;
import cn.hqu.csmall.passport.mapper.AdminRoleMapper;
import cn.hqu.csmall.passport.pojo.entity.Admin;
import cn.hqu.csmall.passport.pojo.entity.AdminRole;
import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.hqu.csmall.passport.service.IAdminService;
import cn.hqu.csmall.passport.web.ServiceCode;
import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import cn.hqu.csmall.product.util.PageInfoToPageDataConverter;
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
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Transactional
    @Override
    public void addNew(AdminAddNewParam adminAddNewParam) {
        log.debug("开始处理【新增管理员】的业务，参数：{}",adminAddNewParam);
        //检查管理员名称是否被占用，如果被占用，则抛出异常
        //根据管理员名称查询管理员表中是否有同名管理员  QueryWrapper:条件对象  拼接where部分内容
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",adminAddNewParam.getUsername());
        int count = adminMapper.selectCount(queryWrapper);
        log.debug("根据管理员名称查询管理员表中是否有同名管理员，查询结果：{}",count);
        if (count > 0){
            String message = "管理员名称被占用，请更换名称后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }
        //查询手机号是否被占用
        QueryWrapper<Admin> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("phone", adminAddNewParam.getPhone());
        int count2 = adminMapper.selectCount(queryWrapper2);
        log.debug("根据手机号查询管理员表中是否有重复手机号，查询结果：{}", count2);
        if (count2 > 0) {
            String message = "管理员手机号被占用，请更换手机号后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        //查询邮箱是否被占用
        QueryWrapper<Admin> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("email", adminAddNewParam.getEmail());
        int count3 = adminMapper.selectCount(queryWrapper3);
        log.debug("根据邮箱查询管理员表中是否有重复邮箱，查询结果：{}", count3);
        if (count3 > 0) {
            String message = "管理员邮箱被占用，请更换邮箱后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        //将管理员信息插入到管理员表中,新增管理员，用实体类Admin
        Admin admin = new Admin();
        //将管理员信息从参数对象AdminAddNewParam中复制到实体类对象Admin中
        BeanUtils.copyProperties(adminAddNewParam,admin);
        //设置管理员的创建时间与修改时间，保证数据的完整性
        admin.setLastLoginIp(null);
        admin.setLoginCount(0);
        admin.setGmtLastLogin(null);
        admin.setGmtCreate(LocalDateTime.now());
        admin.setGmtModified(LocalDateTime.now());
        log.debug("准备将新的管理员数据插入数据库中，管理员信息：{}", admin);
        adminMapper.insert(admin);
        log.debug("新的管理员数据插入数据库中，完成！");
        Long[] roleIds = adminAddNewParam.getRoleIds();
        AdminRole[] adminRoles = new AdminRole[roleIds.length];
        for (long i = 0; i < roleIds.length; i++) {
            AdminRole date = new AdminRole();
            date.setAdminId(admin.getId());
            date.setRoleId(roleIds[(int)i]);
            date.setGmtCreate(LocalDateTime.now());
            date.setGmtModified(LocalDateTime.now());
            adminRoles[(int)i] = date;
        }

        //ToDo 参照以下修改完成别的操作
        int rows = adminRoleMapper.insertBatch(adminRoles);
        if(rows!=roleIds.length){
            String message = "插入管理员角色失败！服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }
        log.debug("新的管理员数据插入数据库中，完成！");
    }

    @Override
    public PageData<AdminListItemVO> list(Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询管理员列表】的业务，页码：{}，每页记录数：{}",pageNum,pageSize);
        PageHelper.startPage(pageNum,pageSize);
        List<AdminListItemVO> list = adminMapper.list();
        PageInfo<AdminListItemVO> pageInfo = new PageInfo<>(list);
        PageData<AdminListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        log.debug("处理【查询管理员列表】的业务完成，结果：{}",pageData);
        return pageData;
    }

    @Override
    public PageData<AdminListItemVO> list(Integer pageNum) {
        Integer pageSize = 5;
        log.debug("开始处理【查询管理员 列表】的业务，页码：{}，每页记录数(默认)：{}",pageNum,pageSize);
        return list(pageNum,pageSize);
    }

}

package cn.hqu.csmall.passport.service.impl;

import cn.hqu.csmall.passport.ex.ServiceException;
import cn.hqu.csmall.passport.mapper.AdminMapper;
import cn.hqu.csmall.passport.pojo.entity.Admin;
import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.service.IAdminService;
import cn.hqu.csmall.passport.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

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
        //TODO 查询手机号是否重用

        //TODO 查询邮箱是否重用

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
        log.debug("准备将新的管理员数据插入数据库中，{}",admin);
        //调用管理员数据表的mapper接口中的insert方法，将管理员信息插入到管理员表中
        adminMapper.insert(admin);
        log.debug("新的管理员数据插入数据库中，完成！");
    }


}

package cn.hqu.csmall.passport.service.impl;

import cn.hqu.csmall.commons.ex.ServiceException;
import cn.hqu.csmall.commons.security.LoginPrincipal;
import cn.hqu.csmall.commons.web.ServiceCode;
import cn.hqu.csmall.commons.pojo.vo.PageData;
import cn.hqu.csmall.commons.util.PageInfoToPageDataConverter;
import cn.hqu.csmall.passport.security.AdminDetail;
import com.alibaba.fastjson.JSON;
import cn.hqu.csmall.passport.mapper.AdminMapper;
import cn.hqu.csmall.passport.mapper.AdminRoleMapper;
import cn.hqu.csmall.passport.pojo.entity.Admin;
import cn.hqu.csmall.passport.pojo.entity.AdminRole;
import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.hqu.csmall.passport.pojo.param.AdminUpdateParam;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.hqu.csmall.passport.service.IAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.Arrays;

@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${csmall.jwt.secret-key}")
    private String secretKey;
    @Value("${csmall.jwt.expire-in-minute}")
    private Long expireInMinute;

    @Transactional
    @Override
    public void addNew(AdminAddNewParam adminAddNewParam) {
        log.debug("开始处理【新增管理员】的业务，参数：{}",adminAddNewParam);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",adminAddNewParam.getUsername());
        int count = adminMapper.selectCount(queryWrapper);
        log.debug("根据管理员名称查询管理员表中是否有同名管理员，查询结果：{}",count);
        if (count > 0){
            String message = "管理员名称被占用，请更换名称后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT,message);
        }
        QueryWrapper<Admin> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("phone", adminAddNewParam.getPhone());
        int count2 = adminMapper.selectCount(queryWrapper2);
        log.debug("根据手机号查询管理员表中是否有重复手机号，查询结果：{}", count2);
        if (count2 > 0) {
            String message = "管理员手机号被占用，请更换手机号后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        QueryWrapper<Admin> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("email", adminAddNewParam.getEmail());
        int count3 = adminMapper.selectCount(queryWrapper3);
        log.debug("根据邮箱查询管理员表中是否有重复邮箱，查询结果：{}", count3);
        if (count3 > 0) {
            String message = "管理员邮箱被占用，请更换邮箱后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddNewParam,admin);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
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
        int rows = adminRoleMapper.insertBatch(adminRoles);
        if(rows!=roleIds.length){
            String message = "插入管理员角色失败！服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT,message);
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
        log.debug("开始处理【查询管理员列表】的业务，页码：{}，每页记录数(默认)：{}",pageNum,pageSize);
        return list(pageNum,pageSize);
    }

    @Override
    public String login(AdminLoginInfoParam adminLoginInfoParam) {
        log.debug("开始处理【管理员登录】的业务，参数：{}",adminLoginInfoParam);
        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                adminLoginInfoParam.getUsername(),
                adminLoginInfoParam.getPassword()
        );
        Authentication authenticateResult = authenticationManager.authenticate(authentication);
        log.debug("验证登录完成,认证结果：{}",authenticateResult);
        AdminDetail adminDetail = (AdminDetail)authenticateResult.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",adminDetail.getId());
        claims.put("username",adminDetail.getUsername());
        List<String> permissions = new ArrayList<>();
        for (GrantedAuthority ga : adminDetail.getAuthorities()) {
            permissions.add(ga.getAuthority());
        }
        claims.put("permissions", JSON.toJSONString(permissions));
        String jwt = Jwts.builder()
                .setHeaderParam("alg","HS256")
                .setHeaderParam("typ","JWT")
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expireInMinute * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
        log.debug("登录认证通过，JWT令牌：{}",jwt);
        return jwt;
    }

    @Override
    @Transactional    public void update(AdminUpdateParam adminUpdateParam) {
        log.debug("开始处理【修改管理员】的业务，参数：{}", adminUpdateParam);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        boolean hasPermission = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("/ams/admin/update")
                        );
        if (!hasPermission) {
            String message = "当前管理员无修改权限";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_FORBIDDEN, message);
        }
        log.debug("权限校验通过，当前用户：{}", auth.getName());
        Admin existAdmin = adminMapper.selectById(adminUpdateParam.getId());
        if (existAdmin == null) {
            String message = "管理员不存在，修改失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        QueryWrapper<Admin> phoneQuery = new QueryWrapper<>();
        phoneQuery.eq("phone", adminUpdateParam.getPhone());
        phoneQuery.ne("id", adminUpdateParam.getId());
        int phoneCount = adminMapper.selectCount(phoneQuery);
        if (phoneCount > 0) {
            String message = "管理员手机号被占用，请更换手机号后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        QueryWrapper<Admin> emailQuery = new QueryWrapper<>();
        emailQuery.eq("email", adminUpdateParam.getEmail());
        emailQuery.ne("id", adminUpdateParam.getId());
        int emailCount = adminMapper.selectCount(emailQuery);
        if (emailCount > 0) {
            String message = "管理员邮箱被占用，请更换邮箱后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminUpdateParam, admin);
        admin.setGmtModified(LocalDateTime.now());
        log.debug("准备更新管理员数据，管理员信息：{}", admin);
        int rows = adminMapper.updateById(admin);
        if (rows != 1) {
            String message = "修改管理员失败，服务器忙，请稍后再试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
        // 如果传入了新的角色列表，则更新角色关联
        List<Long> roleIds = adminUpdateParam.getRoleIds();
        if (roleIds != null && !roleIds.isEmpty()) {
            log.debug("开始更新管理员角色关联，角色ID：{}", Arrays.asList(roleIds));
            // 删除旧的关联
            QueryWrapper<AdminRole> roleQuery = new QueryWrapper<>();
            roleQuery.eq("admin_id", adminUpdateParam.getId());
            adminRoleMapper.delete(roleQuery);
            // 插入新的关联
            AdminRole[] adminRoles = new AdminRole[roleIds.size()];
            for (int i = 0; i < roleIds.size(); i++) {
                AdminRole date = new AdminRole();
                date.setAdminId(adminUpdateParam.getId());
                date.setRoleId(roleIds.get(i));
                date.setGmtCreate(LocalDateTime.now());
                date.setGmtModified(LocalDateTime.now());
                adminRoles[(int)i] = date;
            }
            int roleRows = adminRoleMapper.insertBatch(adminRoles);
            if (roleRows != roleIds.size()) {
                String message = "更新管理员角色失败！服务器忙，请稍后再试！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_INSERT, message);
            }
            log.debug("更新管理员角色关联完成");
                }
        log.debug("修改管理员完成");
    }

    @Override
    public void delete(Long id) {
        log.debug("开始处理【删除管理员】的业务，id为:{}", id);
        // 获取当前登录用户
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginPrincipal currentUser = (LoginPrincipal) auth.getPrincipal();
        log.debug("当前操作用户：{}", currentUser.getUsername());

        // 防止删除自己
        if (currentUser.getId().equals(id)) {
            String message = "删除管理员失败，不允许删除自己！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_FORBIDDEN, message);
        }

        // 检查管理员是否存在
        Admin existAdmin = adminMapper.selectById(id);
        if (existAdmin == null) {
            String message = "删除管理员失败，管理员数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        // 删除管理员与角色的关联数据
        QueryWrapper<AdminRole> roleQuery = new QueryWrapper<>();
        roleQuery.eq("admin_id", id);
        adminRoleMapper.delete(roleQuery);
        log.debug("已删除管理员ID={}的角色关联数据", id);

        // 删除管理员
        int rows = adminMapper.deleteById(id);
        if (rows != 1) {
            String message = "删除管理员失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, message);
        }
        log.debug("处理【根据id删除管理员】的业务完成！");
    }
}

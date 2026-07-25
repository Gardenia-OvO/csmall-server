package cn.hqu.csmall.passport.service.impl;

import cn.hqu.csmall.passport.ex.ServiceException;
import cn.hqu.csmall.passport.security.AdminDetail;
import cn.hqu.csmall.passport.security.LoginPrincipal;
import com.alibaba.fastjson.JSON;
import cn.hqu.csmall.passport.mapper.AdminMapper;
import cn.hqu.csmall.passport.mapper.AdminRoleMapper;
import cn.hqu.csmall.passport.pojo.entity.Admin;
import cn.hqu.csmall.passport.pojo.entity.AdminRole;
import cn.hqu.csmall.passport.pojo.param.AdminAddNewParam;
import cn.hqu.csmall.passport.pojo.param.AdminLoginInfoParam;
import cn.hqu.csmall.passport.pojo.param.AdminUpdateParam;
import cn.hqu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.hqu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import cn.hqu.csmall.passport.service.IAdminService;
import cn.hqu.csmall.passport.web.ServiceCode;
import cn.hqu.csmall.product.pojo.vo.PageData;
import cn.hqu.csmall.product.util.PageInfoToPageDataConverter;
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
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }
        QueryWrapper<Admin> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("phone", adminAddNewParam.getPhone());
        int count2 = adminMapper.selectCount(queryWrapper2);
        log.debug("根据手机号查询管理员表中是否有重复手机号，查询结果：{}", count2);
        if (count2 > 0) {
            String message = "管理员手机号被占用，请更换手机号后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }
        QueryWrapper<Admin> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("email", adminAddNewParam.getEmail());
        int count3 = adminMapper.selectCount(queryWrapper3);
        log.debug("根据邮箱查询管理员表中是否有重复邮箱，查询结果：{}", count3);
        if (count3 > 0) {
            String message = "管理员邮箱被占用，请更换邮箱后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
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
                .setExpiration(new Date(System.currentTimeMillis()+1000L*60*60*24*30))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
        log.debug("登录认证通过，JWT令牌：{}",jwt);
        return jwt;
    }

    @Override
    public String login(LoginPrincipal loginPrincipal) {
        log.debug("开始处理【管理员登录】的业务（LoginPrincipal），参数：{}", loginPrincipal);
        AdminLoginInfoVO loginInfo = adminMapper.getLoginInfoByUsername(loginPrincipal.getUsername());
        if (loginInfo == null) {
            String message = "管理员不存在，请检查用户名";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginInfo.getId());
        claims.put("username", loginInfo.getUsername());
        claims.put("permissions", JSON.toJSONString(loginInfo.getPermissions()));
        String jwt = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        log.debug("登录通过，JWT令牌：{}", jwt);
        return jwt;
    }

    @Override
    public void update(AdminUpdateParam adminUpdateParam) {
        log.debug("开始处理【修改管理员】的业务，参数：{}", adminUpdateParam);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        boolean hasPermission = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("系统管理员")
                        || a.getAuthority().equals("超级管理员"));
        if (!hasPermission) {
            String message = "仅系统管理员和超级管理员可修改管理员信息";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, message);
        }
        log.debug("权限校验通过，当前用户：{}", auth.getName());
        Admin existAdmin = adminMapper.selectById(adminUpdateParam.getId());
        if (existAdmin == null) {
            String message = "管理员不存在，修改失败";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        QueryWrapper<Admin> phoneQuery = new QueryWrapper<>();
        phoneQuery.eq("phone", adminUpdateParam.getPhone());
        phoneQuery.ne("id", adminUpdateParam.getId());
        int phoneCount = adminMapper.selectCount(phoneQuery);
        if (phoneCount > 0) {
            String message = "管理员手机号被占用，请更换手机号后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }
        QueryWrapper<Admin> emailQuery = new QueryWrapper<>();
        emailQuery.eq("email", adminUpdateParam.getEmail());
        emailQuery.ne("id", adminUpdateParam.getId());
        int emailCount = adminMapper.selectCount(emailQuery);
        if (emailCount > 0) {
            String message = "管理员邮箱被占用，请更换邮箱后重试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminUpdateParam, admin);
        admin.setGmtModified(LocalDateTime.now());
        log.debug("准备更新管理员数据，管理员信息：{}", admin);
        int rows = adminMapper.updateById(admin);
        if (rows != 1) {
            String message = "修改管理员失败，服务器忙，请稍后再试";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERR_UPDATE, message);
        }
        log.debug("修改管理员完成");
    }
}


package com.binhao.drive.common.aop;

import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.em.ErrorCodeEnum;
import com.binhao.drive.common.service.AuthenticationService;
import com.binhao.drive.common.vo.BusinessException;
import com.binhao.drive.manager.mapper.AccountUserMapper;
import com.binhao.drive.manager.po.AccountUser;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class PermissionAspect {
    @Resource
    protected HttpServletRequest request;
    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private AccountUserMapper accountUserMapper;
    public PermissionAspect() {
    }

    @Pointcut("execution(* com.binhao.drive.manager.controller.*Controller.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()&&@annotation(operation)")
    public void beforeCheckPermission(JoinPoint joinPoint, AopOperation operation) {

        //获取 身份认证
        String authToken = this.authenticationService.getAuthToken();
        if(StringUtils.isEmpty(authToken)){
            throw new BusinessException(ErrorCodeEnum.NOT_AUTH_TOKEN);
        }

        SessionUser paramUser = this.authenticationService.getSessionUser();

        String type = operation.type();

        // 如果是管理员接口
        if(operation.checkPermission()){
            if(paramUser!=null){
                AccountUser accountUser = accountUserMapper.selectById(paramUser.getUserId());
                //如果是非超级管理员操作
                if(operation.checkEmployee()){
                    if(accountUser.getUserType()!=1){
                        throw new BusinessException(ErrorCodeEnum.NO_PERMISSIONS.getCode(),ErrorCodeEnum.NO_PERMISSIONS.getMsg().replace("{module}","超级管理员模块").replace("{type}",type));
                    }
                }
                if(accountUser.getUserType()!=1 && accountUser.getUserType()!=2){
                    throw new BusinessException(ErrorCodeEnum.NO_PERMISSIONS.getCode(),ErrorCodeEnum.NO_PERMISSIONS.getMsg().replace("{module}","管理员模块").replace("{type}",type));
                }
            }else {
                throw new BusinessException("非法操作,已记录ip");
            }
        }

        if(operation.saveLog()&& paramUser!=null){
            System.out.println("保存"+paramUser.getUserName()+"的"+type+"操作的记录");
        }
    }
}

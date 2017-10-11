package com.owl.wifi.web.interceptor;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.owl.wifi.content.Status;
import com.owl.wifi.content.SysContent;
import com.owl.wifi.log.BKLogger;
import com.owl.wifi.util.Help;
import com.owl.wifi.util.Result;
import com.owl.wifi.web.entity.User;
import com.owl.wifi.web.user.service.PermissionService;


@Aspect
@Component
@Order(-5)
public class ControllerInterceptor {
	BKLogger logger = BKLogger.getLogger(ControllerInterceptor.class);
	private static final Logger log= LoggerFactory.getLogger(ControllerInterceptor.class);
	
	
	@Autowired
	private PermissionService permissionService;
    /**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     * 拦截以Controller结尾的方法
     */
     @Pointcut("execution(public * com.owl.wifi.web.*.controller.*.*Controller(..))")
     public void permission(){}
     
     @Before("permission()")
     public void doBefore(JoinPoint joinPoint){
        
       // 接收到请求，记录请求内容
    
    	    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
           
      // 记录下请求内容
       //获取所有参数方法一：
        Enumeration<String> enu=request.getParameterNames(); 
        while(enu.hasMoreElements()){ 
            String paraName=(String)enu.nextElement(); 
            System.out.println(paraName+": "+request.getParameter(paraName)); 
        } 
     }
     @Around("permission()")
     public Object around(ProceedingJoinPoint thisJoinPoint){
         System.err.println ("切面执行了。。。。");
         Object proceed = null;

         
         Result result = new Result();
         try {
        	    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                User loginUser = (User) request.getSession().getAttribute("user");
                if(Help.isNull(loginUser)){
                	//登录拦截
                	log.debug("登录超时！重新登录");
                	if(isAjax(request)){
		        		result.setStatus(Status.login_timeout_status);
		        		result.setInfo(Status.login_timeout_info);
		        		return logger.infobk("用户登录超时", result);
                	}else{
                		return "login";
                	}
	        		
	        		
	        		
                }else{
                	//权限拦截
                	 String requestURI = request.getRequestURI();
 	    	        String contextPath = request.getContextPath();
 	    	        String permitStr=requestURI.replace(contextPath, "");
 	    	        boolean permitted=isPermission(permitStr,Long.parseLong(loginUser.getUserId()+""));
 	    	        if(!permitted){
 	    	        	if(isAjax(request)){
	 	    	        	log.debug("权限不足！！");
	    	        		result.setStatus(Status.access_deny_status);
	    	        		result.setInfo(Status.access_deny_info);
	    	        		return result;
 	    	        	}else{
 	    	        		return "403";
 	    	        	}
 	    	        }
                	
                }
                
                proceed = thisJoinPoint.proceed();
                
         } catch (Throwable e) {
             e.printStackTrace ();
         }
         return proceed;
     }
     @AfterReturning("permission()")
     public void  doAfterReturning(JoinPoint joinPoint){
       // 处理完请求，返回内容
     }
     /**
     * 判断ajax请求
     * @param request
     * @return
     */
    boolean isAjax(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
    }
    /**
     * 判断是否在登录回话期
     * @return
     */
    boolean isPermission(String permitStr,Long userId){
    	if("/index.do".equals(permitStr)||"/userMenus.do".equals(permitStr)||"/userInfo.do".equals(permitStr)||"/resetPwd.do".equals(permitStr)){
    		return true;
    	}
    	HttpServletRequest request = SysContent.getRequest();
	        HttpServletResponse response = SysContent.getResponse();
		Map<String, Object> authorization = permissionService.authorization(userId);
		List<String> permissions = (List<String>) authorization.get("permissions");
		if(Help.isNotNull(permissions)&&permissions.contains(permitStr)){
			return true;
		}else{
			return false;
		}
    }
}

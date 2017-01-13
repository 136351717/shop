package cn.itcast.shop.interceptor;

import cn.itcast.shop.adminuser.vo.AdminUser;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/*
 * 后台登录访问的权限拦截器
 */
public class PrivilegeInterceptor extends MethodFilterInterceptor{

	@Override
	//执行拦截的方法
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		AdminUser existAdminUser = (AdminUser)ActionContext.getContext().getSession().get("existAdminUser");
		if(existAdminUser==null){
			ActionSupport actionSupport =  (ActionSupport)actionInvocation.getAction();
			actionSupport.addActionError("请您先登录在操作");
			return "loginFail";
		}else{
			return actionInvocation.invoke();
		}
	}
	
}

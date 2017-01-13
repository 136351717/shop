package cn.itcast.shop.interceptor;

import cn.itcast.shop.adminuser.vo.AdminUser;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/*
 * ��̨��¼���ʵ�Ȩ��������
 */
public class PrivilegeInterceptor extends MethodFilterInterceptor{

	@Override
	//ִ�����صķ���
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		AdminUser existAdminUser = (AdminUser)ActionContext.getContext().getSession().get("existAdminUser");
		if(existAdminUser==null){
			ActionSupport actionSupport =  (ActionSupport)actionInvocation.getAction();
			actionSupport.addActionError("�����ȵ�¼�ڲ���");
			return "loginFail";
		}else{
			return actionInvocation.invoke();
		}
	}
	
}

package cn.itcast.shop.adminuser.action;

import cn.itcast.shop.adminuser.service.AdminUserService;
import cn.itcast.shop.adminuser.vo.AdminUser;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser> {
	
	private AdminUser adminUser = new AdminUser();
		
	@Override
	public AdminUser getModel() {
		return adminUser;
	}
	
	private AdminUserService adminUserService;

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}
	
	
	//��̨�û���¼����
	public String login(){
		AdminUser existAdminUser = adminUserService.login(adminUser);
		if(existAdminUser == null){
			//ʧ��
			this.addActionError("��¼ʧ��,�û������������!!");
			return "loginFail";
		}else{
			//��½�ɹ�
			ActionContext.getContext().getSession().put("existAdminUser", existAdminUser);
			return "loginSuccess";
		}
	}

}

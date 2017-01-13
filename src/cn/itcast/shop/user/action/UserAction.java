package cn.itcast.shop.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.user.service.UserService;
import cn.itcast.shop.user.vo.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	//ģ������
	private User user = new User();
	
	private String checkcode;
	
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	@Override
	public User getModel() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private UserService userService;
	
	//��תע��ҳ��
	public String registPage(){
		return "registPage";
	}
	

	//Ajax��̨У���û���
	public void findByName() throws IOException{ 
		
		User existUser = userService.findByUsername(user.getUsername());//ͨ��ģ�������������ݿ�����û���
		
		HttpServletResponse response = ServletActionContext.getResponse();	//��ȡresponse
		response.setContentType("text/html;charset=UTF-8");
		
		String message="<font color='green'>����ʹ��</font>";
		if (existUser != null) {
			message="<font color='red'>�û����Ѵ���</font>";
		}
		response.getWriter().write(message);
		
	}

	/*
	 * �û�ע��ķ���
	 */
	public String regist(){
		String checkcode1 = (String)ServletActionContext.getRequest().getSession().getAttribute("checkcode");
		if(!checkcode.equalsIgnoreCase(checkcode1)){
			this.addActionError("��֤�벻��ȷ");
			return "checkCodeFail";
		}
		userService.save(user);
		this.addActionMessage("ע��ɹ�,��ǰ�����伤��!");
		return "msg";
	}
	
	public String active(){
		User existUser = userService.findByCode(user.getCode());
		if(existUser != null){
			existUser.setState(1);
			existUser.setCode(null);
			userService.update(existUser);
			this.addActionMessage("����ɹ�:��ǰ����¼ҳ��");
		}else{
			this.addActionMessage("����ʧ��:������ʧЧ");
		}
		return "msg";
	}
	
	public String loginPage(){
		return "loginPage";
	}
	
	public String login(){
		User existUser = userService.login(user);
		if(existUser==null || existUser.getState()==0){
			this.addActionError("��¼ʧ��,�����µ�¼");
			return "login";
		}else{
			ActionContext.getContext().getSession().put("existUser", existUser);
			return "loginSuccess";
		}
	}
	
	
	public String quit(){
		ActionContext.getContext().getSession().clear();
		return "quit";
	}
}

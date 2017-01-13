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
	//模型驱动
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
	
	//跳转注册页面
	public String registPage(){
		return "registPage";
	}
	

	//Ajax后台校验用户名
	public void findByName() throws IOException{ 
		
		User existUser = userService.findByUsername(user.getUsername());//通过模型驱动调用数据库查找用户名
		
		HttpServletResponse response = ServletActionContext.getResponse();	//获取response
		response.setContentType("text/html;charset=UTF-8");
		
		String message="<font color='green'>可以使用</font>";
		if (existUser != null) {
			message="<font color='red'>用户名已存在</font>";
		}
		response.getWriter().write(message);
		
	}

	/*
	 * 用户注册的方法
	 */
	public String regist(){
		String checkcode1 = (String)ServletActionContext.getRequest().getSession().getAttribute("checkcode");
		if(!checkcode.equalsIgnoreCase(checkcode1)){
			this.addActionError("验证码不正确");
			return "checkCodeFail";
		}
		userService.save(user);
		this.addActionMessage("注册成功,请前往邮箱激活!");
		return "msg";
	}
	
	public String active(){
		User existUser = userService.findByCode(user.getCode());
		if(existUser != null){
			existUser.setState(1);
			existUser.setCode(null);
			userService.update(existUser);
			this.addActionMessage("激活成功:请前往登录页面");
		}else{
			this.addActionMessage("激活失败:激活码失效");
		}
		return "msg";
	}
	
	public String loginPage(){
		return "loginPage";
	}
	
	public String login(){
		User existUser = userService.login(user);
		if(existUser==null || existUser.getState()==0){
			this.addActionError("登录失败,请重新登录");
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

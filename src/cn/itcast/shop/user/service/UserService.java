package cn.itcast.shop.user.service;


import org.springframework.transaction.annotation.Transactional;

import cn.itcast.shop.user.dao.UserDao;
import cn.itcast.shop.user.vo.User;
import cn.itcast.shop.utils.MailUtils;
import cn.itcast.shop.utils.UUIDUtils;
@Transactional
public class UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User findByUsername(String username){
		return userDao.findByUsername(username);
	}

	public void save(User user) {
		user.setState(0);	//0未激活 1已激活
		String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);
		//发送激活邮件
		MailUtils.sendMail(user.getEmail(), code);
	}

	public User findByCode(String code) {
		return userDao.findByCode(code);
	}

	public void update(User existUser) {
		userDao.update(existUser);
	}

	public User login(User user) {
		return userDao.login(user);
	}
	
	
}

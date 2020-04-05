package cn.al.hax.store.service.serviceImp;

import java.sql.SQLException;

import cn.al.hax.store.dao.UserDao;
import cn.al.hax.store.dao.daoImp.UserDaoImp;
import cn.al.hax.store.domain.User;
import cn.al.hax.store.service.UserService;
import cn.al.hax.store.utils.BeanFactory;

public class UserServiceImp implements UserService {
	private UserDao UserDao=(UserDao)BeanFactory.createObject("UserDao");
	//实现注册功能	
	@Override
	public void userRegist(User user) throws SQLException {
	UserDao.userRegist(user);
		
	}
	//用户的激活功能
	@Override
	public boolean userActive(String code) throws SQLException {	
		///对DB发送select * from user where code=?
		User user=UserDao.userActive(code);
		//激活成功以后的操作
		if(null!=user){
			//可以根据激活码查询到一个用户
			//修改用户的状态,清除激活码
			user.setState(1);
			user.setCode(null);
			//对数据库执行一次真实的更新操作  update user set state=1 , code=null where uid=?
			//update user set username=? , password=? ,name =? ,email=?, telephone =? ,birthday =? ,sex=? ,state=? ,code= ? where uid=?
			UserDao.updateUser(user);
			return  true;
		}else{
			//不可以根据激活码查询到一个用户
			return false;
		}
	}
	//校验用户的登录
	@Override
	public User userLogin(User user) throws SQLException {
		//此处:可以利用异常在模块之间传递数据
		User uu=UserDao.userLogin(user);
		if(null==uu){
			throw new RuntimeException("密码有误!");
		}else if(uu.getState()==0){
			throw new RuntimeException("用户未激活!");
		}else{
			return uu;
		}
	}

}

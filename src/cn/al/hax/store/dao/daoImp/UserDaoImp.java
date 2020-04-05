package cn.al.hax.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.al.hax.store.dao.UserDao;
import cn.al.hax.store.domain.User;
import cn.al.hax.store.utils.JDBCUtils;

public class UserDaoImp implements UserDao {
	private QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
	/**
	 * 注册用户保存用户信息
	 */
	@Override
	public void userRegist(User user) throws SQLException {
		String sql="INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] params={user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		qr.update(sql, params);
		
	}
	/**
	 * 验证码信息的校验（邮箱激活中，查询改激活码是否存在）
	 */
	@Override
	public User userActive(String code)throws SQLException {
		String sql="select * from user where code=?";
		User user=qr.query(sql, new BeanHandler<User>(User.class),code);
		return user;
	}
	/**
	 * 修改用户信息
	 */
	@Override
	public void updateUser(User user) throws SQLException{
		String sql="update user set username=? , password=? ,name =? ,email=?, telephone =? ,birthday =? ,sex=? ,state=? ,code= ? where uid=?";
		Object[] params={user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
		qr.update(sql,params);
	}
	/*
	 * 用户登录的校验操作
	 */
	@Override
	public User userLogin(User user) throws SQLException {
		String sql="select * from user where username=?  and password= ?";
		return qr.query(sql, new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
	}

}

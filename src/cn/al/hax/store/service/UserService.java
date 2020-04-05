package cn.al.hax.store.service;

import java.sql.SQLException;

import cn.al.hax.store.domain.User;

public interface UserService {

	void userRegist(User user)throws SQLException ;

	boolean userActive(String code)throws SQLException ;

	User userLogin(User user)throws SQLException;

}

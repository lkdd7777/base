package com.sppan.base.dao;

import com.sppan.base.dao.support.IBaseDao;
import com.sppan.base.dao.support.IBaseDao;
import com.sppan.base.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends IBaseDao<User, Integer> {

	User findByUserName(String username);

}

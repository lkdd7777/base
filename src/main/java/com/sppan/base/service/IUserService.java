package com.sppan.base.service;

import com.sppan.base.entity.User;
import com.sppan.base.service.support.IBaseService;
import com.sppan.base.entity.User;
import com.sppan.base.service.support.IBaseService;

/**
 * <p>
 * 用户服务类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
public interface IUserService extends IBaseService<User, Integer> {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findByUserName(String username);

	/**
	 * 增加或者修改用户
	 * @param user
	 */
	void saveOrUpdate(User user);

	/**
	 * 给用户分配角色
	 * @param id 用户ID
	 * @param roleIds 角色Ids
	 */
	void grant(Integer id, String[] roleIds);

	/**
	 * 给用户分配部门
	 * @param id
	 * @param deptId
	 */
	void grantDept(Integer id,Integer deptId);

}

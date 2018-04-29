package com.sppan.base.service;

import java.util.List;

import com.sppan.base.entity.Resource;
import com.sppan.base.service.support.IBaseService;
import com.sppan.base.vo.ZtreeView;

/**
 * <p>
 * 资源服务类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
public interface IResourceService extends IBaseService<Resource, Integer> {

	/**
	 * 获取角色的权限树
	 * @param roleId
	 * @return
	 */
	List<ZtreeView> tree(int roleId);

	/**
	 * 修改或者新增资源
	 * @param resource
	 */
	void saveOrUpdate(Resource resource);

}

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
	 * 获取全部
	 * @return
	 */
	List<Resource> parent();

	/**
	 * 修改或者新增资源
	 * @param resource
	 */
	void saveOrUpdate(Resource resource);

	/**
	 * 查询目录
	 * @return
	 */
	List<Resource> findMenu(Integer level);

	/**
	 * 查询菜单
	 * @param parentId
	 * @return
	 */
	List<Resource> findByParentId(Integer parentId);

	List<Resource> findByParentIdAndRoleIds(Integer parentId,List<Integer> roleIds);

}

package com.sppan.base.dao;

import com.sppan.base.dao.support.IBaseDao;
import com.sppan.base.entity.Resource;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResourceDao extends IBaseDao<Resource, Integer> {

	@Modifying
	@Query(nativeQuery = true,value = "DELETE FROM tb_role_resource WHERE resource_id = :id")
	void deleteGrant(@Param("id") Integer id);

	@Query(nativeQuery = true,value = "SELECT * FROM tb_resource WHERE `level` = '0' or `level` = '1' order by sort ASC ")
	List<Resource> findByLevel();

	@Query(nativeQuery = true,value = "SELECT * FROM tb_resource WHERE `level` = :level order by sort ASC ")
	List<Resource> findMenu(@Param("level") Integer level);

	@Query(nativeQuery = true,value = "SELECT * FROM tb_resource WHERE parent_id = :parentId")
	List<Resource> findByParentId(@Param("parentId") Integer parentId);


	@Query(nativeQuery = true,value = "SELECT a.* FROM tb_resource a left join tb_role_resource b on a.id = b.resource_id  WHERE b.role_id in (:roleIds)and a.parent_id = :parentId order by a.sort ASC")
	List<Resource> findByParentIdAndRoleIds(@Param("parentId") Integer parentId,@Param("roleIds") List<Integer> roleIds);
}

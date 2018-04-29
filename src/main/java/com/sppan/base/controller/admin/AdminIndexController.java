package com.sppan.base.controller.admin;

import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.Resource;
import com.sppan.base.entity.Role;
import com.sppan.base.entity.User;
import com.sppan.base.service.IResourceService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sppan.base.controller.BaseController;

import java.util.*;

@Controller
public class AdminIndexController extends BaseController {

	@Autowired
	private IResourceService resourceService;

	@RequestMapping(value ={"/admin/","/admin/index"})
	public String index(ModelMap map){
		//获取登录用户信息
//		User user = (User) SecurityUtils.getSubject().getPrincipal();
//		Set<Role> set = user.getRoles();
//		List<Integer> rolesIds = new ArrayList<Integer>();
//		for (Role r:set) {
//			Integer roleId = r.getId();
//
//			rolesIds.add(roleId);
//		}

//		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
//		List<Resource> list = resourceService.findByParentIdAndRoleIds(0,rolesIds);
//		for (Resource r:list) {
//			Map<String,Object> resultMap = new HashMap<>();
//			String name = r.getName();
//			Integer id = r.getId();
//
//			List<Resource> child = resourceService.findByParentIdAndRoleIds(id,rolesIds);
//
//			resultMap.put("name",name);
//			resultMap.put("child",child);
//
//			result.add(resultMap);
//		}

		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		List<Resource> list = resourceService.findByParentId(0);
		for (Resource r:list) {
			Map<String,Object> resultMap = new HashMap<>();

			String name = r.getName();
			Integer id = r.getId();
			String sourceKey = r.getSourceKey();

			List<Resource> child = resourceService.findByParentId(id);

			resultMap.put("name",name);
			resultMap.put("sourceKey",sourceKey);
			resultMap.put("child",child);

			result.add(resultMap);
		}

		map.put("result",result);
		return "admin/index";
	}
	
	@RequestMapping(value = {"/admin/welcome"})
	public String welcome(){
		
		return "admin/welcome";
	}
}

package com.sppan.base.controller.admin.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sppan.base.common.utils.MD5Utils;
import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.Dept;
import com.sppan.base.service.IDeptService;
import com.sppan.base.service.ILogService;
import com.sppan.base.service.specification.SimpleSpecificationBuilder;
import com.sppan.base.service.specification.SpecificationOperator;
import com.sppan.base.common.JsonResult;
import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.Role;
import com.sppan.base.entity.User;
import com.sppan.base.service.IRoleService;
import com.sppan.base.service.IUserService;
import com.sppan.base.service.specification.SimpleSpecificationBuilder;
import com.sppan.base.service.specification.SpecificationOperator.Operator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private ILogService logService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "admin/user/index";
	}

	@RequestMapping(value = { "/list" })
	@ResponseBody
	public Page<User> list() {
		SimpleSpecificationBuilder<User> builder = new SimpleSpecificationBuilder<User>();
		String searchText = request.getParameter("searchText");
		if(StringUtils.isNotBlank(searchText)){
			builder.add("nickName", SpecificationOperator.Operator.likeAll.name(), searchText);
		}
		Page<User> page = userService.findAll(builder.generateSpecification(), getPageRequest());
		return page;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		return "admin/user/form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		User user = userService.find(id);
		map.put("user", user);
		return "admin/user/form";
	}
	
	@RequestMapping(value= {"/edit"} ,method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(User user,ModelMap map){
		try {
			userService.saveOrUpdate(user);
			User suser = (User) SecurityUtils.getSubject().getPrincipal();
			logService.saveLog(suser,request,"编辑用户："+user.getNickName());
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			User user = this.userService.find(id);
			User suser = (User) SecurityUtils.getSubject().getPrincipal();
			logService.saveLog(suser,request,"删除用户："+user.getNickName());

			userService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
	public String grant(@PathVariable Integer id, ModelMap map) {
		User user = userService.find(id);
		map.put("user", user);
		
		Set<Role> set = user.getRoles();
		List<Integer> roleIds = new ArrayList<Integer>();
		for (Role role : set) {
			roleIds.add(role.getId());
		}
		map.put("roleIds", roleIds);
		
		List<Role> roles = roleService.findAll();
		map.put("roles", roles);
		return "admin/user/grant";
	}
	
	@ResponseBody
	@RequestMapping(value = "/grant/{id}", method = RequestMethod.POST)
	public JsonResult grant(@PathVariable Integer id,String[] roleIds, ModelMap map) {
		try {
			userService.grant(id,roleIds);

			User user = this.userService.find(id);
			User suser = (User) SecurityUtils.getSubject().getPrincipal();
			logService.saveLog(suser,request,"给用户["+user.getNickName()+"]关联权限");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

	@RequestMapping(value = "/grantDept/{id}", method = RequestMethod.GET)
	public String grantDept(@PathVariable Integer id, ModelMap map) {
		User user = userService.find(id);
		map.put("user", user);

		Dept dept = user.getDept();
		map.put("deptId", dept == null ? "" : dept.getId());

		List<Dept> depts = deptService.findAll();
		map.put("depts", depts);
		return "admin/user/grantDept";
	}

	@ResponseBody
	@RequestMapping(value = "/grantDept/{id}", method = RequestMethod.POST)
	public JsonResult grantDept(@PathVariable Integer id,Integer deptId, ModelMap map) {
		try {
			userService.grantDept(id,deptId);

			User user = this.userService.find(id);
			User suser = (User) SecurityUtils.getSubject().getPrincipal();
			logService.saveLog(suser,request,"给用户["+user.getNickName()+"]关联部门");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

}

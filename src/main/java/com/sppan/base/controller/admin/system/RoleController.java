package com.sppan.base.controller.admin.system;

import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.User;
import com.sppan.base.service.ILogService;
import com.sppan.base.service.IResourceService;
import com.sppan.base.service.specification.SimpleSpecificationBuilder;
import com.sppan.base.service.specification.SpecificationOperator;
import com.sppan.base.common.JsonResult;
import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.Role;
import com.sppan.base.service.IResourceService;
import com.sppan.base.service.IRoleService;
import com.sppan.base.service.specification.SimpleSpecificationBuilder;
import com.sppan.base.service.specification.SpecificationOperator.Operator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IResourceService resourceService;

	@Autowired
	private ILogService logService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "admin/role/index";
	}

	@RequestMapping(value = { "/list" })
	@ResponseBody
	public Page<Role> list() {
		SimpleSpecificationBuilder<Role> builder = new SimpleSpecificationBuilder<Role>();
		String searchText = request.getParameter("searchText");
		if(StringUtils.isNotBlank(searchText)){
			builder.add("name", SpecificationOperator.Operator.likeAll.name(), searchText);
			builder.addOr("description", SpecificationOperator.Operator.likeAll.name(), searchText);
		}
		Page<Role> page = roleService.findAll(builder.generateSpecification(), getPageRequest());
		return page;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		return "admin/role/form";
	}
	

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		Role role = roleService.find(id);
		map.put("role", role);
		return "admin/role/form";
	}
	
	
	@RequestMapping(value= {"/edit"},method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(Role role,ModelMap map){
		try {
			roleService.saveOrUpdate(role);

			User user = (User) SecurityUtils.getSubject().getPrincipal();
			logService.saveLog(user,request,"编辑角色："+role.getName());
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			Role role = this.roleService.find(id);
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			logService.saveLog(user,request,"删除角色："+role.getName());

			roleService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
	public String grant(@PathVariable Integer id, ModelMap map) {
		Role role = roleService.find(id);
		map.put("role", role);
		return "admin/role/grant";
	}

	@RequestMapping(value = "/grant/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult grant(@PathVariable Integer id,
			@RequestParam(required = false) String[] resourceIds, ModelMap map) {
		try {
			roleService.grant(id,resourceIds);
			Role role = this.roleService.find(id);
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			logService.saveLog(user,request,"给角色["+role.getName()+"]分配资源");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
}

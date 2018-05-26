package com.sppan.base.controller.admin.system;

import java.util.List;

import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.User;
import com.sppan.base.service.ILogService;
import com.sppan.base.service.IResourceService;
import com.sppan.base.service.specification.SimpleSpecificationBuilder;
import com.sppan.base.service.specification.SpecificationOperator;
import com.sppan.base.vo.ZtreeView;
import com.sppan.base.common.JsonResult;
import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.Resource;
import com.sppan.base.service.IResourceService;
import com.sppan.base.service.specification.SimpleSpecificationBuilder;
import com.sppan.base.service.specification.SpecificationOperator.Operator;
import com.sppan.base.vo.ZtreeView;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/resource")
public class ResourceController extends BaseController {
	@Autowired
	private IResourceService resourceService;

	@Autowired
	private ILogService logService;
	
	@RequestMapping("/tree/{resourceId}")
	@ResponseBody
	public List<ZtreeView> tree(@PathVariable Integer resourceId){
		List<ZtreeView> list = resourceService.tree(resourceId);
		return list;
	}

	@RequestMapping("/index")
	public String index() {
		return "admin/resource/index";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Page<Resource> list() {
		SimpleSpecificationBuilder<Resource> builder = new SimpleSpecificationBuilder<Resource>();
		String searchText = request.getParameter("searchText");
		if(StringUtils.isNotBlank(searchText)){
			builder.add("name", SpecificationOperator.Operator.likeAll.name(), searchText);
		}
		Page<Resource> page = resourceService.findAll(builder.generateSpecification(),getPageRequest());
		return page;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		List<Resource> list = resourceService.parent();
		map.put("list", list);
		return "admin/resource/form";
	}
	

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		Resource resource = resourceService.find(id);
		map.put("resource", resource);
		
		List<Resource> list = resourceService.parent();
		map.put("list", list);

		return "admin/resource/form";
	}
	
	@RequestMapping(value= {"/edit"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(Resource resource,ModelMap map){
		try {
			resourceService.saveOrUpdate(resource);

			User user = (User) SecurityUtils.getSubject().getPrincipal();
			logService.saveLog(user,request,"编辑资源："+resource.getName());
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			Resource resource = this.resourceService.find(id);
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			logService.saveLog(user,request,"删除资源："+resource.getName());
			resourceService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
}

package com.sppan.base.controller.admin.system;

import com.sppan.base.common.JsonResult;
import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.Dept;
import com.sppan.base.service.IDeptService;
import com.sppan.base.service.specification.SimpleSpecificationBuilder;
import com.sppan.base.service.specification.SpecificationOperator.Operator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/dept")
public class DeptController extends BaseController {

	@Autowired
	private IDeptService deptService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "admin/dept/index";
	}

	@RequestMapping(value = { "/list" })
	@ResponseBody
	public Page<Dept> list() {
		SimpleSpecificationBuilder<Dept> builder = new SimpleSpecificationBuilder<Dept>();
		String searchText = request.getParameter("searchText");
		if(StringUtils.isNotBlank(searchText)){
			builder.add("name", Operator.likeAll.name(), searchText);
			builder.addOr("description", Operator.likeAll.name(), searchText);
		}
		Page<Dept> page = deptService.findAll(builder.generateSpecification(), getPageRequest());
		return page;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		return "admin/dept/form";
	}
	

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id,ModelMap map) {
		Dept dept = deptService.find(id);
		map.put("dept", dept);
		return "admin/dept/form";
	}
	
	
	@RequestMapping(value= {"/edit"},method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit(Dept dept,ModelMap map){
		try {
			deptService.saveOrUpdate(dept);
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable Integer id,ModelMap map) {
		try {
			deptService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
}

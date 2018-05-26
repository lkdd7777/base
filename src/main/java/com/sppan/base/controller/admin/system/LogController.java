package com.sppan.base.controller.admin.system;

import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.Log;
import com.sppan.base.entity.User;
import com.sppan.base.service.ILogService;
import com.sppan.base.service.specification.SimpleSpecificationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import com.sppan.base.service.specification.SpecificationOperator.Operator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/log")
public class LogController extends BaseController {

    @Autowired
    private ILogService logService;

    @RequestMapping(value = { "/", "/index" })
    public String index() {
        return "admin/log/index";
    }

    @RequestMapping(value = { "/list" })
    @ResponseBody
    public Page<Log> list() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleSpecificationBuilder<Log> builder = new SimpleSpecificationBuilder<Log>();

        String searchText = request.getParameter("searchText");
        if(StringUtils.isNotBlank(searchText)){
            builder.add("remark", Operator.likeAll.name(), searchText);
        }

        //超级管理员查看全部日志，普通用户查看自己的日志
        String userName = user.getUserName();
        if(!"superadmin".equals(userName)){
            builder.add("user", Operator.eq.name(), user);
        }


        Page<Log> page = logService.findAll(builder.generateSpecification(), getPageRequest());
        return page;
    }
}

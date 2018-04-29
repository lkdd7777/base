package com.sppan.base.controller.web;

import com.sppan.base.common.JsonResult;
import com.sppan.base.controller.BaseController;
import com.sppan.base.entity.File;
import com.sppan.base.entity.User;
import com.sppan.base.service.IFileService;
import com.sppan.base.service.specification.SimpleSpecificationBuilder;
import com.sppan.base.service.specification.SpecificationOperator;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private IFileService fileService;

    @RequestMapping(value = { "/", "/index" })
    public String index() {
        return "file/index";
    }

    @RequestMapping(value = { "/list" })
    @ResponseBody
    public Page<File> list(){
        SimpleSpecificationBuilder<File> builder = new SimpleSpecificationBuilder<File>();
        String searchText = request.getParameter("searchText");
        if(StringUtils.isNotBlank(searchText)){
            builder.add("name", SpecificationOperator.Operator.likeAll.name(), searchText);
        }
        builder.add("deleteStatus",SpecificationOperator.Operator.eq.name(),0);
        Page<File> page = fileService.findAll(builder.generateSpecification(), getPageRequest());

        return page;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        return "file/form";
    }

    @RequestMapping(value= {"/edit"} ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult edit(File bean,@RequestParam("file") MultipartFile file){
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(null == user){
                return JsonResult.failure("登录失效，请重新登录");
            }

            if(file != null){
                String originalFilename = file.getOriginalFilename();
                String name = originalFilename.substring(0,originalFilename.indexOf("."));
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newName = System.currentTimeMillis() + suffix;
                String path = uploadPath.getPath() + newName;
                java.io.File routeFile = new java.io.File( path);
                file.transferTo(routeFile);

                bean.setName(name);
                bean.setSuffix(suffix);
                bean.setRoute(newName);
            }

            bean.setDeleteStatus(0);
            bean.setCreateTime(new Date());

            bean.setUser(user);

            fileService.saveOrUpdate(bean);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(@PathVariable Integer id,ModelMap map) {
        try {
            this.fileService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void download(@PathVariable("id")Integer id) {
        File file = this.fileService.find(id);
        String filename = file.getName() + file.getSuffix();
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new java.io.File(uploadPath.getPath() + file.getRoute())));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }
}

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
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

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

    /**
     * 逻辑删除
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/logicDelete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult logicDelete(@PathVariable Integer id,ModelMap map) {
        try {
            File file = this.fileService.find(id);
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            file.setUser(user);
            file.setDeleteStatus(1);
            this.fileService.logicDel(file);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * 还原
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/reduction/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult reduction(@PathVariable Integer id,ModelMap map) {
        try {
            File file = this.fileService.find(id);
            file.setDeleteStatus(0);
            this.fileService.logicDel(file);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * 回收站
     * @return
     */
    @RequestMapping(value = "/recycle" )
    public String recycle() {
        return "file/recycle";
    }

    /**
     * 回收站list
     * @return
     */
    @RequestMapping(value = { "/recycleList" })
    @ResponseBody
    public Page<File> recycleList(){
        SimpleSpecificationBuilder<File> builder = new SimpleSpecificationBuilder<File>();
        String searchText = request.getParameter("searchText");
        if(StringUtils.isNotBlank(searchText)){
            builder.add("name", SpecificationOperator.Operator.likeAll.name(), searchText);
        }
        builder.add("deleteStatus",SpecificationOperator.Operator.eq.name(),1);
        Page<File> page = fileService.findAll(builder.generateSpecification(), getPageRequest());

        return page;
    }

    /**
     * 下载
     * @param id
     * @throws FileNotFoundException
     */
    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void download(@PathVariable("id")Integer id)throws FileNotFoundException {
        File file = this.fileService.find(id);
        String fileName = file.getName() + file.getSuffix();

        // 下载本地文件
        String filePath = uploadPath.getPath() + file.getRoute();
        InputStream in = null;
        try{
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            //2.设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

            java.io.File file1 = new java.io.File(filePath);

            in = new FileInputStream(file1);

            //3.通过response获取ServletOutputStream对象(out)
            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1){
                b = in.read(buffer);
                if(b != -1){
                    response.getOutputStream().write(buffer,0,b);//4.写到输出流(out)中
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (in != null) {
                    in.close();
                }
                response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
//    public void download(@PathVariable("id")Integer id) {
//        File file = this.fileService.find(id);
//        String filename = file.getName() + file.getSuffix();
//        response.setHeader("content-type", "application/octet-stream");
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
//        byte[] buff = new byte[1024];
//        BufferedInputStream bis = null;
//        OutputStream os = null;
//        try {
//            os = response.getOutputStream();
//            bis = new BufferedInputStream(new FileInputStream(new java.io.File(uploadPath.getPath() + file.getRoute())));
//            int i = bis.read(buff);
//            while (i != -1) {
//                os.write(buff, 0, buff.length);
//                os.flush();
//                i = bis.read(buff);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (bis != null) {
//                try {
//                    bis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("success");
//    }
}

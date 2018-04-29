package com.sppan.base.service;

import com.sppan.base.entity.File;
import com.sppan.base.service.support.IBaseService;

import java.util.List;
import java.util.Map;

public interface IFileService extends IBaseService<File,Integer> {

    /**
     * 新增或修改
     * @param file
     */
    void saveOrUpdate(File file);

}

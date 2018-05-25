package com.sppan.base.service;

import com.sppan.base.entity.DeptFile;
import com.sppan.base.service.support.IBaseService;

public interface IDeptFileService extends IBaseService<DeptFile,Integer> {

    /**
     * 新增或修改
     * @param file
     */
    void saveOrUpdate(DeptFile file);

    /**
     * 逻辑删除
     * @param file
     */
    void logicDel(DeptFile file);

}

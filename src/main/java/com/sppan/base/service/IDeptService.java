package com.sppan.base.service;

import com.sppan.base.entity.Dept;
import com.sppan.base.service.support.IBaseService;

public interface IDeptService extends IBaseService<Dept,Integer>{

    /**
     * 新增或修改
     * @param dept
     */
    void saveOrUpdate(Dept dept);
}

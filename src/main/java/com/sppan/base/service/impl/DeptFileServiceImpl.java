package com.sppan.base.service.impl;

import com.sppan.base.dao.IDeptFileDao;
import com.sppan.base.dao.IFileDao;
import com.sppan.base.dao.support.IBaseDao;
import com.sppan.base.entity.DeptFile;
import com.sppan.base.entity.File;
import com.sppan.base.service.IDeptFileService;
import com.sppan.base.service.IFileService;
import com.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptFileServiceImpl extends BaseServiceImpl<DeptFile,Integer> implements IDeptFileService{

    @Autowired
    private IDeptFileDao deptFileDao;

    @Override
    public IBaseDao<DeptFile, Integer> getBaseDao() {
        return this.deptFileDao;
    }

    @Override
    public void saveOrUpdate(DeptFile file) {
        save(file);
    }

    @Override
    public void logicDel(DeptFile file) {
        update(file);
    }
}

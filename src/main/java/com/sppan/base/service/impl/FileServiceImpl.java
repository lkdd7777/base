package com.sppan.base.service.impl;

import com.sppan.base.dao.IFileDao;
import com.sppan.base.dao.support.IBaseDao;
import com.sppan.base.entity.File;
import com.sppan.base.service.IFileService;
import com.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl extends BaseServiceImpl<File,Integer> implements IFileService{

    @Autowired
    private IFileDao fileDao;

    @Override
    public IBaseDao<File, Integer> getBaseDao() {
        return this.fileDao;
    }

    @Override
    public void saveOrUpdate(File file) {
        if(file.getId() != null){
            File dbFile = find(file.getId());
            dbFile.setDeleteStatus(file.getDeleteStatus());
            dbFile.setDescription(file.getDescription());

            update(dbFile);
        }else{
            file.setCreateTime(new Date());
            file.setDeleteStatus(0);

            save(file);
        }
    }

}

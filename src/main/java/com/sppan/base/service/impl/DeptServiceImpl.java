package com.sppan.base.service.impl;

import com.sppan.base.dao.IDeptDao;
import com.sppan.base.dao.support.IBaseDao;
import com.sppan.base.entity.Dept;
import com.sppan.base.service.IDeptService;
import com.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept,Integer> implements IDeptService{

    @Autowired
    private IDeptDao deptDao;

    @Override
    public void saveOrUpdate(Dept dept) {
        if(dept.getId() != null){
            Dept dbDept = find(dept.getId());
            dbDept.setUpdateTime(new Date());
            dbDept.setName(dept.getName());
            dbDept.setDescription(dept.getDescription());
            dbDept.setUpdateTime(new Date());
            dbDept.setStatus(dept.getStatus());
            update(dbDept);
        }else{
            dept.setCreateTime(new Date());
            dept.setUpdateTime(new Date());
            save(dept);
        }
    }

    @Override
    public IBaseDao<Dept, Integer> getBaseDao() {
        return this.deptDao;
    }
}

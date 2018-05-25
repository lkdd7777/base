package com.sppan.base.service.impl;

import com.sppan.base.dao.ILogDao;
import com.sppan.base.dao.support.IBaseDao;
import com.sppan.base.entity.Log;
import com.sppan.base.service.ILogService;
import com.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends BaseServiceImpl<Log,Integer> implements ILogService{

    @Autowired
    private ILogDao logDao;

    @Override
    public IBaseDao<Log, Integer> getBaseDao() {
        return this.logDao;
    }
}

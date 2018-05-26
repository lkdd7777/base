package com.sppan.base.service.impl;

import com.sppan.base.common.log.LoggerUtil;
import com.sppan.base.dao.ILogDao;
import com.sppan.base.dao.support.IBaseDao;
import com.sppan.base.entity.Log;
import com.sppan.base.entity.User;
import com.sppan.base.service.ILogService;
import com.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class LogServiceImpl extends BaseServiceImpl<Log,Integer> implements ILogService{

    @Autowired
    private ILogDao logDao;

    @Override
    public IBaseDao<Log, Integer> getBaseDao() {
        return this.logDao;
    }

    @Override
    public void saveLog(User user, HttpServletRequest request, String remark) {
        Log log = new Log();
        log.setUser(user);
        log.setIp(LoggerUtil.getCliectIp(request));
        log.setCreateTime(new Date());
        log.setRemark(remark);
        save(log);
    }
}

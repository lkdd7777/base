package com.sppan.base.service;

import com.sppan.base.entity.Log;
import com.sppan.base.entity.User;
import com.sppan.base.service.support.IBaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface ILogService extends IBaseService<Log,Integer> {

    void saveLog(User user, HttpServletRequest request, String remark);
}

package com.sppan.base.dao;

import com.sppan.base.dao.support.IBaseDao;
import com.sppan.base.entity.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IFileDao extends IBaseDao<File,Integer>{

}

package com.hww.ucenter.common.dao.impl;

import com.hww.ucenter.common.dao.UCenterLoginDao;
import com.hww.ucenter.common.entity.UCenterLogin;

import org.springframework.stereotype.Repository;

@Repository("uCenterLoginDao")
public class UCenterLoginDaoImpl extends LocalEntityDaoImpl<Long, UCenterLogin> implements UCenterLoginDao {

}

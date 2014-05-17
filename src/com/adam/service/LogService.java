package com.adam.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.adam.dao.LogDao;
import com.adam.model.Log;

@Service("logService")
public class LogService {

	@Resource(name = "logDao")
	private LogDao logDao;
	
	
	/**
	 * 获取订单列表
	 * @param start
	 * @param size
	 * @return
	 */
	public List<Log> getLogs(int start, int size) {
		return logDao.getList(start, size);
	}

	/**
	 * 新增订单
	 * @param bills
	 * @return
	 */
	public int addLogs(Log log) {
		return logDao.saveLog(log);
	}

}

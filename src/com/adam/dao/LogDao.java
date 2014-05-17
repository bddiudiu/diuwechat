package com.adam.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adam.model.Log;

@Component("logDao")
public class LogDao extends BaseDao {
	

	public int saveLog(Log log){
		return this.writerSqlSession.insert(
				"com.adam.dao.LogDao.addLog", log);
	}
	
	public List<Log> getList(int start, int size){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("size", size);
		return this.readSqlSession.selectList("com.adam.dao.LogDao.getList",map);
	}
}

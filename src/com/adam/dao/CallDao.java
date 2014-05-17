package com.adam.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adam.model.Call;

@Component("callDao")
public class CallDao extends BaseDao {

	public Call findByID(String id) {
		return this.readSqlSession.selectOne(
				"com.adam.dao.CallDao.selectByid", id);
	}

	public Call findByWx(String type) {
		return this.readSqlSession.selectOne(
				"com.adam.dao.CallDao.selectByWx", type);
	}

	public List<Call> findBytype(String type) {
		return this.readSqlSession.selectList(
				"com.adam.dao.CallDao.selectBytype", type);
	}

	public Call findByState(String state) {
		return this.readSqlSession.selectOne(
				"com.adam.dao.CallDao.selectBystate", state);
	}

	public List<Call> findCall(int start, int size, Call call) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("size", size);
		map.put("call", call);
		return this.readSqlSession.selectList(
				"com.adam.dao.CallDao.selectCall", map);
	}

	public int addCall(Call call) {
		return this.writerSqlSession.insert(
				"com.adam.dao.CallDao.addCall", call);
	}

	public int deleteById(String id) {
		return this.writerSqlSession.delete(
				"com.adam.dao.CallDao.deleteById", id);
	}

	public int updateCall(Call call) {
		return this.writerSqlSession.update(
				"com.adam.dao.CallDao.updateCall", call);
	}

	public int updateCallByType(String type) {
		return this.writerSqlSession.update(
				"com.adam.dao.CallDao.updateCallByType", type);
	}
}

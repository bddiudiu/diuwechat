package com.adam.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adam.model.Bills;

@Component("billsDao")
public class BillsDao extends BaseDao {

	public Bills findById(int id) {
		return this.readSqlSession.selectOne(
				"com.adam.dao.BillsDao.selectById", id);
	}

	public List<Bills> findBills(int start, int size, Bills bills) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("size", size);
		map.put("bills", bills);
		return this.readSqlSession.selectList(
				"com.adam.dao.BillsDao.selectBills", map);
	}

	public List<Bills> findBillslist(int start, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("size", size);
		return this.readSqlSession.selectList(
				"com.adam.dao.BillsDao.selectBillslist", map);
	}

	public int addBills(Bills bills) {
		return this.writerSqlSession.insert(
				"com.adam.dao.BillsDao.addBills", bills);
	}

	public int deleteById(String id) {
		return this.writerSqlSession.delete(
				"com.adam.dao.BillsDao.deleteById", id);
	}

}

package com.adam.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.adam.dao.BillsDao;
import com.adam.dao.MessageDao;
import com.adam.model.Bills;
import com.adam.model.Message;

@Service("billsService")
public class BillsService {

	@Resource(name = "billsDao")
	private BillsDao billsDao;
	
	@Resource(name = "messageDao")
	private MessageDao messageDao;
	
	/**
	 * 获取订单列表
	 * @param start
	 * @param size
	 * @return
	 */
	public List<Bills> listBills(int start, int size) {
		return billsDao.findBillslist(start, size);
	}

	/**
	 * 获取订单列表-搜索方法用
	 * @param start
	 * @param size
	 * @param bills
	 * @return
	 */
	public List<Bills> listBills(int start, int size, Bills bills) {
		return billsDao.findBills(start, size, bills);
	}

	/**
	 * 根据订单号查询
	 * @param id
	 * @return
	 */
	public Bills findById(int id) {
		return billsDao.findById(id);
	}

	/**
	 * 新增订单
	 * @param bills
	 * @return
	 */
	public int addBills(Bills bills) {
		return billsDao.addBills(bills);
	}

	/**
	 * 删除订单
	 * @param id
	 * @return
	 */
	public int deleteBills(String id) {
		return billsDao.deleteById(id);
	}
	
	/**
	 * 根据用户名获取信息内容
	 * @param fromusername
	 * @return
	 */
	public List<Message> getMsg(String fromusername){
		return messageDao.getMsg(fromusername);
	}

}

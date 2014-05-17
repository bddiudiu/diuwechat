package com.adam.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.adam.dao.CallDao;
import com.adam.model.Call;
import com.adam.util.UuidGenerator;

@Service("callService")
public class CallService {

	@Resource(name = "callDao")
	private CallDao callDao;

	/**
	 * 回复信息列表
	 * 
	 * @param start
	 *            起始数据条数
	 * @param size
	 *            展示数据每页的大小
	 */
	public List<Call> listCall(int start, int size, Call call) {
		return callDao.findCall(start, size, call);
	}

	/**
	 * 根据类型查找
	 * 
	 * @param type
	 *            类型
	 * @return 后台返回内容
	 */
	public List<Call> findBytype(String type) {
		return callDao.findBytype(type);
	}

	/**
	 * 根据ID查找回复内容
	 * @param id
	 * @return
	 */
	public Call findByid(String id) {
		return callDao.findByID(id);
	}

	/**
	 * 根据状态查找返回内容
	 * @param state
	 * @return
	 */
	public List<Call> findByState(String state) {
		return callDao.findBytype(state);
	}

	/**
	 * 保存回复内容
	 * @param call
	 * @return
	 */
	public int addCall(Call call) {
		return callDao.addCall(call);
	}

	/**
	 * 删除回复内容
	 * @param id
	 * @return
	 */
	public int deleteCall(String id) {
		return callDao.deleteById(id);
	}

	/**
	 * 更新回复内容
	 * @param call
	 * @return
	 */
	public int updateCall(Call call) {
		return callDao.updateCall(call);
	}

	/**
	 * 更新回复内容的启用状态
	 * @param type
	 * @return
	 */
	public int updateCallByType(String type) {
		return callDao.updateCallByType(type);
	}
	
	/**
	 * 根据类型获取回复内容
	 * @param type
	 * @return
	 */
	public Call getCall(String type) {
		return callDao.findByWx(type);
	}

}

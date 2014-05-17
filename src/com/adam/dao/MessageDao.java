package com.adam.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adam.model.Message;

@Component("messageDao")
public class MessageDao extends BaseDao {

	public int addMessage(Message message) {
		return this.writerSqlSession.insert(
				"com.adam.dao.MessageDao.addMessage", message);
	}

	public List<Message> findMessage(int start, int size) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("size", size);
		return this.readSqlSession.selectList(
				"com.adam.dao.MessageDao.selectMessage", map);
	}
	
	public List<Message> getMsg(String fromusername){
		return this.readSqlSession.selectOne("com.adam.dao.MessageDao.selectMessagebyWx", fromusername);
	}
	
	public Message getMsgById(int id){
		return this.readSqlSession.selectOne("com.adam.dao.MessageDao.selectMessageById", id);
	}

}

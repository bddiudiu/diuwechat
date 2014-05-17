package com.adam.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.adam.model.Article;
import com.adam.model.Reply;

@Component("replyDao")
public class ReplyDao extends BaseDao {

	public void addReply(Reply reply) {
		this.writerSqlSession.insert("com.adam.dao.ReplyDao.addReply",
				reply);
	}

	public void addArticle(Article article) {
		this.writerSqlSession.insert("com.adam.dao.ReplyDao.addArticle",
				article);
	}

	public List<Reply> findReply(int start, int size) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("size", size);
		return this.readSqlSession.selectList(
				"com.adam.dao.ReplyDao.selectReply", map);
	}

}

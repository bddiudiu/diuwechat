package com.adam.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.adam.dao.BillsDao;
import com.adam.dao.CallDao;
import com.adam.dao.MessageDao;
import com.adam.dao.ReplyDao;
import com.adam.model.Article;
import com.adam.model.Bills;
import com.adam.model.Call;
import com.adam.model.Message;
import com.adam.model.Reply;

@Service("weixinService")
public class WeixinService {

	@Resource(name = "messageDao")
	private MessageDao messageDao;

	@Resource(name = "replyDao")
	private ReplyDao replyDao;

	@Resource(name = "callDao")
	private CallDao callDao;

	@Resource(name = "billsDao")
	private BillsDao billsDao;

	/**
	 * 新增Message对象到数据库中
	 */
	public void addMessage(Message message) {
		Date date = new Date();
		message.setCreateTime(date);
		messageDao.addMessage(message);
	}

	/**
	 * 新增Message对象到数据库中
	 */
	public int addBills(Bills bills) {
		return billsDao.addBills(bills);
	}

	/**
	 * 将数据库中Message数据分页查出
	 * 
	 * @param start
	 *            其实数据条数
	 * @param size
	 *            展示数据每页的大小
	 */
	public List<Message> listMessage(int start, int size) {
		return messageDao.findMessage(start, size);
	}

	public List<Reply> listReply(int start, int size) {
		return replyDao.findReply(start, size);
	}


	

	/**
	 * 保存回复消息至数据库中，如果为news类型消息将article一并保存
	 */
	public void addReply(Reply reply) {
		replyDao.addReply(reply);
		if (Reply.NEWS.equals(reply.getMsgType())
				&& null != reply.getArticles()) {
			List<Article> articles = reply.getArticles();
			for (Article a : articles) {
				a.setReplyId(reply.getId());
				replyDao.addArticle(a);
			}
		}
	}
	
	
	public Message selectMsgById(int id){
		return messageDao.getMsgById(id);
	}

}

package com.adam.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.adam.model.Bills;
import com.adam.model.Call;
import com.adam.model.Message;
import com.adam.model.Reply;
import com.adam.service.BillsService;
import com.adam.service.CallService;
import com.adam.service.WeixinService;
import com.adam.util.WeixinUtil;

/**
 * 包含回复列表菜单内的所有操作
 */
@Controller
public class CallController {

	public static int pagesize = 10;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Resource(name = "weixinService")
	private WeixinService weixinService;
	
	@Resource(name = "billsService")
	private BillsService billsService;
	
	@Resource(name = "callService")
	private CallService callService;

	
	/**
	 * 获取回复列表
	 * @param pagenum
	 * @param call
	 * @return
	 */
	@RequestMapping(value = "/manager/calls", method = RequestMethod.GET)
	public ModelAndView listCall(String pagenum, Call call) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("calllist");
		mv.addObject("sidebar", "call");
		int num = 1;
		if (null != pagenum) {
			num = Integer.parseInt(pagenum);
		}
		List<Call> list = callService.listCall((num - 1) * pagesize, pagesize,
				call);
		mv.addObject("callList", list);
		mv.addObject("length", list.size());
		mv.addObject("pagenum", num);
		mv.addObject("call", call);
		return mv;
	}

	
	/**
	 * 更新回复的启用状态
	 * @param type
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/manager/state", method = RequestMethod.GET)
	public ModelAndView updatestate(String type, String id) {
		ModelAndView mv = new ModelAndView();
		callService.updateCallByType(type);
		Call call = callService.findByid(id);
		call.setType("1");
		callService.updateCall(call);
		mv.setViewName("redirect:/manager/calls");
		return mv;
	}

	/**
	 * 获取回复信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/manager/calldetail", method = RequestMethod.GET)
	public ModelAndView callDetail(String id) {
		ModelAndView mv = new ModelAndView();
		Call call = callService.findByid(id);
		if (null == call) {
			mv.setViewName("redirect:/manager/calls");
		} else {
			mv.setViewName("calldetail");
			mv.addObject("sidebar", "call");
			mv.addObject("call", call);
		}
		return mv;
	}

	
	/**
	 * 添加回复跳转方法
	 * @return
	 */
	@RequestMapping(value = "/manager/toadd", method = RequestMethod.GET)
	public ModelAndView addCallPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addcall");
		mv.addObject("sidebar", "call");
		return mv;
	}

	/**
	 * 添加回复方法
	 * @param call
	 * @return
	 */
	@RequestMapping(value = "/manager/addcall", method = RequestMethod.POST)
	public ModelAndView addCall(Call call) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/manager/calls");
		call.setPic("");
		call.setPicurl("");
		callService.addCall(call);
		mv.addObject("notice", "add");
		return mv;
	}
	
	/**
	 * 添加回复方法
	 * @param call
	 * @return
	 */
	@RequestMapping(value = "/manager/updatecall", method = RequestMethod.POST)
	public ModelAndView update(Call call) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/manager/calls");
		if (call.getType() == "1" || "1".equalsIgnoreCase(call.getType())) {
			callService.updateCallByType(call.getType());
			call.setType("1");
		}
		call.setPic("");
		call.setPicurl("");
		callService.updateCall(call);
		mv.addObject("notice", "update");
		return mv;
	}
	

	/**
	 * 删除回复
	 * @param id
	 * @return mv
	 */
	@RequestMapping(value = "/manager/delcall", method = RequestMethod.GET)
	public ModelAndView deletemessage(String id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/manager/calls");
		callService.deleteCall(id);
		mv.addObject("notice", "del");
		return mv;
	}
	
	/**
	 * 收到消息列表页面
	 * @param pagenum
	 * @return 
	 */
	@RequestMapping(value = "/manager/messages", method = RequestMethod.GET)
	public ModelAndView listMessage(String pagenum) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("sidebar", "messages");
		mv.setViewName("messages");
		int num = 1;
		if (null != pagenum) {
			num = Integer.parseInt(pagenum);
		}
		List<Message> list = weixinService.listMessage((num - 1) * pagesize,
				pagesize);
		mv.addObject("messageList", list);
		mv.addObject("pagenum", num);
		mv.addObject("length", list.size());
		return mv;
	}
	
	/**
	 * 查看消息详情
	 * @param id
	 * @return 
	 */
	@RequestMapping(value = "/manager/detail" , method = RequestMethod.GET)
	public ModelAndView detail(int id){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("detail");
		Message message = weixinService.selectMsgById(id);
		mv.addObject("message", message);
		return mv;
	}

	/**
	 * 回复消息列表页面
	 * @param pagenum
	 * @return 
	 */
	@RequestMapping(value = "/manager/replys", method = RequestMethod.GET)
	public ModelAndView listReply(String pagenum) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("sidebar", "replys");
		mv.setViewName("replys");
		int num = 1;
		if (null != pagenum) {
			num = Integer.parseInt(pagenum);
		}
		List<Reply> list = weixinService.listReply((num - 1) * pagesize,
				pagesize);
		mv.addObject("replyList", list);
		mv.addObject("pagenum", num);
		mv.addObject("length", list.size());
		return mv;
	}
	
	/**
	 * 回复方法
	 * @param content 内容
	 * @param fromusername   回复人
	 * @return
	 */
	@RequestMapping(value = "/addmessage" , method = RequestMethod.POST)
	public ModelAndView addmessage(String content,String fromusername){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/manager/leavebills");
		Reply reply = new Reply();
		reply.setToUserName(fromusername);
		reply.setFromUserName("gh_2e0229510b18");
		reply.setCreateTime(new Date());
		reply.setMsgType(Reply.TEXT);
		reply.setContent(content);
		weixinService.addReply(reply);// 保存回复消息到数据库
		// 将回复消息序列化为xml形式
		String back = WeixinUtil.replyToXml(reply);
		System.out.println(back);
		return mv;
	}
	
	/**
	 * 订单列表页面
	 */
	@RequestMapping(value = "/manager/getbills", method = RequestMethod.GET)
	public ModelAndView listBills(String pagenum,Bills bills) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("sidebar", "bill");
		mv.setViewName("bills");
		int num = 1;
		if (null != pagenum) {
			num = Integer.parseInt(pagenum);
		}
		List<Bills> list = billsService.listBills((num - 1) * pagesize,
				pagesize ,bills);
		mv.addObject("messageList", list);
		mv.addObject("bills",bills);
		mv.addObject("pagenum", num);
		mv.addObject("length", list.size());
		return mv;
	}
	
	/**
	 * 订单回复页面
	 */
//	@RequestMapping(value = "/manager/leavebills", method = RequestMethod.GET)
//	public ModelAndView leavebills(int id) {
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("sidebar", "bill");
//		Bills bills = billsService.findById(id);
//		List<Message> list = billsService.getMsg(bills.getFromUserName());
//		mv.setViewName("leavebill");
//		mv.addObject("bill", bills);
//		mv.addObject("list", list);
//		return mv;
//	}
	

}

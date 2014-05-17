package com.adam.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.adam.model.Article;
import com.adam.model.Bills;
import com.adam.model.Call;
import com.adam.model.Message;
import com.adam.model.Reply;
import com.adam.service.BillsService;
import com.adam.service.CallService;
import com.adam.service.WeixinService;
import com.adam.util.AiBangUtil;
import com.adam.util.BaiduUtil;
import com.adam.util.Simsimi;
import com.adam.util.UuidGenerator;
import com.adam.util.WeixinUtil;
import com.adam.util.YoudaoUtil;
import com.sina.sae.storage.SaeStorage;
import com.sina.sae.util.SaeUserInfo;
import com.sun.org.apache.xml.internal.security.utils.ElementCheckerImpl.FullChecker;

@Controller()
public class WeixinController {

	private static final String TOKEN = "adam";

	Logger logger = Logger.getLogger(WeixinController.class);

	public static int pagesize = 10;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Resource(name = "weixinService")
	private WeixinService weixinService;

	@Resource(name = "billsService")
	private BillsService billsService;

	@Resource(name = "callService")
	private CallService callService;

	// @RequestMapping(value="/test",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	// @ResponseBody
	// public String test(HttpServletRequest request){
	// return weixinService.getStudentMessageHistoryByStudentId(30202);
	// }

	// 接收微信公众号接收的消息，处理后再做相应的回复
	@RequestMapping(value = "/weixin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String replyMessage(HttpServletRequest request) throws IOException {
		// 仅处理微信服务端发的请求
		if (checkWeixinReques(request)) {
			logger.info("replyWechat");
			//解析微信提交的xml 封装成map
			Map<String, String> requestMap = WeixinUtil.parseXml(request);
			//解析map对象到 message 实体
			Message message = WeixinUtil.mapToMessage(requestMap);
			weixinService.addMessage(message);// 保存接受消息到数据库
			String replyContent = "";//返回的内容
			String type = message.getMsgType(); //接受的消息类型
			
			// 拼装回复消息
			Reply reply = new Reply();
			reply.setToUserName(message.getFromUserName());
			reply.setFromUserName(message.getToUserName());
			reply.setCreateTime(new Date());
			try {
				if (type.equals(Message.TEXT)) {
					// 仅处理文本回复内容
					String content = message.getContent();// 消息内容
					String[] cs = content.split("#");// 消息内容都以下划线_分隔
					String style = cs[0] != null ? cs[0] : "";
					System.out.println("this is your style " + style);
					if ("dd".equalsIgnoreCase(style)) {
						reply.setMsgType(Reply.TEXT);
						replyContent = getBills(cs,message);
					} else if ("f".equalsIgnoreCase(style)) {
						//处理翻译
						reply.setMsgType(Reply.TEXT);
						replyContent = YoudaoUtil.getYouDaoValue(content.replaceAll("f#", ""));
					} else if ("tq".equalsIgnoreCase(style)) {
						//百度天气
						reply.setMsgType(Reply.NEWS);
						List<Article> alist = BaiduUtil.getWeather(content.replaceAll("tq#", ""),reply);
						reply.setArticleCount(alist.size());
						reply.setArticles(alist);
					} else if ("jt".equalsIgnoreCase(style)) {
						reply.setMsgType(Reply.TEXT);
						replyContent = BaiduUtil.getTrafficEvent(content.replaceAll("jt#", ""));
					} else if ("yp".equalsIgnoreCase(style)) {
						//百度热映
						reply.setMsgType(Reply.NEWS);
						List<Article> alist = BaiduUtil.getMovie(content.replaceAll("yp#", ""),reply);
						reply.setArticleCount(alist.size());
						reply.setArticles(alist);
					} else if ("gj".equalsIgnoreCase(style)) {
						//公交查询
						reply.setMsgType(Reply.TEXT);
						replyContent = AiBangUtil.getBus(content.replaceAll("gj#", ""));
					}
					
					else {
						reply.setMsgType(Reply.TEXT);
						//聊天机器人预留位置
//						Call call = callService.getCall(style);
//						if (call != null) {
//							replyContent = call.getContent();
//						} else {
//							replyContent = "回复格式错误,请回复HELP查询.from style";
//						}
						replyContent = Simsimi.getSim(content);
					}
				} else if ("image".equalsIgnoreCase(type)) {
					//处理图片内容
					reply.setMsgType(Reply.IMAGE);
//					replyContent = "您的图片我们已经收到,感谢您的支持.";
					reply.setMediaid(message.getMsgId());
					logger.debug("图片完毕" + replyContent);
				} else if ("location".equalsIgnoreCase(type)) {
					reply.setMsgType(Reply.TEXT);
					//处理位置信息
					replyContent = "您的位置我们已经收到,感谢您的支持.";
				} else if ("event".equalsIgnoreCase(type)) {
					reply.setMsgType(Reply.TEXT);
					//处理事件
					if ("subscribe".equalsIgnoreCase(message.getEvent())) {
						//关注事件
						Call call = callService.getCall("welcome");
						replyContent = call.getContent();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e);
				reply.setMsgType(Reply.TEXT);
				replyContent = "回复格式错误,请回复HELP查询.from catch";;
			}
			
			reply.setContent(replyContent);
			System.out.println("输出的返回信息:" + replyContent);
			weixinService.addReply(reply);// 保存回复消息到数据库
			// 将回复消息序列化为xml形式
			String back = WeixinUtil.replyToXml(reply);
			System.out.println(back);
			return back;
		} else {
			return "error";
		}
	}

	//订单的逻辑代码
	private String getBills(String[] cs,Message message) {
		String replyContent = "";
			if (cs.length == 1) {
				replyContent = "回复格式错误,请回复HELP查询.from dd";
			} else {
				String id = UuidGenerator.generate36UUID();
				String goods = message.getContent();
				String tel = "";
				String name = "";
				String ordertime = "";
				switch (cs.length) {
				case 2:
					goods = cs[1];
					break;
				case 3:
					goods = cs[1];
					tel = cs[2];
				case 4:
					goods = cs[1];
					tel = cs[2];
					name = cs[3];
				case 5:
					goods = cs[1];
					tel = cs[2];
					name = cs[3];
					ordertime = cs[4];
				default:
				}
				String date = sdf.format(new Date());
				Bills bills = new Bills();
				bills.setOrderid(id);
				bills.setGoods(goods);
				bills.setName(name);
				bills.setTel(tel);
				bills.setDate(date);
				bills.setOrdertime(ordertime);
				int i = weixinService.addBills(bills);

				if (i == 0) {
					replyContent = "回复格式错误,请回复HELP查询.from dd";

				} else {
					replyContent = "您的订单为" + goods + ",订单号为" + id
							+ ",请凭订单号查询.";
				}
			}
		return replyContent;
	}

	// 微信公众平台验证url是否有效使用的接口
	@RequestMapping(value = "/weixin", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String initWeixinURL(HttpServletRequest request) {
		String echostr = request.getParameter("echostr");
		if (checkWeixinReques(request) && echostr != null) {
			return echostr;
		} else {
			return "error";
		}
	}

	/**
	 * 根据token计算signature验证是否为weixin服务端发送的消息
	 */
	private static boolean checkWeixinReques(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		if (signature != null && timestamp != null && nonce != null) {
			String[] strSet = new String[] { TOKEN, timestamp, nonce };
			java.util.Arrays.sort(strSet);
			String key = "";
			for (String string : strSet) {
				key = key + string;
			}
			String pwd = WeixinUtil.sha1(key);
			return pwd.equals(signature);
		} else {
			return true;
		}
	}

	public String sendWechat(String content) {
		System.out.println(content);
		return content;
	}

	
	//上传功能 未测试
	@SuppressWarnings("finally")
	public String upload(HttpServletRequest request) throws IOException {
		logger.debug("进入图片上传");
		request.setCharacterEncoding("utf-8");
		// 使用SaeUserInfo拿到改请求可写的路径
		String realPath = SaeUserInfo.getSaeTmpPath() + "/";
		File fullFile = null;
		try {
			// 使用common-upload上传文件至这个路径中
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				return "";
			}
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(1024 * 1024);
			List<FileItem> items = null;
			items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					fullFile = new File(item.getName());
					File uploadFile = new File(realPath, fullFile.getName());
					item.write(uploadFile);
					// 上传完毕后 使用SaeStorage往storage里面写
					SaeStorage ss = new SaeStorage();
					// 使用upload方法上传到域为image下
					ss.upload("diuwx", realPath + fullFile.getName(),
							fullFile.getName());
				}
			}
		} catch (Exception e) {
			logger.info("ERROR:" + e.getMessage() + "</br>");
		} finally {
			return realPath + fullFile.getName();
		}
	}

	
	public String upload(String url) throws IOException{
		File f = new File(url);  
		String name = f.getName();
		
		BufferedImage im = javax.imageio.ImageIO.read(f); //构造Image对象
		
		/*原始图像的宽度和高度*/
		int width = im.getWidth();
		int height = im.getHeight();
		/*调整后的图片的宽度和高度*/
		int toWidth = (int) (Float.parseFloat(String.valueOf(width)) / 2);
		int toHeight = (int) (Float.parseFloat(String.valueOf(height)) / 2);
		/*新生成结果图片*/
		BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
		result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		ImageIO.write(result,"jpg",out);
		
		byte[] data = out.toByteArray();
		
		SaeStorage storage = new SaeStorage();

		storage.write("image" , name,data);

		return storage.getUrl("diuwx", name);
	}
	
}

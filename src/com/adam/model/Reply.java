package com.adam.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 回复用户的消息
 * 
 * @author ADAM
 */
public class Reply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 416946480611617869L;
	public static final String TEXT = "text";     //文字消息
	public static final String MUSIC = "music";   //音乐
	public static final String NEWS = "news";     //图文
	public static final String IMAGE = "image";   //图片
	public static final String VOICE = "voice";   //语音
	public static final String VIDEO = "video";   //视频

	@XStreamOmitField
	private int id;// 数据库存储id

	// 开发者微信号
	@XStreamAlias("ToUserName")
	private String toUserName;
	// 发送方帐号（一个OpenID）
	@XStreamAlias("FromUserName")
	private String fromUserName;
	// 消息创建时间
	@XStreamAlias("CreateTime")
	private Date createTime;
	// 消息类型（text/music/news/image/link/location）
	@XStreamAlias("MsgType")
	private String msgType;

	// 回复的消息内容,长度不超过2048字节 (文本消息专有)
	@XStreamAlias("Content")
	private String content;
	
	//媒体编号  图片 语音 视频 公用
	@XStreamAlias("MediaId")
	private String mediaid;
	
	//标题 视频 音乐 公用
	@XStreamAlias("Title")
	private String title;
	
	//描述 视频 音乐 公用
	@XStreamAlias("Description")
	private String description;
	
	// 音乐链接 (音乐消息专有)
	@XStreamAlias("MusicUrl")
	private String musicUrl;
	// 高质量音乐链接，WIFI环境优先使用该链接播放音乐 (音乐消息专有)
	@XStreamAlias("HQMusicUrl")
	private String hQMusicUrl;

	// 图文消息个数，限制为10条以内 (图文消息专有)
	@XStreamAlias("ArticleCount")
	private int articleCount;

	// 多条图文消息信息，默认第一个item为大图
	@XStreamAlias("Articles")
	private List<Article> articles;

	public static void main(String[] args) {
		System.out
				.println("http://mmsns.qpic.cn/mmsns/UKMLIAeREF9IyZGhfvF8f0CAKDzEvXwCibLKUM4kmsfGnZvFM7EJlrg/0"
						.length());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String gethQMusicUrl() {
		return hQMusicUrl;
	}

	public void sethQMusicUrl(String hQMusicUrl) {
		this.hQMusicUrl = hQMusicUrl;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public String getMediaid() {
		return mediaid;
	}

	public void setMediaid(String mediaid) {
		this.mediaid = mediaid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

package com.adam.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.net.ssl.SSLEngineResult.Status;

import org.apache.log4j.Logger;

import com.adam.dao.ReplyDao;
import com.adam.model.Article;
import com.adam.model.Reply;
import com.adam.service.WeixinService;
import com.adam.web.controller.WeixinController;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 百度API
 * @author adam
 *
 */
public class BaiduUtil {

	Logger logger = Logger.getLogger(BaiduUtil.class);
	private static String api = "http://api.map.baidu.com/telematics/v3/";
	private static String output = "json";
	private static String ak = "";
	
	
	public static String getURL(String str,String method){
		try {
			str = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer buf = new StringBuffer();
		buf.append("location=");
		buf.append(str);
		buf.append("&output=");
		buf.append(output);
		buf.append("&ak=");
		buf.append(ak);
		String param = buf.toString();
		String urlName = api + method + param;
		System.out.println(urlName);
		return urlName;
	}
	
	public static String getSend(String url){
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			conn.setDoInput(true);
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = br.readLine();
            while (line != null) {
                result += line;
                line = br.readLine();
            }
            br.close();
            return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 调用的方法 
	 * @param str  城市名
	 * @return
	 */
	public static List<Article> getWeather(String str,Reply reply){
		List<Article> alist = new ArrayList<Article>();
		String url = getURL(str,"weather?");
		String json = getSend(url);
		System.out.println("baidu return " + json);
		Gson gson = new Gson();
		Status status = gson.fromJson(json, Status.class);
		if ("0".equalsIgnoreCase(status.getError())) {
			System.out.println("查询日期:"+status.getDate());
			List<Results> list = status.getResults();
			if (list != null) {
				for (Results results : list) {
					System.out.println("所在城市:"+results.getCurrentCity());
					List<Weather> wlist = results.getList();
					if (wlist != null) {
						for (Weather weather : wlist) {
							Article article = new Article();
							article.setTitle(weather.getDate()+"\n"+weather.getWeather()+weather.getWind()+weather.getTemperature());
							article.setPicUrl(weather.getDayPictureUrl());
							article.setDescription("");
							article.setUrl("");
							article.setReply(reply);
							article.setReplyId(reply.getId());
							alist.add(article);
							
						}
					}
				}
			}
		}
		System.out.println(alist.size());
		return alist;
	}
	
	/**
	 * 热映影片
	 * @author adam
	 *
	 */
	public static List<Article> getMovie(String str,Reply reply){
		List<Article> list = new ArrayList<Article>();
		String url = getURL(str,"movie?qt=hot_movie&");
		String json = getSend(url);
		System.out.println("baidu return " + json);
		Gson gson = new Gson();
		pStatus status = gson.fromJson(json, pStatus.class);
		if ("0".equalsIgnoreCase(status.getError())) {
			System.out.println("查询日期:" + status.getDate());
			pResults results = status.getResult();
			System.out.println("所在城市为: " + results.getCityname());
			List<pMovie> mlist = results.getMovie();
			for (pMovie movie : mlist) {
				Article article = new Article();
				article.setTitle(movie.getMovie_name());
				article.setDescription(movie.movie_nation);
				article.setPicUrl(movie.getMovie_picture());
				article.setUrl("");
				article.setReply(reply);
				article.setReplyId(reply.getId());
				list.add(article);
			}
		}
		return list;
	}
	
	
	/**
	 * 交通事件
	 * @param str
	 * @param reply
	 * @return
	 */
	public static String getTrafficEvent(String str){
		StringBuffer sb = new StringBuffer();
		String url = getURL(str,"trafficEvent?");
		String json = getSend(url);
		System.out.println("baidu return " + json);
		try {
			Gson gson = new Gson();
			tStatus status = gson.fromJson(json, tStatus.class);
			if ("0".equalsIgnoreCase(status.getError())) {
				sb.append("查询日期:" + status.getDateTime());
				sb.append("\n查询城市:" + status.getCurrentCity());
				List<tResults> list = status.getResults();
				if (list != null) {
					int i = 0;
					for (tResults results : list) {
						sb.append("\n-------");
						sb.append("\n开始时间:" + results.getStartTime().replaceAll("/", "-"));
						sb.append("\n结束时间:" + results.getEndTime().replaceAll("/", "-"));
						sb.append("\n事件名称:" + results.getTitle());
						sb.append("\n事件描述:" + results.getDescription());
						if ("0".equalsIgnoreCase(results.getType())) {
							sb.append("\n事件类型:管制");
						}else if ("1".equalsIgnoreCase(results.getType())) {
							sb.append("\n事件类型:事故");
						}else if ("2".equalsIgnoreCase(results.getType())) {
							sb.append("\n事件类型:施工");
						}else {
							sb.append("\n事件类型:管制");
						}
						i++;
						if (i > 5) {
							break;
						}
					}
				}
			}else {
				sb.append("您所查询的城市暂无信息!");
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		return sb.toString();
	}
	
	static class pStatus{
		private String error;
		private String status;
		private String date;
		private pResults result;
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public pResults getResult() {
			return result;
		}
		public void setResult(pResults result) {
			this.result = result;
		}
	}
	static class pResults{
		private String cityid;
		private String cityname;
		private Location location;
		private List<pMovie> movie;
		public String getCityid() {
			return cityid;
		}
		public void setCityid(String cityid) {
			this.cityid = cityid;
		}
		public String getCityname() {
			return cityname;
		}
		public void setCityname(String cityname) {
			this.cityname = cityname;
		}
		public Location getLocation() {
			return location;
		}
		public void setLocation(Location location) {
			this.location = location;
		}
		public List<pMovie> getMovie() {
			return movie;
		}
		public void setMovie(List<pMovie> movie) {
			this.movie = movie;
		}
	}
	static class pMovie{
		private String movie_id;
		private String movie_name;
		private String movie_type;
		private String movie_release_date;
		private String movie_nation;
		private String movie_starring;
		private String movie_length;
		private String movie_picture;
		private String movie_score;
		private String movie_director;
		private String movie_tags;
		private String movie_message;
		private String is_imax;
		private String is_new;
		private String movies_wd;
		public String getMovie_id() {
			return movie_id;
		}
		public void setMovie_id(String movie_id) {
			this.movie_id = movie_id;
		}
		public String getMovie_name() {
			return movie_name;
		}
		public void setMovie_name(String movie_name) {
			this.movie_name = movie_name;
		}
		public String getMovie_type() {
			return movie_type;
		}
		public void setMovie_type(String movie_type) {
			this.movie_type = movie_type;
		}
		public String getMovie_release_date() {
			return movie_release_date;
		}
		public void setMovie_release_date(String movie_release_date) {
			this.movie_release_date = movie_release_date;
		}
		public String getMovie_nation() {
			return movie_nation;
		}
		public void setMovie_nation(String movie_nation) {
			this.movie_nation = movie_nation;
		}
		public String getMovie_starring() {
			return movie_starring;
		}
		public void setMovie_starring(String movie_starring) {
			this.movie_starring = movie_starring;
		}
		public String getMovie_length() {
			return movie_length;
		}
		public void setMovie_length(String movie_length) {
			this.movie_length = movie_length;
		}
		public String getMovie_picture() {
			return movie_picture;
		}
		public void setMovie_picture(String movie_picture) {
			this.movie_picture = movie_picture;
		}
		public String getMovie_score() {
			return movie_score;
		}
		public void setMovie_score(String movie_score) {
			this.movie_score = movie_score;
		}
		public String getMovie_director() {
			return movie_director;
		}
		public void setMovie_director(String movie_director) {
			this.movie_director = movie_director;
		}
		public String getMovie_tags() {
			return movie_tags;
		}
		public void setMovie_tags(String movie_tags) {
			this.movie_tags = movie_tags;
		}
		public String getMovie_message() {
			return movie_message;
		}
		public void setMovie_message(String movie_message) {
			this.movie_message = movie_message;
		}
		public String getIs_imax() {
			return is_imax;
		}
		public void setIs_imax(String is_imax) {
			this.is_imax = is_imax;
		}
		public String getIs_new() {
			return is_new;
		}
		public void setIs_new(String is_new) {
			this.is_new = is_new;
		}
		public String getMovies_wd() {
			return movies_wd;
		}
		public void setMovies_wd(String movies_wd) {
			this.movies_wd = movies_wd;
		}
	}
	
	
	static class tStatus{
		private String error;
		private String status;
		private String dateTime;
		private String currentCity;
		private List<tResults> results;
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDateTime() {
			return dateTime;
		}
		public void setDateTime(String dateTime) {
			this.dateTime = dateTime;
		}
		public String getCurrentCity() {
			return currentCity;
		}
		public void setCurrentCity(String currentCity) {
			this.currentCity = currentCity;
		}
		public List<tResults> getResults() {
			return results;
		}
		public void setResults(List<tResults> results) {
			this.results = results;
		}
	}
	
	static class tResults{
		private String startTime;
		private String endTime;
		private String title;
		private String description;
		private String type;
		private Location location;
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
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
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Location getLocation() {
			return location;
		}
		public void setLocation(Location location) {
			this.location = location;
		}
	}
	
	static class Location{
		private String lng;
		private String lat;
		public String getLng() {
			return lng;
		}
		public void setLng(String lng) {
			this.lng = lng;
		}
		public String getLat() {
			return lat;
		}
		public void setLat(String lat) {
			this.lat = lat;
		}
	}

	static class Status{
		private String error;
		private String status;
		private String date;
		private List<Results> results;
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public List<Results> getResults() {
			return results;
		}
		public void setResults(List<Results> results) {
			this.results = results;
		}
	}
	
	static class Results{
		private String currentCity;
		private List<Weather> weather_data;
		public String getCurrentCity() {
			return currentCity;
		}
		public void setCurrentCity(String currentCity) {
			this.currentCity = currentCity;
		}
		public List<Weather> getList() {
			return weather_data;
		}
		public void setList(List<Weather> weather_data) {
			this.weather_data = weather_data;
		}
	}
	
	static class Weather{
		private String date;
		private String dayPictureUrl;
		private String nightPictureUrl;
		private String weather;
		private String wind;
		private String temperature;
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getDayPictureUrl() {
			return dayPictureUrl;
		}
		public void setDayPictureUrl(String dayPictureUrl) {
			this.dayPictureUrl = dayPictureUrl;
		}
		public String getNightPictureUrl() {
			return nightPictureUrl;
		}
		public void setNightPictureUrl(String nightPictureUrl) {
			this.nightPictureUrl = nightPictureUrl;
		}
		public String getWeather() {
			return weather;
		}
		public void setWeather(String weather) {
			this.weather = weather;
		}
		public String getWind() {
			return wind;
		}
		public void setWind(String wind) {
			this.wind = wind;
		}
		public String getTemperature() {
			return temperature;
		}
		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}
	}
	
	public static void main(String[] args) {
		BaiduUtil bu = new BaiduUtil();
		String str = "南京";
//		String JSON = bu.getTrafficEvent(str);
		
		Reply reply = new Reply();
		reply.setMsgType("news");
		List<Article> list = bu.getWeather(str, reply);
		reply.setArticles(list);
		reply.setArticleCount(list.size());
		String xml = WeixinUtil.replyToXml(reply);
//		System.out.println(JSON.getBytes().length);
		
		System.out.println(xml);
//		System.out.println(xml);
		
	}
	
	
	
	
	
	
	
	
}

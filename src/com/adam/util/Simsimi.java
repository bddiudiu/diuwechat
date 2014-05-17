package com.adam.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.google.gson.Gson;


/**
 * 小黄鸭
 * @author adam
 *
 */
public class Simsimi {

	private static String api = "http://sandbox.api.simsimi.com/request.p?ft=1.0";
	private static String lc= "ch";
	private static String ak = "a8d8112e-5e44-40fb-a0d4-91404c5307fc";
	
	public static String getURL(String str){
		try {
			str = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer buf = new StringBuffer();
		buf.append("&lc=");
		buf.append(lc);
		buf.append("&key=");
		buf.append(ak);
		buf.append("&text=");
		buf.append(str);
		String param = buf.toString();
		String urlName = api + param;
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
	
	public static String getSim(String str){
		String callback = "";
		String url = getURL(str);
		String json = getSend(url);
		Gson gson = new Gson();
		Sim sim = gson.fromJson(json, Sim.class);
		if ("100".equalsIgnoreCase(sim.getResult())) {
			callback = sim.getResponse();
		}else {
			callback = "主人我现在不方便聊天哟...";
		}
		return callback;
	}
	
	static class Sim{
		private String result;
		private int id;
		private String response;
		private String msg;
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getResponse() {
			return response;
		}
		public void setResponse(String response) {
			this.response = response;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	
	public static void main(String[] args) {
		Simsimi sim = new Simsimi();
		String str = "你好";
		String url = getURL(str);
		String out = getSend(url);
		System.out.println(out);
	}
}

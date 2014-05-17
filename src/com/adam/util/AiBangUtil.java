package com.adam.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class AiBangUtil {

	private static String api = "http://openapi.aibang.com/bus/lines?";
	private static String key = "";
	private static String sn = "";
	
	
	
	public static String getURL(String str,String zd){
		try {
			str = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer buf = new StringBuffer();
		buf.append("app_key=");
		buf.append(key);
		buf.append("&city=");
		buf.append(str);
		buf.append("&q=");
		buf.append(zd);
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
	
	public static String getBus(String str){
		StringBuffer sb = new StringBuffer();
		String[] cs = str.split("#");
		String url = getURL(cs[0],cs[1]);
		String input = getSend(url);
		System.out.println(input);
		XStream xstream=new XStream(new DomDriver());
		xstream.processAnnotations(Root.class);		
		Root root = (Root) xstream.fromXML(input);
		String result_num = root.getResult_num();
		if (Integer.parseInt(result_num) > 0 ) {
			Lines lines = root.getLines();
			List<Line> list = lines.getLine();
			for (Line line : list) {
				sb.append("线路名称" + line.getName() + "\n");
				sb.append("线路信息" + line.getInfo() + "\n");
				sb.append("站点信息" + line.getStats() + "\n");
				sb.append("============\n");
			}
		}else {
			sb.append("系统暂时无法查询,请检查您的输入.");
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	@XStreamAlias("root")
	static class Root{
		@XStreamAlias("result_num")
		private String result_num;
		@XStreamAlias("web_url")
		private String web_url;
		@XStreamAlias("wap_url")
		private String wap_url;
		@XStreamAlias("lines")
		private Lines lines;
		public String getResult_num() {
			return result_num;
		}
		public void setResult_num(String result_num) {
			this.result_num = result_num;
		}
		public String getWeb_url() {
			return web_url;
		}
		public void setWeb_url(String web_url) {
			this.web_url = web_url;
		}
		public String getWap_url() {
			return wap_url;
		}
		public void setWap_url(String wap_url) {
			this.wap_url = wap_url;
		}
		public Lines getLines() {
			return lines;
		}
		public void setLines(Lines lines) {
			this.lines = lines;
		}
	}
	
	static class Lines{
		@XStreamImplicit(itemFieldName="line")
		private List<Line> line;

		public List<Line> getLine() {
			return line;
		}

		public void setLine(List<Line> line) {
			this.line = line;
		}
		
	}
	
	static class Line{
		@XStreamAlias("name")
		private String name;
		@XStreamAlias("info")
		private String info;
		@XStreamAlias("stats")
		private String stats;
		@XStreamAlias("stat_xys")
		private String stat_xys;
		@XStreamAlias("xys")
		private String xys;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		public String getStats() {
			return stats;
		}
		public void setStats(String stats) {
			this.stats = stats;
		}
		public String getStat_xys() {
			return stat_xys;
		}
		public void setStat_xys(String stat_xys) {
			this.stat_xys = stat_xys;
		}
		public String getXys() {
			return xys;
		}
		public void setXys(String xys) {
			this.xys = xys;
		}
	}
	
	public static void main(String[] args) {
		AiBangUtil a = new AiBangUtil();
		String str = "南通#6";
		String back = getBus(str);
	}
	
}

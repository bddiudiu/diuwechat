package com.adam.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



/**
 * 使用有道词典的API
 * 
 * @author adam
 * 
 */
public class YoudaoUtil {

	private static String url = "http://fanyi.youdao.com/openapi.do";
	private static String keyfrom = "";
	private static String key = "";
	private static String doctype = "xml";

	private static String sendGet(String str) {

		// 编码成UTF-8
		try {
			str = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringBuffer buf = new StringBuffer();
		buf.append("keyfrom=");
		buf.append(keyfrom);
		buf.append("&key=");
		buf.append(key);
		buf.append("&type=data&doctype=");
		buf.append(doctype);
		buf.append("&&callback=show&version=1.1&q=");
		buf.append(str);

		String param = buf.toString();

		String result = "";
		String urlName = url + "?" + param;
		URL realUrl;

		try {
			realUrl = new URL(urlName);
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

	
	public static String getYouDaoValue(String str) {
		String result = null;

		// 发送GET请求翻译
		result = sendGet(str);
		System.out.println(result);
		StringBuffer callback = new StringBuffer();
		callback.append(str);
		// 处理XML中的值
		int re1 = result.indexOf("<errorCode>");
		int re2 = result.indexOf("</errorCode>");
		String in = result.substring(re1 + 11, re2);
		System.out.println("===========翻译是否成功============" + in);

		if (in.equals("0")) {
			System.out.println("翻译正常");
			re1 = result.indexOf("<paragraph><![CDATA[");
			re2 = result.indexOf("]]></paragraph>");
			in = result.substring(re1 + 20, re2);
			System.out.println("==========有道翻译================" + in);
			callback.append("\n ----有道翻译---- \n"+in);

			re1 = result.indexOf("<ex><![CDATA[");
			re2 = result.indexOf("]]></ex>");
			if (re1 > 0 && re2 > re1) {
				in = result.substring(re1 + 13, re2);
				System.out.println("==========有道词典-网络释义================" + in);
				callback.append("\n ----网络释义---- \n"+in);
			}

		} else if (in.equals("20")) {
			System.out.println("要翻译的文本过长");
			callback.append("要翻译的文本过长");
		} else if (in.equals("30")) {
			System.out.println("无法进行有效的翻译");
			callback.append("无法进行有效的翻译");
		} else if (in.equals("40")) {
			System.out.println("不支持的语言类型");
			callback.append("不支持的语言类型");
		} else if (in.equals("50")) {
			System.out.println("无效的key");
			callback.append("无效的key");
		}
		
		return callback.toString();
		
	}
	
	
	public static Map<String, String> parseXml(String str) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new ByteArrayInputStream(str.getBytes("utf-8")));
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			for (Element e : elementList)
				// 遍历xml将数据写入map
				map.put(e.getName(), e.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public String parseJson(String str){
		String out = "";
		String strjson = str.substring(4,str.length()-2);
		
		
		return out;
	}

	public static void main(String[] args) {

		String str = "明天天气如何";
		YoudaoUtil test = new YoudaoUtil();
		String temp = test.getYouDaoValue(str);
//		String temp = test.sendGet(str);
//		Map<String,String> out = parseXml(temp);
		System.out.println(temp);
//		String strjson = temp.substring(5,temp.length()-2);
//		System.out.println(strjson);
	}

}

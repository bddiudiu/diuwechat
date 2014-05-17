package com.adam.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class test {

	public static void main(String[] args) {
		String str = "dd#qe";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String[] cs = str.split("#");
		
		
		String id = UuidGenerator.generate36UUID();
		String goods = "";
		String tel =  "";
		String name =  "";
		String ordertime = "";
		String date = sdf.format(new Date());
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
			id = UuidGenerator.generate36UUID();
		}
		
		
		
		
		
		System.out.println(id + goods + name + tel + ordertime);
		
	}
}

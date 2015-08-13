package com.lazypeople.util;

import java.text.DecimalFormat;


public class MoneyUtil {
	 
	public static Integer Vo2Po(String vom) {
		if(vom==null||vom.trim().equals("")){
			return 0;
		}
		Integer outs = 0;
		if (vom.contains(".")) {
			String[] a = vom.split("\\.");
			if (a[1].length() == 1) {
				outs = Integer.parseInt(a[0]) * 100 + Integer.parseInt(a[1])
						* 10;
			} else if (a[1].length() == 2) {
				outs = Integer.parseInt(a[0]) * 100 + Integer.parseInt((a[1]));
			} else if (a[1].length() > 2) {
				String temp=a[1].substring(2,3);
				if(Integer.parseInt(temp)>=5){
					outs =( Integer.parseInt(a[0]) * 100 + Integer.parseInt((a[1].substring(0, 2))))+1;
				}else{
					outs = Integer.parseInt(a[0]) * 100 + Integer.parseInt((a[1].substring(0, 2)));
				}
			}  

		} else {
			outs = Integer.parseInt(vom)*100;
		}
		return outs;
	}
	
	
	public static String Po2Vo(Integer pom) {
		if(pom==null){
			pom=0;
		}
		StringBuffer temp=new StringBuffer();
		temp.append(pom);
		if(temp.length()==1){
			temp=new StringBuffer("0.0"+pom);
		}else if(temp.length()==2){
			String a="0";
			String b=temp.insert(temp.length()-2, ".").toString();
			temp=new StringBuffer(a+b);
		}else if(temp.length()>2){
			temp.insert(temp.length()-2, ".");
		}
		return temp.toString();
	}
	
	public static int cutOutRedundance(int pom) {
		
		StringBuffer temp=new StringBuffer();
		temp.append(pom);
		String str = "";
		if(temp.length()==1){
			str = new StringBuffer("0").toString();
		}else if(temp.length()==2){
			str = new StringBuffer("0").toString();
		}else if(temp.length()>2){
			str = temp.substring(0,temp.length()-2);
		}
		return Integer.parseInt(str);
	}
	
	public static String format(Double number){
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");  
		return fmt.format(number);
	}
	
	public static void main(String[] args) {
		System.out.println(cutOutRedundance(0));
		System.out.println(Vo2Po("01.00"));
		System.out.println(cutOutRedundance(1000));
		
	}
	 
}

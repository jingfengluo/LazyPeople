package com.lazypeople.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class GenerateOrderNum{

	private static final AtomicLong account = new AtomicLong();
	private static final String dtMilliseconds = "yyyyMMddHHmmssSSS";
	  /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     * 以yyyyMMddHHmmss为格式的当前系统时间
     * @throws InterruptedException 
     */
	public static String getOrderNum() throws InterruptedException{
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtMilliseconds);
		if(account.get()==9999){
			Thread.sleep(1);
			account.set(0);
		}
		if(account.get()<9){
			return df.format(date)+"000"+account.incrementAndGet()+MsgValidateCode.getRandomNum(2);
		}
		if(account.get()<99){
			return df.format(date)+"00"+account.incrementAndGet()+MsgValidateCode.getRandomNum(2);
		}
		if(account.get()<999){
			return df.format(date)+"0"+account.incrementAndGet()+MsgValidateCode.getRandomNum(2);
		}
		if(account.get()<9999){
			return df.format(date)+account.incrementAndGet()+MsgValidateCode.getRandomNum(2);
		}
		return null;
	}
}

package com.lazypeople.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSON;

/**
 * 省电信《浙江云储》短信通道
 * @author Administrator
 *
 */
public class SMSUtil {

	//测试环境账号，URL
	private final static String userId = "1";
	private final static String msgType = "0";// 参数短信 - 普通短信为0，参数短信为1
	private final static String key = "1234567890";
	private final static String isParam = "0"; // 参数短信 - 普通短信为0，参数短信为1
	private final static String submitURL = "http://122.227.254.234:7003/reqin.do?to=reqSendSms";
	private final static String queryURL = "http://122.227.254.234:7003/reqin.do?to=reqSmsQuery";
	private final static String moneyURL = "http://122.227.254.234:7003/reqin.do?to=reqSmsMoney";

	/**
	 * 短信提交请求
	 * 
	 * @param userId
	 *            - 用户账号, 由电信分配
	 * @param orderId
	 *            - 订单号， 由代理商生成，当日订单号必须唯一
	 * @param msgType
	 *            - 消息类型。 0 立即发送 1预约发送
	 * @param dateOrder
	 *            - 预约时间。当msgType为1时有用，格式：yyyyMMddHHmmss
	 * @param msgs
	 *            - phone 接收号码 - 每个号码之间用“,”分隔,最大一次提交300个号码[2013-11-03] content
	 *            消息内容 - 需要使用密钥key对content内容进行标准DES加密,加密采用UTF-8编码转换.
	 *            需要对msgs内容进行encode后再提交，采用utf-8编码 isParam 参数短信 - 普通短信为0，参数短信为1
	 * @return 返回报文结构 {“orderId”:” ”,”status”:” ”,”result”:” “,”sign”:” ”} 返回参数：
	 *         字段 名称 类型 备注 orderId 订单号 String 返回由代理商提交的订单号 status 处理状态 int 参见码表
	 *         result 处理结果 String 处理结果以encode后返回，故需要decode解码 sign 校验码 String
	 *         按照以上参数顺序，进行值的MD5加密，生成32位大写字符串
	 * 
	 *         状态码 备注 1 提交成功 -1 提交失败 -3 资金余额不足 -4 校验码失败 -5 系统错误 -6 订单号重复 -8
	 *         系统维护中 -9 关键字错误 -12 号码数量错误 -13 msgs结构错误
	 */
	public static SMSReturnParams submit(String phones, String msgContent)
			throws Exception {
		SMSReturnParams ret = null;

		String url = "";
		String msgs = "";
		String isParam = "";
		try {
//			System.out.println("SMSUtil.submit: 浙江省电信《浙江云储》网关 短消息,手机号:"+phones+"; 内容 - "+ msgContent);
//			/String msg = new String(msgContent.getBytes("ISO-8859-1"),"UTF-8");
			String content = CryptUtils.encrypt(msgContent, key);
			msgs = "msgs="
					+ URLEncoder.encode("[{\"phone\":\"" + phones
							+ "\",\"content\":\"" + content
							+ "\",\"isParam\":\"" + isParam + "\"}]", "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url.toString());
		StringRequestEntity entity;

		System.out.println("SMSUtil.submit: 浙江省电信《浙江云储》网关 发送短消息调用URL - "+ url.toString());
		entity = new StringRequestEntity(msgs,
				"application/x-www-form-urlencoded", "utf-8");
		method.setRequestEntity(entity);


		int i = client.executeMethod(method); // http返回码。 200-ok;
		 System.out.println("SMSUtil.submit: 浙江省电信《浙江云储》网关 发送短消息结果  - status:" + i + " body:"+ URLDecoder.decode(method.getResponseBodyAsString(), "utf-8"));
		ret = (SMSReturnParams) JSON.parseObject(
				URLDecoder.decode(method.getResponseBodyAsString(), "utf-8"), SMSReturnParams.class);
		ret.setHttpRetCode(i);
		ret.setOther(URLDecoder.decode(method.getResponseBodyAsString(), "utf-8"));

		return ret;
	}

	/**
	 * 短信状态查询
	 * 
	 * @param userId
	 *            - 用户账号, 由电信分配
	 * @param orderId
	 *            - 由代理商生成，短信提交请求的订单号
	 * @param phone
	 *            - 接收号码. 如果不传，则返回整个订单的处理状态
	 * @param date
	 *            - 提交时间。格式为：yyyyMMdd
	 * @return 报文结构：{“orderId”:” ”,”status”:” ”,”result”:” “,”msgs”:[{”phone”:”
	 *         ”,”status”:” ”},{”phone”:” ”,”status”:” ”},{”phone”:”
	 *         ”,”status”:” ”}],”sign”:” ”}
	 *         备注：当phone字段为空，仅当订单处理状态为处理结束，才返回msgs内容包含所有号码的处理状态。
	 * 
	 *         返回参数： 字段 名称 类型 备注 orderId 订单号 String 由代理商生成，短信提交请求的订单号 status
	 *         处理状态 Int 3 处理中 0处理结束 -2 未查询到记录 result 处理结果 String
	 *         处理结果以encode后返回，故需要decode解码 msgs ： phone 发送号码 String status 处理状态
	 *         Int 1成功 2失败 3内容不合法 4进行中 sign 校验码 String
	 *         按照以上参数顺序+key，除了msgs以外内容，对参数值进行md5加密，生成32位大写字符串
	 * 
	 * 
	 *         状态码表 状态码 备注 0 处理机结束 3 处理中 -2 未查询到记录 -4 校验码失败 -5 系统错误 -7 查询过于频繁
	 */
	public static SMSReturnParams query(String orderId, String phone,
			String date) throws Exception {
		SMSReturnParams ret = null;

		// 校验码. 按照以上参数顺序+key，进行值的MD5加密，生成32位大写字符串
		String sign = userId + orderId + phone + date + key;

		StringBuffer url = new StringBuffer(queryURL);
		url.append("&userId=" + userId);
		url.append("&orderId=" + orderId);
		url.append("&date=" + date);
		url.append("&phone=" + phone);
		url.append("&sign=" + KeyedDigestMD5.getKeyedDigest(sign, ""));
		System.out.println("SMSUtil.query: 浙江省电信《浙江云储》网关 查询短消息调用URL - "+url.toString());
		
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url.toString());

		int i = client.executeMethod(method); // http返回码。 200-ok;
		System.out.println("SMSUtil.query: 浙江省电信《浙江云储》网关 查询短消息调用结果 - status:" + i + " body:"+ URLDecoder.decode(method.getResponseBodyAsString(), "utf-8"));
		ret = (SMSReturnParams) JSON.parseObject(
				URLDecoder.decode(method.getResponseBodyAsString(), "utf-8"), SMSReturnParams.class);
		ret.setHttpRetCode(i);
		ret.setOther(URLDecoder.decode(method.getResponseBodyAsString(), "utf-8"));

		return ret;
	}

	/**
	 * 代理商余额查询
	 * 
	 * @param userId
	 *            - 用户账号, 由电信分配
	 * @return 返回参数 字段 名称 类型 备注 money 账号余额 String 精确到分，如1.011元，返回101.1 status
	 *         处理状态 Int 1成功 -1失败 result 处理结果 String 处理结果以encode后返回，故需要decode解码
	 *         sign 校验码 String 按照以上参数顺序+key，进行值的MD5加密，生成32位大写字符串
	 * 
	 *         返回 报文结构：{“money”:” ”,”status”:” ”,”result”:” “,”sign”:” ”}
	 */
	public static SMSReturnParams money() throws Exception {
		SMSReturnParams ret = null;

		// 校验码. 按照以上参数顺序+key，进行值的MD5加密，生成32位大写字符串
		String sign = userId + key;

		StringBuffer url = new StringBuffer(moneyURL);
		url.append("&userId=" + userId);
		url.append("&sign=" + KeyedDigestMD5.getKeyedDigest(sign, ""));
		System.out.println("SMSUtil.money: 浙江省电信《浙江云储》网关 查询短消息余额调用URL - "+url.toString());

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url.toString());


		int i = client.executeMethod(method); // http返回码。 200-ok;
		System.out.println("SMSUtil.money: 浙江省电信《浙江云储》网关 查询短消息余额调用结果 - status:" + i + " body:"+ URLDecoder.decode(method.getResponseBodyAsString(), "utf-8"));
		ret = (SMSReturnParams) JSON.parseObject(
				URLDecoder.decode(method.getResponseBodyAsString(), "utf-8"), SMSReturnParams.class);
		ret.setHttpRetCode(i);
		ret.setOther(URLDecoder.decode(method.getResponseBodyAsString(), "utf-8"));

		return ret;
	}
}

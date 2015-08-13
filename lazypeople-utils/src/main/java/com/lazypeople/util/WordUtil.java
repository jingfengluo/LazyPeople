package com.lazypeople.util;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;

public class WordUtil {
	public static InputStream getWordFile(String name,String xz) throws Exception{
		Document doc = new Document(PageSize.A4);// 创建DOC
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();// 创建新字节输出流
		RtfWriter2.getInstance(doc, byteArrayOut);// 建立一个书写器与document对象关联，通过书写器可以将文档写入到输出流中
		doc.open();// 打开文档
		RtfFont titleFont = new RtfFont("宋体", 14, Font.NORMAL, Color.BLACK);// 标题字体
		RtfFont contextFont = new RtfFont("宋体", 12, Font.NORMAL, Color.BLACK);// 内容字体
		Paragraph title = new Paragraph(name, titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		doc.add(title);
		String kk=matchHTML(xz);
		Paragraph title2 = new Paragraph(kk, contextFont);
		doc.add(title2);
		doc.close();

		ByteArrayInputStream byteArray = new ByteArrayInputStream(
				byteArrayOut.toByteArray());
		byteArrayOut.close();
		return byteArray;
	}
	
	/**
	 * 去掉所有HTML标签
	 */
	public static String matchHTML(String htmlStr) {
		String regEx_html = "<[^>]+>";//定义HTML标签的正则表达式
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");//过滤html标签
        return htmlStr.trim();//返回文本字符串
	}
}

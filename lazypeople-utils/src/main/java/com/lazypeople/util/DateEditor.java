/*
 * @(#)DateEditor.java 2013-12-7下午08:05:25
 * Copyright 2013 sinovatech, Inc. All rights reserved.
 */
package com.lazypeople.util;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.util.StringUtils;

/**
 * 时间属性编辑器
 * <ul>
 * <li>
 * <b>修改历史：</b><br/>
 * <p>
 * [2013-12-7下午08:05:25]joel<br/>
 * TODO
 * </p>
 * </li>
 * </ul>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DateEditor extends PropertyEditorSupport {
	/**
	 * 区域：默认为中国
	 */
	private final Locale locale = Locale.CHINA;
	/**
	 * 毫秒格式
	 */
	private final String MILLISECOND_FORMAT = ".SSS";
	/**
	 * 是否允许空
	 */
	private final boolean allowEmpty;
	/**
	 * 要求的时间长度
	 */
	private final int exactDateLength;
	/**
	 * 目标类型
	 */
	private Class toType = java.util.Date.class;
	
	/**
	 * 
	 * 创建一个新的实例DateEditor.
	 *
	 * @param allowEmpty 是否允许空
	 */
	public DateEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
		exactDateLength = -1;
	}
	
	/**
	 * 
	 * 创建一个新的实例DateEditor.
	 *
	 * @param allowEmpty 是否允许空
	 * @param toType 目标类型
	 */
	public DateEditor(boolean allowEmpty, Class toType) {
		this.allowEmpty = allowEmpty;
		exactDateLength = -1;
		this.toType = toType;
	}

	/**
	 * 
	 * 创建一个新的实例DateEditor.
	 *
	 * @param dateFormat 时间格式
	 * @param allowEmpty 是否允许空
	 * @param exactDateLength 要求的时间长度
	 */
	public DateEditor(boolean allowEmpty, int exactDateLength) {
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
	}
	
	/**
	 * 
	 * 创建一个新的实例DateEditor.
	 *
	 * @param allowEmpty 是否允许空
	 * @param exactDateLength 要求的时间长度
	 * @param toType 目标类型
	 */
	public DateEditor(boolean allowEmpty, int exactDateLength, Class toType) {
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
		this.toType = toType;
	}
	
	/* (non-Javadoc)
	 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		} else {
			if (text != null && exactDateLength >= 0 && text.length() != exactDateLength) {
				throw new IllegalArgumentException((new StringBuilder("Could not parse date: it is not exactly"))
						.append(exactDateLength).append("characters long").toString());
			}
			setValue(convertValue(text, toType));
		}
	}
	
	/**
	 * 转换值
	 * 将字符串值自动转换为各种时间类型
	 * @author sunju
	 * @creationDate. 2013-12-7 下午09:43:40 
	 * @param value 值
	 * @param toType 目标类型（可选：java.util.Date.class、java.sql.Timestamp.class、java.sql.Time.class）
	 * @return 转换后的值
	 */
	private Object convertValue(String value, Class toType) {
        Date result = null;
        DateFormat df = null;
        if (java.util.Date.class == toType) {
            Date check;
            DateFormat[] dfs = getDateFormats();
            for (DateFormat df1 : dfs) {
                try {
                    check = df1.parse(value);
                    df = df1;
                    if (check != null) {
                        break;
                    }
                } catch (ParseException ignore) {}
            }
        } else if (java.sql.Timestamp.class == toType) {
            Date check = null;
            SimpleDateFormat dtfmt = (SimpleDateFormat) DateFormat.getDateTimeInstance(
            		DateFormat.SHORT, DateFormat.MEDIUM, locale);
            SimpleDateFormat fullfmt = new SimpleDateFormat(
            		dtfmt.toPattern() + MILLISECOND_FORMAT, locale);
            SimpleDateFormat dfmt = (SimpleDateFormat) DateFormat.getDateInstance(
            		DateFormat.SHORT, locale);
            SimpleDateFormat[] fmts = {fullfmt, dtfmt, dfmt};
            for (SimpleDateFormat fmt : fmts) {
                try {
                	check = fmt.parse(value);
                    df = fmt;
                    if (check != null) {
                        break;
                    }
                } catch (ParseException ignore) {}
            }
        } else if (java.sql.Time.class == toType) {
        	df = DateFormat.getTimeInstance(DateFormat.MEDIUM, locale);
        } 
        // final fallback for dates without time
        if (df == null) {
            df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        }
        try {
            df.setLenient(false); // let's use strict parsing (XW-341)
            result = df.parse(value);
            if (!(Date.class == toType)) {
                try {
                    Constructor constructor = toType.getConstructor(new Class[]{long.class});
                    return constructor.newInstance(new Object[]{Long.valueOf(result.getTime())});
                } catch (Exception e) {
                	throw new IllegalArgumentException("Couldn't create class " + toType + " using default (long) constructor", e);
                }
            }
        } catch (ParseException e) {
        	throw new IllegalArgumentException("Could not parse date", e);
        }
        return result;
    }
	
	/**
	 * 获得时间格式
	 * @author sunju
	 * @creationDate. 2013-12-7 下午08:55:02 
	 * @return 时间格式数组
	 */
	private DateFormat[] getDateFormats() {
        DateFormat dt1 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale);
        DateFormat dt2 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, locale);
        DateFormat dt3 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);

        DateFormat d1 = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        DateFormat d2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        DateFormat d3 = DateFormat.getDateInstance(DateFormat.LONG, locale);

        DateFormat rfc3399 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        return new DateFormat[]{dt1, dt2, dt3, rfc3399, d1, d2, d3};
    }

}

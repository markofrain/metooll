package com.fsats.mianshi.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class MyConversionDateToString implements Converter<String, Date> {
	private String patten;
	public MyConversionDateToString(String patten){
		this.patten=patten;
	}
	
	@Override
	public Date convert(String arg0) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		try {
			date = sdf.parse(arg0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	


}

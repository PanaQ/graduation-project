package com.ly.bean;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.LYRegisterHandler;

public class RegisterBean {
	public String register(String xml){
		String result="";
		SAXParserFactory sf = SAXParserFactory.newInstance();
		//获取sax解析器的工厂对象
		try{
		SAXParser saxParser = sf.newSAXParser();
		LYRegisterHandler h = new LYRegisterHandler();
		saxParser.parse(new InputSource(new StringReader(xml)),h);
		//通过解析saxParser的parse()方法设定解析的文件和事件处理器对象  
	    result=h.getInfo();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	
	}

}


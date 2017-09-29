package com.ly.bean;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.LoginHandler;

public class LoginBean {
	String result="";
	public String password(String xml){
		  //获取sax解析器的工厂对象
		SAXParserFactory sf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = sf.newSAXParser();
			LoginHandler h = new LoginHandler(); 
			saxParser.parse(new InputSource(new StringReader(xml)),h);
			//通过解析saxParser的parse()方法设定解析的文件和事件处理器对象  
		    String error=h.getError();
			if(error!=null){
				result="error";
			}else{
				result =h.getId()+","+h.getName()+","+h.getUname();
			}
		}catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}

}

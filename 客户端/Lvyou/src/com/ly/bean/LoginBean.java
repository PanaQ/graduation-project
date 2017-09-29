package com.ly.bean;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.LoginHandler;

public class LoginBean {
	String result="";
	public String password(String xml){
		  //��ȡsax�������Ĺ�������
		SAXParserFactory sf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = sf.newSAXParser();
			LoginHandler h = new LoginHandler(); 
			saxParser.parse(new InputSource(new StringReader(xml)),h);
			//ͨ������saxParser��parse()�����趨�������ļ����¼�����������  
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

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
		//��ȡsax�������Ĺ�������
		try{
		SAXParser saxParser = sf.newSAXParser();
		LYRegisterHandler h = new LYRegisterHandler();
		saxParser.parse(new InputSource(new StringReader(xml)),h);
		//ͨ������saxParser��parse()�����趨�������ļ����¼�����������  
	    result=h.getInfo();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	
	}

}


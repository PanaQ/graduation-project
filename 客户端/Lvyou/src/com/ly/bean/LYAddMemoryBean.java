package com.ly.bean;


import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import com.ly.handler.LoginHandler;

public class LYAddMemoryBean {
	String result="";
	public String memory(String xml){
		SAXParserFactory sf = SAXParserFactory.newInstance();
		//��ȡsax�������Ĺ�������
		try {
			SAXParser saxParser = sf.newSAXParser();
			LoginHandler h = new LoginHandler(); 
			saxParser.parse(new InputSource(new StringReader(xml)),h);
			//ͨ������saxParser��parse()�����趨�������ļ����¼�����������  
			
			String id = h.getId();
			//System.out.println(id);
			String name = h.getName();
			//System.out.println(name);
			String uname = h.getUname();
			//System.out.println(uname);
			
			if(h.getError()!=null){
				result="error";
			}else{
				result =h.getId()+","+h.getName()+","+h.getUname();
			}
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}

	
}


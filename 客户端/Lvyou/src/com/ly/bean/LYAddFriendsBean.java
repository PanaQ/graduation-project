package com.ly.bean;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.LYAddFriendsHandler;

public class LYAddFriendsBean {
	String result="";
	public String addfriends( String xml){
		  //��ȡsax�������Ĺ�������
		SAXParserFactory sf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = sf.newSAXParser();
			LYAddFriendsHandler h = new LYAddFriendsHandler();
			saxParser.parse(new InputSource(new StringReader(xml)),h);
			//ͨ������saxParser��parse()�����趨�������ļ����¼�����������  
			if(h.getError()!=null){
				result="error";
			}else{
				
			}
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}

}

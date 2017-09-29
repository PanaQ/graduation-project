package com.ly.bean;

import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.RMsgHandler;

public class RMsgBean {
public ArrayList<String[]> rmsg(String xml){
	   //��ȡsax�������Ĺ�������
		SAXParserFactory sf = SAXParserFactory.newInstance();
		ArrayList<String[]> list=null;
		try {
			SAXParser saxParser = sf.newSAXParser();;
			RMsgHandler h = new RMsgHandler();
			saxParser.parse(new InputSource(new StringReader(xml)),h);
			//ͨ������saxParser��parse()�����趨�������ļ����¼�����������  
			
			list=h.getList();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return list;
	}
}

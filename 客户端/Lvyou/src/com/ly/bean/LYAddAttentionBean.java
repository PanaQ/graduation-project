package com.ly.bean;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.LYAddAttentionHandler;

public class LYAddAttentionBean {
	String result="";
	public String addattention( String xml){
		//��ȡsax�������Ĺ�������
		SAXParserFactory sf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = sf.newSAXParser();
			LYAddAttentionHandler h = new LYAddAttentionHandler();
			saxParser.parse(new InputSource(new StringReader(xml)),h);

			//ͨ������saxParser��parse()�����趨�������ļ����¼�����������  
			if(h.getError()!=null){
				result="error";
			}else{	
			}
		}catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}
}

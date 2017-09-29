package com.ly.bean;

import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.FriendListHandler;

public class FriendListBean {
public ArrayList<String[]> friendlist(String xml){
	//��ȡsax�������Ĺ�������
		SAXParserFactory sf = SAXParserFactory.newInstance(); 
		ArrayList<String[]> list=null;
		try {
			//�õ�һ��XML�Ķ�ȡ��
			SAXParser saxParser = sf.newSAXParser();
			
			//���ö�ȡ�����¼�������
			FriendListHandler h = new FriendListHandler();
			saxParser.parse(new InputSource(new StringReader(xml)),h);
			
			list=h.getList();

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
}

package com.ly.bean;

import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.FriendListHandler;

public class FriendListBean {
public ArrayList<String[]> friendlist(String xml){
	//获取sax解析器的工厂对象
		SAXParserFactory sf = SAXParserFactory.newInstance(); 
		ArrayList<String[]> list=null;
		try {
			//得到一个XML的读取器
			SAXParser saxParser = sf.newSAXParser();
			
			//设置读取器的事件处理器
			FriendListHandler h = new FriendListHandler();
			saxParser.parse(new InputSource(new StringReader(xml)),h);
			
			list=h.getList();

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
}

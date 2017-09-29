package com.ly.bean;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.ly.handler.ApplyHandler;

public class ApplyBean {
public ArrayList<String[]> applys(InputStream xml){
	  //获取sax解析器的工厂对象
		SAXParserFactory sf = SAXParserFactory.newInstance();
		ArrayList<String[]> list=null;
		try {
			//得到一个XML的读取器
			XMLReader xr =  sf.newSAXParser().getXMLReader();
			ApplyHandler h = new ApplyHandler();
			//设置读取器的事件处理器
			xr.setContentHandler(h);
			//解析xml文件
			xr.parse(new InputSource(xml));
			list=h.getList();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
}

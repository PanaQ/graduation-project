  package com.ly.bean;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.ly.handler.AttentionHandler;

public class AttentionBean {
public ArrayList<String[]> attention(InputStream in){
	   //获取sax解析器的工厂对象
		SAXParserFactory sf = SAXParserFactory.newInstance();
		ArrayList<String[]> lista=null;
		try {
			XMLReader xr =  sf.newSAXParser().getXMLReader();
            //得到一个XML的读取器
			
			
			AttentionHandler h = new AttentionHandler();
			//设置读取器的事件处理器
			xr.setContentHandler(h);
			xr.parse(new InputSource(in));//解析xml文件
			lista=h.getList();

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return lista;
	}
}

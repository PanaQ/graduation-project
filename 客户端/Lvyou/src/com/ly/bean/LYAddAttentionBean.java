package com.ly.bean;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.LYAddAttentionHandler;

public class LYAddAttentionBean {
	String result="";
	public String addattention( String xml){
		//获取sax解析器的工厂对象
		SAXParserFactory sf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = sf.newSAXParser();
			LYAddAttentionHandler h = new LYAddAttentionHandler();
			saxParser.parse(new InputSource(new StringReader(xml)),h);

			//通过解析saxParser的parse()方法设定解析的文件和事件处理器对象  
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

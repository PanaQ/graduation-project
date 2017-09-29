package com.ly.bean;

import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.GetTogetherHandler;

public class GetTogetherBean {
	public ArrayList<String[]> gettogether(String xml){
		  //获取sax解析器的工厂对象
		SAXParserFactory sf = SAXParserFactory.newInstance();
		ArrayList<String[]> list = null;
		try {
			SAXParser saxParser = sf.newSAXParser();
			GetTogetherHandler h = new GetTogetherHandler();
			saxParser.parse(new InputSource(new StringReader(xml)),h);
			//通过解析saxParser的parse()方法设定解析的文件和事件处理器对象 
			if(h.getError()!=null){
				list=null;			
			}else{
				list = h.getList();				
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return list;		
	}
}

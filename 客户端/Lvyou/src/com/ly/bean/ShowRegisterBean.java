package com.ly.bean;

import java.io.StringReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import com.ly.handler.ShowRegisterHandler;


public class ShowRegisterBean {
	public String showregister(String xml){
		SAXParserFactory fc = SAXParserFactory.newInstance();
		String result = null;
		try {
			SAXParser saxParser = fc.newSAXParser();
			ShowRegisterHandler h = new ShowRegisterHandler();
			saxParser.parse(new InputSource(new StringReader(xml)),h);
			

			//通过解析saxParser的parse()方法设定解析的文件和事件处理器对象  
			if(h.getError()!=null){
				result="error";
			
			}else{
	result=h.getUname()+","+h.getEmail()+","+h.getSex()+","+h.getPhone()+","+h.getJob()+","+h.getAddress()+","+h.getCircle()+","+h.getGz();
				
			}					
		
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}										
		return result;	
		
	}
}

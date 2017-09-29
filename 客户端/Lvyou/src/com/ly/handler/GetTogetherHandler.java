package com.ly.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetTogetherHandler extends DefaultHandler {
	private String error;
	private String []loginfo;
	private String val="";
	private ArrayList<String[]> list = new ArrayList<String[]>();
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public ArrayList<String[]> getList() {
		return list;
	}
	public void setList(ArrayList<String[]> list) {
		this.list = list;
	}
	

	  //在读取到开始标签的时候执行
@Override
public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {
	if(qName.equals("log")){
		loginfo=new String[6];
	}
	super.startElement(uri, localName, qName, attributes);
}

//在读取到结束标签的时候执行
@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
			if(qName.equals("name")){
				loginfo[0]=val;
			}
			if(qName.equals("pic")){
				loginfo[1]=val;
			}
			if(qName.equals("content")){
				loginfo[2]=val;
			}
			if(qName.equals("time")){
				loginfo[3]=val;
			}
			if(qName.equals("title")){
				loginfo[4]=val;
			}
			if(qName.equals("gtime")){
				loginfo[5]=val;
				}
			if(qName.equals("log")){
				list.add(loginfo);
			}
			if(qName.equals("error")){
				this.error=error;
			}
			val="";
					super.endElement(uri, localName, qName);
				}
			

//读取到文本节点的时候执行
@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	    val+=new String(ch,start,length);
		super.characters(ch, start, length);
	}
}

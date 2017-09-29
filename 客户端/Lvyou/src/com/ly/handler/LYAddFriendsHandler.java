package com.ly.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LYAddFriendsHandler extends DefaultHandler {

	private String val="";
	private String error;
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	

	  //在读取到开始标签的时候执行
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
	}
	

	  //在读取到结束标签的时候执行
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("error"))
		 {
				this.error=val;
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

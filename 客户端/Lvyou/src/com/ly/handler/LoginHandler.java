package com.ly.handler;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LoginHandler extends DefaultHandler{
	private static String name;
	private static String id;
	private String val="";
	private String error;
	private static String sex;
	private static String uname;
	private static String no;
	
	public static String getUname() {
		return uname;
	}
	public static void setUname(String uname) {
		LoginHandler.uname = uname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public static String getNo() {
		return no;
	}
	public static void setNo(String no) {
		LoginHandler.no = no;
	}
	
	
	
	@Override//开始解析节点 
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
	}
	
	@Override //结束解析节点 
	public void endElement(String uri, String localName, String qName)throws SAXException {
		if(qName.equals("id"))
		 {
				this.id=val;
		 }
		if(qName.equals("name"))
		 {
				this.name=val;
		 }
		
		if(qName.equals("uname"))
		 {
				this.uname=val;
		 }
		if(qName.equals("no"))
		 {
				this.no=val;
		 }
		if(qName.equals("error"))
		 {
				this.error=val;
		 }
		val="";
		super.endElement(uri, localName, qName);
	}
	
	
		
	@Override //保存节点内容
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		val+=new String(ch,start,length);
		super.characters(ch, start, length);
	}
}

package com.ly.handler;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ApplyHandler extends DefaultHandler {
	
	private String val="";
	private String []info;
	private ArrayList<String[]> list = new ArrayList<String[]>();
	private String error;
	
	
	public String[] getLoginfo()             {return info;}
	public void setLoginfo(String[] info) {this.info = info;}
	public ArrayList<String[]> getList()     {return list;}
	public void setList(ArrayList<String[]> list) {this.list = list;}
	public String getError()                 {return error;}
	public void setError(String error)       {this.error = error;}
	
	
	//是在加载文档开始的时候执行
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}
	
    //在读取到开始标签的时候执行
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("fid")){
			info=new String[5];
		}
		super.startElement(uri, localName, qName, attributes);
	}
	
    //是在读取到结束标签的时候执行
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println(localName+"===ApplyHandler");
		
		if(localName.equals("fid"))
		{
			info[0]=val;
			
		}
		if(localName.equals("uid"))
		{
			info[1]=val;
		}

		if(localName.equals("uname"))
		{
			info[2]=val;
		}
		if(localName.equals("ueid"))
		{
			info[3]=val;
		}
		if(localName.equals("fname"))
		{
			info[4]=val;
		}
		if(localName.equals("error")){
			this.error=val;
		}
		if(localName.equals("fname")){
			list.add(info);
		}
		
		val="";
		super.endElement(uri, localName, qName);
	}
	

	 //读取到文本节点的时候执行
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		val+= new String(ch,start,length);
		super.characters(ch, start, length);
		
	}
	
	
}

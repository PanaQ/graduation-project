package com.ly.handler;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AttentionHandler extends DefaultHandler {

	private String val = "";
	private String[] loginfo;
	private ArrayList<String[]> list= new ArrayList<String[]>();
	private String error;
	
	
	public String[] getLoginfo() {
		return loginfo;
	}

	public void setLoginfo(String[] loginfo) {
		this.loginfo = loginfo;
	}

	public ArrayList<String[]> getList() {
		return list;
	}

	public void setList(ArrayList<String[]> list) {
		this.list = list;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	//是在加载文档开始的时候执行
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	  //在读取到开始标签的时候执行
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("aid")) {
			loginfo = new String[5];
		}
		super.startElement(uri, localName, qName, attributes);
	}

	  //在读取到结束标签的时候执行
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("aid")) {
			loginfo[0] = val;
		}
		if (localName.equals("hid")) {
			loginfo[1] = val;
		}

		if (localName.equals("hname")) {
			loginfo[2] = val;
		}
		if (localName.equals("oid")) {
			loginfo[3] = val;
		}
		if (localName.equals("oname")) {
			loginfo[4] = val;
		}
		if (localName.equals("error")) {
			this.error = val;
		}
		if (localName.equals("oname")) {
			list.add(loginfo);
		}

		val = "";
		super.endElement(uri, localName, qName);
	}


	 //读取到文本节点的时候执行
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		val += new String(ch, start, length);
		super.characters(ch, start, length);

	}

}

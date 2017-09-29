package com.ly.bean;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.ly.handler.ApplyHandler;

public class ApplyBean {
public ArrayList<String[]> applys(InputStream xml){
	  //��ȡsax�������Ĺ�������
		SAXParserFactory sf = SAXParserFactory.newInstance();
		ArrayList<String[]> list=null;
		try {
			//�õ�һ��XML�Ķ�ȡ��
			XMLReader xr =  sf.newSAXParser().getXMLReader();
			ApplyHandler h = new ApplyHandler();
			//���ö�ȡ�����¼�������
			xr.setContentHandler(h);
			//����xml�ļ�
			xr.parse(new InputSource(xml));
			list=h.getList();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
}

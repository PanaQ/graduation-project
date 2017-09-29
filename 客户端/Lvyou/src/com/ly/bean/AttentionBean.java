  package com.ly.bean;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.ly.handler.AttentionHandler;

public class AttentionBean {
public ArrayList<String[]> attention(InputStream in){
	   //��ȡsax�������Ĺ�������
		SAXParserFactory sf = SAXParserFactory.newInstance();
		ArrayList<String[]> lista=null;
		try {
			XMLReader xr =  sf.newSAXParser().getXMLReader();
            //�õ�һ��XML�Ķ�ȡ��
			
			
			AttentionHandler h = new AttentionHandler();
			//���ö�ȡ�����¼�������
			xr.setContentHandler(h);
			xr.parse(new InputSource(in));//����xml�ļ�
			lista=h.getList();

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return lista;
	}
}

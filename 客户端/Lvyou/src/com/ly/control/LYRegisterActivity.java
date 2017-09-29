package com.ly.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.ly.control.R;
import com.ly.bean.RegisterBean;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LYRegisterActivity extends Activity {
	
	private Button zhuce;
	private EditText zhanghao,et2,et3,et4,et5,et6,et8,et9,et10;
	private RadioButton rb1,rb2;
	private CheckBox cb1,cb2,cb3;
	private String e1,e2,e3,e4,e5,e6,e8,e9,e10;
	public int i,n1,n2,n3;
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
	
	    zhanghao=(EditText)findViewById(R.id.title);
		et2=(EditText)findViewById(R.id.EditText02);
		et3=(EditText)findViewById(R.id.EditText03);
		et4=(EditText)findViewById(R.id.EditText04);
		et5=(EditText)findViewById(R.id.EditText05);
		et6=(EditText)findViewById(R.id.EditText06);
		et8=(EditText)findViewById(R.id.EditText08);
		et9=(EditText)findViewById(R.id.EditText09);
		et10=(EditText)findViewById(R.id.EditText10);
		
		
		rb1=(RadioButton) findViewById(R.id.RadioButton01);
		rb2=(RadioButton) findViewById(R.id.RadioButton02);
		rb1.setOnClickListener(l);
		rb2.setOnClickListener(l);
		
		cb1=(CheckBox) findViewById(R.id.CheckBox01);//ѡ���ע����
		cb2=(CheckBox) findViewById(R.id.CheckBox02);
		cb3=(CheckBox) findViewById(R.id.CheckBox03);
		zhuce=(Button)findViewById(R.id.chakandibiao);
		zhuce.setOnClickListener(l);
		pd = new ProgressDialog(this); 
	
	}
	
	
	private OnClickListener l = new OnClickListener() {
		public void onClick(View v) {
			if(v==zhuce)
			{
				e1=zhanghao.getText().toString();//
				e2=et2.getText().toString();
				e3=et3.getText().toString();//��������
				e4=et4.getText().toString();//����
				e5=et5.getText().toString();//�ǳ�
				e6=et6.getText().toString();//�ֻ�
				e8=et8.getText().toString();//ְҵ
				e9=et9.getText().toString();//���ڵ�
				e10=et10.getText().toString();//Ȧ��
				if(rb1.isChecked()){	i=1;	    }
				else{	i=0;	}
				if(cb1.isChecked()){	n1=1;		}
				else{	n1=0;	}
				if(cb2.isChecked()){	n2=1;		}
				else{	n2=0;	}
				if(cb3.isChecked()){	n3=1;		}
				else{	n3=0;	}
				if (!e1.equals("") && !e2.equals("") && !e3.equals("") && !e4.equals("") && !e5.equals("") && !e6.equals("") && !e8.equals("") && !e9.equals("") &&(n1!=0||n2!=0||n3!=0)) 
				{
					if(e2.equals(e3))//�����ظ���֤
					{
						if(Linkify.addLinks(et4, Linkify.EMAIL_ADDRESSES)){//����										
						Thread t=new Thread(r);//���ӷ�����
						t.start();
						}
						else{
							Toast.makeText(LYRegisterActivity.this, "��������ȷ��email��ʽ��", Toast.LENGTH_LONG).show();																														
						}
						
					}
					else
					{					
						Toast.makeText(LYRegisterActivity.this, "��ȷ��������������һ�£� ",
								Toast.LENGTH_LONG).show();										
					}
								
				} 
				else {
						Toast.makeText(LYRegisterActivity.this, "����д�������ϣ� ",
								Toast.LENGTH_LONG).show();
				}		
			pd.show();
	   	}

	}
	};

	 Runnable r = new Runnable()
	 {
			public void run() {
				try {
					URL u = new URL("http://192.168.21.12:8888/Ly/LYRegisterServlet");
					HttpURLConnection huc = (HttpURLConnection) u.openConnection();
					huc.setDoInput(true);
					huc.setDoOutput(true);
					huc.setRequestMethod("POST");
					huc.setRequestProperty("Content-Type", "text/xml");  
					huc.connect();//��ʼ��������
					OutputStream out = huc.getOutputStream();
					StringBuilder sb = new StringBuilder();
					sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
					sb.append("<files>");
					sb.append("<file>");
					sb.append("<username>");
					sb.append(e1);
					sb.append("</username>");
					sb.append("<password>");
					sb.append(e2);
					sb.append("</password>");
					sb.append("<email>");
					sb.append(e4);
					sb.append("</email>");
					sb.append("<name>");
					sb.append(e5);
					sb.append("</name>");
					sb.append("<sex>");
					sb.append(i);
					sb.append("</sex>");
					sb.append("<phone>");
					sb.append(e6);
					sb.append("</phone>");
					sb.append("<job>");
					sb.append(e8);
					sb.append("</job>");
					sb.append("<address>");
					sb.append(e9);
					sb.append("</address>");
					sb.append("<circle>");
					sb.append(e10);
					sb.append("</circle>");
					sb.append("<guanzhu>");
					sb.append(n1+""+n2+""+n3);
					sb.append("</guanzhu>");
					sb.append("</file>");
					sb.append("</files>");
					
					byte userXml[] = sb.toString().getBytes();
					out.write(userXml);
	
					
					if(huc.getResponseCode()==HttpURLConnection.HTTP_OK){
						InputStream in =huc.getInputStream();
						InputStreamReader read = new InputStreamReader(in);//
						BufferedReader bufferedReader = new BufferedReader(read);//
						
						String xml = bufferedReader.readLine();//��ȡ�� ���н���
						System.out.println(xml);
						
						RegisterBean rb = new RegisterBean();
						String reg=rb.register(xml);
						Message msg = new Message();
						msg.obj=reg;
						h.sendMessage(msg);}
					}
					 catch (IOException e) {
						e.printStackTrace();
					 }
					 
				pd.cancel();
			}
		  };

		  Handler h=new Handler ()
		  {
				public void handleMessage(Message msg){
				     String p=(String) msg.obj;
				     
				     if(p.equals("0")){
				    	 Toast.makeText(LYRegisterActivity.this,"�Ѿ����ڵ��û�����������д�û���Ϣ��", Toast.LENGTH_LONG).show();
				     }
				     else if(p.equals("1")){
				    	 Toast.makeText(LYRegisterActivity.this,"ע��ɹ���", Toast.LENGTH_LONG).show();
				    	 Intent intent=new Intent(LYRegisterActivity.this,LoginActivity.class);
						 startActivity(intent);
			 				
			 		 }
			 	 };
			  };
			}

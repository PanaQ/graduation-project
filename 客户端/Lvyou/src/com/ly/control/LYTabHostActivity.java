package com.ly.control;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


import com.ly.control.R;
import com.ly.bean.RMsgBean;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

@SuppressWarnings("deprecation")
public class LYTabHostActivity extends TabActivity {
	private String uid;
	private ArrayList<String[]> list;
	TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.tab);
    uid = getIntent().getStringExtra("result");
  
    
    
    tabHost=this.getTabHost();
    TabHost.TabSpec spec1,spec2,spec3,spec4;
  // System.out.println("=====tbhost:"+id);
    

    Intent  intent1=new Intent().setClass(LYTabHostActivity.this,LYMemoryActivity.class);
    spec1=tabHost.newTabSpec("�μ�").setIndicator("�μ�").setContent(intent1);
    System.out.println("22");
    String id1 = getIntent().getStringExtra("result");
    System.out.println("33");
    intent1.putExtra("result", id1);
    System.out.println("44"+id1);
    tabHost.addTab(spec1); 
    System.out.println("55");
    
    Intent   intent2=new Intent().setClass(LYTabHostActivity.this,LYTogetherActivity.class);
    spec2=tabHost.newTabSpec("���").setIndicator("���").setContent(intent2); 
    String id2 = getIntent().getStringExtra("result");
    intent2.putExtra("result", id2);
    tabHost.addTab(spec2);
    
    Intent   intent3=new Intent().setClass(LYTabHostActivity.this,LYFriendsActivity.class);
    spec3=tabHost.newTabSpec("����").setIndicator("����").setContent(intent3); 
    String id3 = getIntent().getStringExtra("result");
    intent3.putExtra("result", id3);
    tabHost.addTab(spec3);
 
    Intent intent4=new Intent().setClass(LYTabHostActivity.this, LYOtherActivity.class);
    spec4=tabHost.newTabSpec("����").setIndicator("����").setContent(intent4); 
    String id4 = getIntent().getStringExtra("result");
    intent4.putExtra("result", id4);
    tabHost.addTab(spec4);
    
    
    RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
    radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
		
			switch (checkedId) {
			case R.id.main_tab_addExam:
				tabHost.setCurrentTabByTag("�μ�");	
				
				break;
			case R.id.main_tab_myExam:
				tabHost.setCurrentTabByTag("���");
				break;
			case R.id.main_tab_message:
				tabHost.setCurrentTabByTag("����");
				break;
			case R.id.main_tab_settings:
				tabHost.setCurrentTabByTag("����");
				break;
			default:
				//tabHost.setCurrentTabByTag("�ҵĿ���");
				break;
			}
		}
	});

		if(uid!=null){
			msg();
		}
	}
	
	private  void msg() {
			try {
				URL url = new URL("http://192.168.21.12:8888/Ly/RMsgServlet");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");//���ݷ�ʽ
				con.setRequestProperty("Content-Type", "text/xml");  
				con.setDoInput(true);//�������
				con.setDoOutput(true);
				con.connect();//��ʼ��������
				OutputStream out = con.getOutputStream();
				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
				sb.append("<user>");
				sb.append("<uid>");					
				sb.append(uid);
				sb.append("</uid>");
				sb.append("</user>");
				byte userXML[] = sb.toString().getBytes();
				out.write(userXML);
				
				if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
				{
					InputStream in =con.getInputStream();
					InputStreamReader read = new InputStreamReader(in);//
					BufferedReader bufferedReader = new BufferedReader(read);//
					String aa = bufferedReader.readLine();//��ȡ�� ���н���
					System.out.println(aa);
					RMsgBean fmb= new RMsgBean();
					list = fmb.rmsg(aa);
					Message msg = new Message();
					msg.obj=list;
					h.sendMessage(msg);
				
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		private void delmsg(){//δ����Ϣ
			try {
				URL url = new URL("http://192.168.21.12:8888/Ly/DelMsgServlet");
				HttpURLConnection htc = (HttpURLConnection) url.openConnection();
				htc.setRequestProperty("Content-Type", "text/xml");  
				htc.setDoInput(true);
				htc.setDoOutput(true);
				htc.setRequestMethod("POST");
				htc.connect();//��ʼ��������
				OutputStream out= htc.getOutputStream();
				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
				sb.append("<user>");
				sb.append("<uid>");					
				sb.append(uid);
				sb.append("</uid>");
				sb.append("</user>");
				
				byte userXML[] = sb.toString().getBytes();
				out.write(userXML);
				
				if(htc.getResponseCode()==HttpURLConnection.HTTP_OK)
				{
				
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//pd.cancel();
		}
		

		
	
	Handler  h=new  Handler()
	  {
		 public void handleMessage(Message msg) {
			
			 list =(ArrayList<String[]>) msg.obj;
			 
			 Map<String, String> childdata =null;
			 
			 if(list.size()==0){
				 Toast.makeText(getApplicationContext(), "����Ϣ", Toast.LENGTH_SHORT).show();
			 }else{
				
				 showmsg();
			 }

		  };
		 
	  };
	  
	  
	private void showmsg(){
		// �����Ի��򹹽���
		AlertDialog.Builder up = new AlertDialog.Builder(LYTabHostActivity.this);
		up.setIcon(R.drawable.info);
		up.setTitle("δ����Ϣ");
		View vv = LayoutInflater.from(LYTabHostActivity.this).inflate(R.layout.msg, null);
		ListView landmark=(ListView)vv.findViewById(R.id.ListView01);
		landmark.setAdapter(new myadapter(LYTabHostActivity.this , list));
		up.setView(vv);
		up.setNegativeButton("�Ѷ�", new DialogInterface.OnClickListener() {
			
		public void onClick(DialogInterface dialog, int which) {
					
					//pd.show();
					delmsg();
					dialog.cancel();
							
			}
			});
		
			up.setCancelable(false);
			up.show();
	    		 
	     }   
	
	
	private class myadapter extends BaseAdapter{
		private Context c;
		private ArrayList<String[]> list;
		public myadapter(Context c, ArrayList<String[]> list){
			this.c=c;
			this.list=list;
		}
		
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}

			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return list.get(position);
			}

			
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View v = LayoutInflater.from(c).inflate(R.layout.msgitem, null);	
				
				TextView tv2 = (TextView) v.findViewById(R.id.title1);
				TextView tv3 = (TextView) v.findViewById(R.id.address1);
				TextView tv4 = (TextView) v.findViewById(R.id.content2);	

				String name = list.get(position)[4];	
				String time = list.get(position)[5];
				String content=list.get(position)[6];
				
				tv2.setText(name);
				tv3.setText(content);
				tv4.setText(time);
			
				return v;
			}
			
		}

}

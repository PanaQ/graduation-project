package com.ly.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.ly.control.R;
import com.ly.bean.GetTogetherBean;
import com.ly.bean.ShowMemoryBean;






import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LYMemoryActivity extends Activity{
	private  ArrayList<String[]> list = new ArrayList<String[]>();
	private ListView listmemory;
	private String t,a,c,m,u,ti,te,tt;
	private int count=0;
	private Button bt1,bt2;
	private String hostid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fxmemory);
		listmemory = (ListView) findViewById(R.id.FXListView01);
		bt1 = (Button) findViewById(R.id.btnLeft);
		bt2 = (Button) findViewById(R.id.btnRight);
		hostid=getIntent().getStringExtra("result");
		System.out.println("=========LM:"+hostid);
		checkButton();
		Thread t = new Thread(r);
		t.start();
		bt1.setOnClickListener(l);
		bt2.setOnClickListener(l);
	}
	 private void checkButton() { 
		  //����ֵС�ڵ���0����ʾ������ǰ��ҳ�ˣ��Ѿ����˵�һҳ�ˡ�
		  //����ǰ��ҳ�İ�ť��Ϊ�����á�
		  if(count <=0){
		    bt1.setEnabled(false);
		    bt2.setEnabled(true);
		  }
		   /**ֵ�ĳ��ȼ�ȥǰ��ҳ�ĳ��ȣ�ʣ�µľ�����һҳ�ĳ��ȣ�
		    * �����һҳ�ĳ��ȱ�View_CountС��
		    * ��ʾ��������һҳ�ˣ�������û���ˡ�*/
		   //�����ҳ�İ�ť��Ϊ�����á�
		  else if(list.size()<=5){
		     bt2.setEnabled(false);
		     bt1.setEnabled(true);
		  }
		  //����2����ť����Ϊ���õġ�
		   else {
		    bt1.setEnabled(true);
		    bt2.setEnabled(true);
		   }
		 } 
	private OnClickListener l = new OnClickListener() {
		public void onClick(View v) {
			if(v==bt1){		//��һҳ
				 count--;
				 Thread t = new Thread(r);
				 t.start();
				 checkButton();
			}
			if(v==bt2){//��һҳ
				count++;
				Thread t = new Thread(r);
				t.start();
			    checkButton();
			}
		}
	};
	
	

	Runnable r = new Runnable(){
		public void run() {
			try {
				URL url = new URL("http://192.168.21.12:8888/Ly/ShowMemoryServlet");
				HttpURLConnection htc = (HttpURLConnection) url.openConnection();
				htc.setRequestMethod("POST");
				htc.setRequestProperty("Content-Type", "text/xml"); 
				htc.setDoInput(true);
				htc.setDoOutput(true);
				OutputStream out = htc.getOutputStream();
				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
				sb.append("<user>");
				sb.append("<flag>");
				sb.append(count);
				sb.append("</flag>");
				sb.append("</user>");
				byte userXml[] = sb.toString().getBytes();
				out.write(userXml);
				if(htc.getResponseCode()==HttpURLConnection.HTTP_OK){
					InputStream in = htc.getInputStream();
					InputStreamReader read = new InputStreamReader(in);//
					BufferedReader bufferedReader = new BufferedReader(read);//
					String aa = bufferedReader.readLine();//��ȡ�� ���н���
					ShowMemoryBean smb = new ShowMemoryBean();
					list =smb.gettogether(aa);
					Message msg = new Message();
					msg.obj=list;
					h.sendMessage(msg);
				}			
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	};
	Handler  h=new  Handler()
	{
		public void handleMessage(Message msg) {
			  list=(ArrayList<String[]>) msg.obj;
			  listmemory.setAdapter(new myadapter(LYMemoryActivity.this , list));
			  listmemory.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					//    ����ǧ�����ˣ���������   �Ѿ�����
					// u.u_name,m.memory_name,m.memory_address,m.memory_content,m.memory_id,u.u_id,m.memory_time 
					t = list.get(arg2)[1];
					a = list.get(arg2)[2];				
					c = list.get(arg2)[3];
					m = list.get(arg2)[4];
					u = list.get(arg2)[5];
			
					if(hostid==null)
					{
						Toast.makeText(LYMemoryActivity.this, "���ȵ�¼,�ٲ鿴����", Toast.LENGTH_LONG).show();	
					
					}else
					{
						Intent intent =new Intent(LYMemoryActivity.this,LYMemory2Activity.class);
						//���ĳһ����������ʾ��������ת

						System.out.println(t+" "+a+" "+c+" "+m+" "+u+" "+hostid);
						intent.putExtra("title", t);
						intent.putExtra("address", a);
						intent.putExtra("content", c);
						intent.putExtra("mid", m);
						intent.putExtra("uid", u);
						intent.putExtra("hostid", hostid);
						System.out.println("M1");
						startActivity(intent);
					}
				}
			});	  
		  };
	};
	
	
	private class myadapter extends BaseAdapter{
		private Context c;
		private ArrayList<String[]> list;
		public myadapter(Context c, ArrayList<String[]> list){
			this.c=c;
			this.list=list;
		}
		
			public int getCount() {return list.size();}
			public Object getItem(int position) {return list.get(position);}
			public long getItemId(int position) {return position;}

			public View getView(int position, View convertView, ViewGroup parent) {
				View v = LayoutInflater.from(c).inflate(R.layout.memoryitem, null);
				ImageView iv = (ImageView) v.findViewById(R.id.fImg);
				TextView tv1 = (TextView) v.findViewById(R.id.uname);
				TextView tv2 = (TextView) v.findViewById(R.id.title1);
				TextView tv3 = (TextView) v.findViewById(R.id.address1);
				TextView tv4 = (TextView) v.findViewById(R.id.content1);
				TextView tv5 = (TextView) v.findViewById(R.id.content2);
	
				String uname = list.get(position)[0];
				String title = list.get(position)[1];
				String address = list.get(position)[2];
				String content = list.get(position)[3];			
				String mid=list.get(position)[4];
				String uid=list.get(position)[5];
				String time = list.get(position)[6];
				
				tv1.setText(uname);
				tv2.setText(title);
				tv3.setText(address);
				tv4.setText(content);
				tv5.setText(time);
				return v;
			}		
		}
}

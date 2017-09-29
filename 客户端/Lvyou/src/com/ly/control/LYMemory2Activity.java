package com.ly.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ly.control.R;
import com.ly.bean.LYAddAttentionBean;
import com.ly.bean.LYAddFriendsBean;
import com.ly.bean.ShowReplyBean;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class LYMemory2Activity extends Activity {

	private String t2,a2,c2,m2,u2,ti2,te2,tt2;
	private TextView tv03,tv05,tv07,tv11,tv13,tv15;
	private Button addfriend,gzfriend,btnsendmsg;
	private ProgressDialog pd;
	private String hostid,otherid;
	private EditText etmsg;
	private ArrayList<String []> list;
	private ListView lvreply;
	private String mmid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
			super.onCreate(savedInstanceState);
			setContentView(R.layout.lymemory2);
			t2=getIntent().getStringExtra("title");
			a2=getIntent().getStringExtra("address");
			c2=getIntent().getStringExtra("content");
			m2=getIntent().getStringExtra("mid");
			u2=getIntent().getStringExtra("uid");
			

			addfriend=(Button) findViewById(R.id.jzzhy);
			gzfriend=(Button) findViewById(R.id.gzzz);
			btnsendmsg=(Button) findViewById(R.id.BtnSendInfo);
			
			addfriend.setOnClickListener(l);
			gzfriend.setOnClickListener(l);
			btnsendmsg.setOnClickListener(l);
		
			
			lvreply=(ListView) findViewById(R.id.Lvreply01);
			etmsg=(EditText) findViewById(R.id.EtSendInfo);
			
			
			pd = new ProgressDialog(this);
			tv03=(TextView) findViewById(R.id.TextBT);
			tv05=(TextView) findViewById(R.id.TextADD);
			tv07=(TextView) findViewById(R.id.TextCT);

			tv03.setText(t2);
			tv05.setText(a2);
			tv07.setText(c2);


			
			hostid=getIntent().getStringExtra("hostid");
			otherid=getIntent().getStringExtra("uid");
			
			//System.out.println(hostid+"::::"+otherid);
			
			Thread t = new Thread(r2);
			t.start();
			
			
		}
 
		private OnClickListener l = new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v==addfriend){
					if(otherid.equals(hostid)||hostid==null)					
					{
					Toast.makeText(LYMemory2Activity.this, "添加失败，未登录或者ID冲突请检查", Toast.LENGTH_LONG).show();
					}
					else
					{
					pd.show();
					Thread t = new Thread(r1);
					t.start();
					
					}
				}
				if(v == gzfriend){
					
					if(otherid.equals(hostid)||hostid==null)					
					{
						Toast.makeText(LYMemory2Activity.this, "添加失败，未登录或者ID冲突请检查", Toast.LENGTH_LONG).show();
					}
					else
					{
						pd.show();
						Thread t = new Thread(r);
						t.start();
					}
				}
				if(v == btnsendmsg)
				{	
					if(hostid==null)					
					{
						Toast.makeText(LYMemory2Activity.this, "未登录，请登录后再回复", Toast.LENGTH_LONG).show();
					}
					else
					{
						pd.show();
						Thread t = new Thread(r2);
						t.start();
						Toast.makeText(LYMemory2Activity.this, "回复成功", Toast.LENGTH_LONG).show();
					}
				}
			}
		};


	///关注作者线程 
	 Runnable r = new Runnable() {
			public void run() {
				try {
					URL url = new URL("http://192.168.21.12:8888/Ly/LYAddAttentionServlet");
					HttpURLConnection htc = (HttpURLConnection) url.openConnection();
					htc.setRequestProperty("Content-Type", "text/xml"); 
					htc.setDoInput(true);
					htc.setDoOutput(true);
					htc.setRequestMethod("POST");
					OutputStream out= htc.getOutputStream();
					StringBuilder sb = new StringBuilder();
					sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
					sb.append("<atts>");
					sb.append("<att>");
					sb.append("<hostid>");					
					sb.append(hostid);
					sb.append("</hostid>");
					sb.append("<otherid>");					
					sb.append(""+otherid);
					sb.append("</otherid>");
					sb.append("</att>");
					sb.append("</atts>");
					byte userXML[] = sb.toString().getBytes();
					out.write(userXML);
					
					if(htc.getResponseCode()==HttpURLConnection.HTTP_OK)
					{
						InputStream in =htc.getInputStream();
						InputStreamReader read = new InputStreamReader(in);//
						BufferedReader bufferedReader = new BufferedReader(read);//
						String aa = bufferedReader.readLine();//获取到 进行解析
						LYAddAttentionBean fab = new LYAddAttentionBean();
						String result = fab.addattention(aa);
						System.out.println("LYMemory:"+result);
						Message msg = new Message();
						msg.obj=result;
						ha.sendMessage(msg);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				pd.cancel();
			}
		};
		Handler  ha=new  Handler()
		  {
			  public void handleMessage(android.os.Message msg) {
				String result=(String) msg.obj;
				if(result.equals("error")){
					Toast.makeText(LYMemory2Activity.this, "添加失败，未登录或者ID冲突请检查", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(LYMemory2Activity.this, "关注成功", Toast.LENGTH_LONG).show();	
				}
			  };
		  };
		 
		  
		  
//点评和回复对其他人的旅游记忆
		 Runnable r2 = new Runnable() {
				public void run() {
					try {
						URL url = new URL("http://192.168.21.12:8888/Ly/LYReplyMemoryServlet");
						HttpURLConnection htc = (HttpURLConnection) url.openConnection();
						htc.setRequestMethod("POST");//传递方式
						htc.setRequestProperty("Content-Type", "text/xml");  
						htc.setDoInput(true);//允许输出
						htc.setDoOutput(true);
						htc.connect();//开始连接请求
						OutputStream out = htc.getOutputStream();
	
						StringBuilder sb = new StringBuilder();
							sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
							sb.append("<atts>");
							sb.append("<att>");
							sb.append("<memoryid>");					
							sb.append(m2);
							sb.append("</memoryid>");
							sb.append("<hostid>");					
							sb.append(hostid);
							sb.append("</hostid>");
							sb.append("<content>");					
							sb.append(""+etmsg.getText().toString());
							sb.append("</content>");
							sb.append("</att>");
							sb.append("</atts>");
						byte userXML[] = sb.toString().getBytes();
						out.write(userXML);
						
						if(htc.getResponseCode()==HttpURLConnection.HTTP_OK)
						{
							InputStream in =htc.getInputStream();
							InputStreamReader read = new InputStreamReader(in);//
							BufferedReader bufferedReader = new BufferedReader(read);//
							String aa = bufferedReader.readLine();//获取到 进行解析
							System.out.println(aa);
							ShowReplyBean srb = new ShowReplyBean();
							list = srb.showreply(aa);
							Message msg = new Message();
							msg.obj=list;
							h.sendMessage(msg);	
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					pd.cancel();
				}
			};
			 Handler  h=new  Handler()
			  {
				  @SuppressWarnings("unchecked")
				public void handleMessage(Message msg) {
					   list=(ArrayList<String[]>) msg.obj;
					   //System.out.println(list.get(2));
					   lvreply.setAdapter(new myadapter(LYMemory2Activity.this , list));
				  };
			  };
			  
		//将回复内容放入列表	  
			  private class myadapter extends BaseAdapter{
					private Context c;
					private ArrayList<String[]> list;
					public myadapter(Context c,ArrayList<String[]> l){			
						this.c=c;
						this.list=l;
					}
					public int getCount() {
						return list.size();
					}
					public Object getItem(int position) {
						return list.get(position);
					}
					public long getItemId(int position) {
						return position;
					}
					public View getView(int position, View convertView, ViewGroup parent) {
						View v=LayoutInflater.from(c).inflate(R.layout.lyreplyitem2, null);
							mmid=list.get(position)[2];
							
							TextView tv1 = (TextView) v.findViewById(R.id.uname22);
							String name =list.get(position)[3];
							tv1.setText("（"+name+"）");
							TextView tv2 = (TextView)v.findViewById(R.id.content22);
							String content=list.get(position)[0];
							tv2.setText(content);								
							return v;
					}		
				}
 
//添加好友线程
  Runnable r1 = new Runnable() {
					public void run() {
						try {
							URL url = new URL("http://192.168.21.12:8888/Ly/LYAddFriendsServlet");
							HttpURLConnection htc = (HttpURLConnection) url.openConnection();
							htc.setRequestMethod("POST");//传递方式
							htc.setRequestProperty("Content-Type", "text/xml");  
							htc.setDoInput(true);//允许输出
							htc.setDoOutput(true);
							htc.connect();//开始连接请求
							OutputStream out = htc.getOutputStream();
							StringBuilder sb = new StringBuilder();
							sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
							sb.append("<atts>");
							sb.append("<att>");
							sb.append("<hostid>");					
							sb.append(hostid);
							sb.append("</hostid>");
							sb.append("<otherid>");					
							sb.append(""+otherid);
							sb.append("</otherid>");
							sb.append("</att>");
							sb.append("</atts>");
							byte userXML[] = sb.toString().getBytes();
							out.write(userXML);
							if(htc.getResponseCode()==HttpURLConnection.HTTP_OK)
							{
								InputStream in =htc.getInputStream();
								InputStreamReader read = new InputStreamReader(in);//
								BufferedReader bufferedReader = new BufferedReader(read);//
								String aa = bufferedReader.readLine();//获取到 进行解析
								System.out.println(aa);
								LYAddFriendsBean fab = new LYAddFriendsBean();
								String result = fab.addfriends(aa);
								Message msg = new Message();
								msg.obj=result;
								ha1.sendMessage(msg);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						pd.cancel();
					}
				};
				Handler  ha1=new  Handler()
				  {
					  public void handleMessage(Message msg) {
						String result=(String) msg.obj;
						if(result.equals("error")){
							Toast.makeText(LYMemory2Activity.this, "添加失败，未登录或者ID冲突请检查", Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(LYMemory2Activity.this, "验证信息已经发送", Toast.LENGTH_LONG).show();
						}
					  };
				  };
}

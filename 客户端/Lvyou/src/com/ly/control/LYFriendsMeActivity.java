package com.ly.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.ly.control.R;
import com.ly.bean.ShowMemoryBean;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LYFriendsMeActivity extends Activity {
	private  ArrayList<String[]> list = new ArrayList<String[]>();
	private String ueid;
	private int count = 0;
	private ListView lv;
	private Button up,down;
	private String t,a,c,m,u,hostid;
@Override
protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.friendm);
	lv = (ListView) findViewById(R.id.ListView01);
	up = (Button) findViewById(R.id.btnLeft);
	down = (Button) findViewById(R.id.btnRight);
	
	ueid = getIntent().getStringExtra("ueid");
	hostid = getIntent().getStringExtra("result");
	System.out.println("FriendsMeA==============:"+ueid+"  "+hostid);
	checkButton();
	Thread t = new Thread(r);
	t.start();
	up.setOnClickListener(l);
	down.setOnClickListener(l);
}
private void checkButton() { 
	  //索引值小于等于0，表示不能向前翻页了，已经到了第一页了。
	  //将向前翻页的按钮设为不可用。
	  if(count <=0){
	    up.setEnabled(false);
	    down.setEnabled(true);
	  }
	   /**值的长度减去前几页的长度，剩下的就是这一页的长度，
	    * 如果这一页的长度比View_Count小，
	    * 表示这是最后的一页了，后面在没有了。*/
	   //将向后翻页的按钮设为不可用。
	  else if(list.size()<=5){
	     down.setEnabled(false);
	     up.setEnabled(true);
	  }
	  //否则将2个按钮都设为可用的。
	   else {
	    up.setEnabled(true);
	    down.setEnabled(true);
	   }
	 } 
private OnClickListener l = new OnClickListener() {
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==up){		
			count--;
			 Thread t = new Thread(r);
			t.start();
			  checkButton();
		}
		if(v==down){
			count++;
			Thread t = new Thread(r);
			t.start();
		 checkButton();
		}
	}
};
//与服务器进行交互
Runnable r = new Runnable() {
	public void run() {
		
		try {
			URL url = new URL("http://192.168.21.12:8888/Ly/FriendsMeServlet");
			HttpURLConnection htc = (HttpURLConnection) url.openConnection();
			htc.setDoInput(true);
			htc.setDoOutput(true);
			htc.setRequestMethod("POST");
			htc.setRequestProperty("Content-Type", "text/xml"); 
			htc.connect();//开始连接请求
			OutputStream out= htc.getOutputStream();
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
			sb.append("<user>");
			sb.append("<ueid>");					
			sb.append(ueid);
			sb.append("</ueid>");
			sb.append("<count>");					
			sb.append(count);
			sb.append("</count>");
			sb.append("</user>");
			
			byte userXML[] = sb.toString().getBytes();
			out.write(userXML);
			if(htc.getResponseCode()==HttpURLConnection.HTTP_OK)
			{
				InputStream in =htc.getInputStream();
				InputStreamReader read = new InputStreamReader(in);//
				BufferedReader bufferedReader = new BufferedReader(read);//
				String aa = bufferedReader.readLine();//获取到 进行解析
				ShowMemoryBean smb = new ShowMemoryBean();
				list =smb.gettogether(aa);
				Message msg = new Message();
				msg.obj=list;
				h.sendMessage(msg);
			}
		} catch (Exception e) {e.printStackTrace();}	
	}
};


Handler  h=new  Handler()
{
	  @SuppressWarnings("unchecked")
	public void handleMessage(Message msg) {
		  list=(ArrayList<String[]>) msg.obj;
		  lv.setAdapter(new myadapter(LYFriendsMeActivity.this , list));
		  lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//降1
				t = list.get(arg2)[1];
				a = list.get(arg2)[2];
				c = list.get(arg2)[3];
				m = list.get(arg2)[4];
				u = list.get(arg2)[5];
				Intent intent =new Intent(LYFriendsMeActivity.this,LYMemory2Activity.class);
				//intent传值进行跳转
				intent.putExtra("title", t);
				intent.putExtra("address", a);
				intent.putExtra("content", c);
				intent.putExtra("mid", m);
				intent.putExtra("uid", u);
				intent.putExtra("hostid", hostid);
				startActivity(intent);
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
			// TODO Auto-generated method stub
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

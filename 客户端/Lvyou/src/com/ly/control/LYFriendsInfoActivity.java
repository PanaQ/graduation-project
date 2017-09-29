package com.ly.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.ly.control.R;
import com.ly.bean.ShowRegisterBean;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class LYFriendsInfoActivity extends Activity {
	private String id;
	private String result;
	private TextView nichen,email,xingbie,dianhua,zhiye,dizhi,quanzi,xingqu;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.friendsinfomation);
	id=getIntent().getStringExtra("ueid");
	
	System.out.println("FriendsInfoActivity:"+id);

	nichen=(TextView) findViewById(R.id.Textnichen);
	email=(TextView) findViewById(R.id.Textemail);
	xingbie=(TextView) findViewById(R.id.Textsex);
	dianhua=(TextView) findViewById(R.id.Texttel);
	zhiye=(TextView) findViewById(R.id.Textjob);
	dizhi=(TextView) findViewById(R.id.Textadd);
	quanzi=(TextView) findViewById(R.id.Textqz);
	xingqu=(TextView) findViewById(R.id.Textgz);
	
	Thread t=new Thread(r);
	t.start();
}


Runnable r = new Runnable(){
	public void run() {
		try {
			URL url = new URL("http://192.168.21.12:8888/Ly/ShowRegisterServlet");
			HttpURLConnection htc = (HttpURLConnection) url.openConnection();
			htc.setRequestMethod("POST");
			htc.setRequestProperty("Content-Type", "text/xml"); 
			htc.setDoInput(true);
			htc.setDoOutput(true);
			htc.connect();//开始连接请求
			OutputStream out = htc.getOutputStream();
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<user>");
			sb.append("<id>");
			sb.append(id);
			sb.append("</id>");
			sb.append("</user>");
			byte userXml[] = sb.toString().getBytes();
			out.write(userXml);
			
			
			if(htc.getResponseCode()==HttpURLConnection.HTTP_OK){
				InputStream in = htc.getInputStream();
				InputStreamReader read = new InputStreamReader(in);//
				BufferedReader bufferedReader = new BufferedReader(read);//
				String aa = bufferedReader.readLine();//获取到 进行解析
				
				ShowRegisterBean bean = new ShowRegisterBean();
				result =bean.showregister(aa);
				Message msg = new Message();
				msg.obj=result;
				h.sendMessage(msg);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
};
Handler  h=new  Handler()
{
	  public void handleMessage(android.os.Message msg) {
		   result= (String) msg.obj;
		 if(result.equals("error")){
			 Toast.makeText(LYFriendsInfoActivity.this, "页面错误", Toast.LENGTH_LONG).show();
		 }else{
			String [] re = result.split(",");
			nichen.setText(re[0]);
			email.setText(re[1]);
			xingbie.setText(re[2]);
			dianhua.setText(re[3]);
			zhiye.setText(re[4]);
			dizhi.setText(re[5]);
			quanzi.setText(re[6]);
			xingqu.setText(re[7]);
			

		 }
		
	  };
};
}

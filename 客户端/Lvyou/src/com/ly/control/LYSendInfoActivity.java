package com.ly.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ly.control.R;
import com.ly.bean.FriendListBean;
import com.ly.bean.LoginBean;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LYSendInfoActivity extends Activity {
    /** Called when the activity is first created. */
	private String time,uid,ueid,content;
	private EditText et;
	private Button bt;
	private ProgressDialog pd;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendinfo);
        et = (EditText) findViewById(R.id.EtSendInfo);
        bt = (Button) findViewById(R.id.BtnSendInfo);
        
        pd = new ProgressDialog(this);
        uid = getIntent().getStringExtra("result");
        ueid = getIntent().getStringExtra("ueid");
        System.out.println("发送消息===："+uid+"  "+ueid);
        
        
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd  HH:mm:ss");  
        Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间             
        time  =   formatter.format(curDate);  
        bt.setOnClickListener(l);
    }
    private OnClickListener l = new OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			content = et.getText().toString();
			pd.show();
			Thread t = new Thread(r);
			t.start();
			Intent intent = new Intent(LYSendInfoActivity.this,LYTabHostActivity.class);
			intent.putExtra("result", uid);
			startActivity(intent);
			Toast.makeText(LYSendInfoActivity.this, "发送成功！", Toast.LENGTH_LONG).show();
		}
	};
	
	//服务器交互进程
	Runnable r = new Runnable() {
	public void run() {
		try {
			URL url = new URL("http://192.168.21.12:8888/Ly/LYAddMsgServlet");
			HttpURLConnection htc = (HttpURLConnection) url.openConnection();
			htc.setRequestMethod("POST");//传递方式
			htc.setRequestProperty("Content-Type", "text/xml");  
			htc.setDoInput(true);//允许输出
			htc.setDoOutput(true);
			htc.connect();//开始连接请求
			OutputStream out= htc.getOutputStream();
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
			sb.append("<user>");
			sb.append("<hostid>");					
			sb.append(uid);
			sb.append("</hostid>");
			sb.append("<otherid>");					
			sb.append(ueid);
			sb.append("</otherid>");
			sb.append("<time>");					
			sb.append(time);
			sb.append("</time>");
			sb.append("<content>");					
			sb.append(content);
			sb.append("</content>");
			sb.append("</user>");
			
			byte userXML[] = sb.toString().getBytes();
			out.write(userXML);
			
			if(htc.getResponseCode()==HttpURLConnection.HTTP_OK)
			{
				InputStream in = htc.getInputStream();
			//////////////////////////////////////////////////////////
				InputStreamReader read = new InputStreamReader(in);//
				BufferedReader bufferedReader = new BufferedReader(read);//
				String aa = bufferedReader.readLine();//获取到 进行解析
				System.out.println(aa);
				FriendListBean xp = new FriendListBean();//
				ArrayList<String[]> result = xp.friendlist(aa);// 类型变化
				Message msg = new Message();
				msg.obj = result;
			//	h.sendMessage(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		pd.cancel();
	}
};
}
package com.ly.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.ly.control.R;
import com.ly.bean.LoginBean;
import com.ly.db.DBHelper;
import com.ly.handler.LoginHandler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private EditText title,passwordd;
	private Button denglu;
	private CheckBox cb;
	public static String f;
	public static String name;
	public String s,s1;
	 private LoginHandler handler;
	 private int count;
	 private ProgressDialog pd;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        denglu = (Button) findViewById(R.id.loginin);
        title = (EditText) findViewById(R.id.title);
        passwordd = (EditText) findViewById(R.id.passwordd);
        cb = (CheckBox) findViewById(R.id.CheckBox01);
        pd = new ProgressDialog(this);
        
        
        DBHelper db = new DBHelper(LoginActivity.this);
		SQLiteDatabase sd = db.getReadableDatabase();
		Cursor c = sd.rawQuery("select user_id _id,user_name,user_pswd from "+DBHelper.table_name+"", null);
		while(c.moveToNext()){
			int id = c.getInt(0);
			name = c.getString(1);
			f = c.getString(2);
			cb.setChecked(true);
			
		}
		title.setText(name);
		passwordd.setText(f);
		c.close();
		sd.close();
		db.close();
		
		handler = new LoginHandler();
		 denglu.setOnClickListener(l);
    }
    
    private OnClickListener l = new OnClickListener() {
		
		public void onClick(View v) {
			if(cb.isChecked()&&v==denglu){
				count++;
				pd.show();
				Thread t = new Thread(r);
				t.start();
				
				//记住登录密码保存到本地数据库
				//后期修改为使用SharedPreferences
				//http://blog.csdn.net/liuyiming_/article/details/7704923/
				s = title.getText().toString();
				s1 = passwordd.getText().toString();
				DBHelper db = new DBHelper(LoginActivity.this);
				SQLiteDatabase sd = db.getWritableDatabase();
				sd.execSQL("insert into "+DBHelper.table_name+" values(null,?,?)",new String[]{s,s1});
				sd.close();
				db.close();
			}
			
			
			if(v==denglu&&!cb.isChecked()){
				count++;
				pd.show();
				Thread t = new Thread(r);
				t.start();
				
			}
		}
	};
	
Runnable r = new Runnable(){

		public void run() {
			// TODO Auto-generated method stub
			try {
				URL url = new URL("http://192.168.21.12:8888/Ly/LoginServlet");
				HttpURLConnection htc = (HttpURLConnection) url.openConnection();
				
				htc.setRequestMethod("POST");//传递方式
				htc.setRequestProperty("Content-Type", "text/xml");  
				htc.setDoInput(true);//允许输出
				htc.setDoOutput(true);
				htc.connect();//开始连接请求
				OutputStream out = htc.getOutputStream();
				
				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
				sb.append("<user>");
				sb.append("<name>");
				sb.append(title.getText()+"");
				sb.append("</name>");
				sb.append("<pswd>");
				sb.append(passwordd.getText()+"");
				sb.append("</pswd>");
				sb.append("<flag>");
				sb.append(count);
				sb.append("</flag>");
				sb.append("</user>");	
			
				byte userXml[] = sb.toString().getBytes();
				out.write(userXml);

				if(htc.getResponseCode()==HttpURLConnection.HTTP_OK){
					
					InputStream in = htc.getInputStream();
					InputStreamReader read = new InputStreamReader(in);//从字节到字符
					BufferedReader bufferedReader = new BufferedReader(read);//从字符输入流中读取文本，缓冲各个字符
					String xml = bufferedReader.readLine();//获取到 进行解析
				
					System.out.println(xml);
					LoginBean xp = new LoginBean();//
					
					
					String result = xp.password(xml);//
					System.out.println("=====登陆返回："+result);
					Message msg = new Message();
					msg.obj = result;
					h.sendMessage(msg);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			pd.cancel();
		}
  };
  private String result;
  Handler h = new Handler(){
 	 public void handleMessage(Message msg) {
 		 String re = msg.obj+"";
 		
 		 if(re.equals("error")){
 			 Toast.makeText(LoginActivity.this, "登录失败,请检查用户名密码是否正确", Toast.LENGTH_LONG).show();
 			if(handler.getNo()!=null){
 				Intent intent = new Intent(LoginActivity.this,YanzhengActivity.class);
 				startActivity(intent);
 				count=0;
 			}

 		 }else{
 			 result =re;
 			 String ss[] = result.split(",");
 			 Toast.makeText(LoginActivity.this, "欢迎"+ss[2]+"进入", Toast.LENGTH_LONG).show();
 			 Intent intent = new Intent(LoginActivity.this,LYTabHostActivity.class);
 			 intent.putExtra("result", ss[0]);
 			 startActivity(intent);
	
 		 }
 	 };
  };
}
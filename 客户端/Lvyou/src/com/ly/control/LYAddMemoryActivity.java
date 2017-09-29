package com.ly.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ly.control.R;
import com.ly.db.DBHelper2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LYAddMemoryActivity extends Activity {
	private String [] str00;
	private String [] str01;
	private String str02[][]={//后期可使用AS开发工具   使用开源项目https://github.com/crazyandcoder/citypicker
			{"请选择"},
			{"东莞市","广州市","中山市","深圳市","惠州市","珠海市","汕头市","佛山市","湛江市","其他"},
			{"济南市","青岛市","临沂市","济宁市","烟台市","淄博市","泰安市","日照市","威海市","其他"},
			{"苏州市","盐城市","无锡市","南京市","南通市","常州市","镇江市","扬州市","泰州市","其他"},
			{"温州市","宁波市","杭州市","嘉兴市","金华市","湖州市","绍兴市","舟山市","丽水市","其他"},
			{"漳州市","厦门市","泉州市","福州市","莆田市","宁德市","三明市","南平市","龙岩市","其他"},
			{"昆明市","红河州","大理州","文山州","德宏州","曲靖市","昭通市","怒江州","迪庆州","其他"}
	};
	private String []str03;
	private Spinner sp1,sp2,sp3;
	private EditText txtName,txtContent;
	private ProgressDialog pd;
	private Button btnsend,btnsave;
	private String title,content,sp11,sp22,sp33;
	private String id,time,title1,content1,address1;
	private String nll;
	private int sp111,sp221,sp331;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fxaddmemory);
		btnsend=(Button) findViewById(R.id.fabu);
		btnsave=(Button) findViewById(R.id.savejy);
		txtName=(EditText) findViewById(R.id.title);
		txtContent=(EditText) findViewById(R.id.contentt);
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd  HH:mm:ss");  
        Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间             
        time  = formatter.format(curDate);  

        nll = getIntent().getStringExtra("nll");
		pd = new ProgressDialog(this);
		
		id=getIntent().getStringExtra("result");
		content1 = getIntent().getStringExtra("content");
		title1 = getIntent().getStringExtra("title");
		address1 = getIntent().getStringExtra("address");
		
		btnsend.setOnClickListener(l);
		btnsave.setOnClickListener(l);
		sp1=(Spinner) findViewById(R.id.Spinner01);
	    sp2=(Spinner) findViewById(R.id.Spinner02);
	    sp3=(Spinner) findViewById(R.id.Spinner03);
		
	    str00=new String[]{"中国"};
	    str01=new String[]{"请选择","广东省", "山东省","江苏省", "浙江省","福建省 ","云南省"};
	    
	    
	    SpinnerAdapter adapter00= new SpinnerAdapter(this,android.R.layout.simple_spinner_item,str00);
	    adapter00.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sp1.setAdapter(adapter00);	
	    
	    SpinnerAdapter adapter01=new SpinnerAdapter(this,android.R.layout.simple_spinner_item,str01);
		adapter01.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2.setAdapter(adapter01); 

	    sp2.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				position=(int)arg3;
				str03=new String[str02[position].length];
				for(int i=0;i<str02[position].length;i++){  
                    str03[i]=str02[position][i];  
                }
					SpinnerAdapter adapter02=new SpinnerAdapter(LYAddMemoryActivity.this,android.R.layout.simple_spinner_item,str03);  
			        adapter02.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
			        sp3.setAdapter(adapter02);  
			}
			public void onNothingSelected(AdapterView<?> arg0) {} 
});
	    
	    
		if(nll!=null){
			String[] s = address1.split(",");
			Integer[] orderids = new Integer[s.length];
			for(int i = 0;i<s.length;i++){
				orderids[i] = Integer.parseInt(s[i]);
			}
	
			txtName.setText(title1);
			txtContent.setText(content1);
			sp1.setSelection(orderids[0]);
			sp2.setSelection(orderids[1]);
			sp3.setSelection(orderids[2]);
		}
	}
	
	private class SpinnerAdapter extends ArrayAdapter<String> 
	{  
	    Context context;  
	    String[] items = new String[] {};  
	  
	    public SpinnerAdapter(final Context context,final int textViewResourceId, final String[] objects) {  
	        super(context, textViewResourceId, objects);  
	        this.items = objects;  
	        this.context = context;  
	    } 
	  
	    @Override  
	    public View getView(int position, View convertView, ViewGroup parent) {  
	        if (convertView == null) {  
	            LayoutInflater inflater = LayoutInflater.from(context);  
	            convertView = inflater.inflate(  
	                    android.R.layout.simple_spinner_item, parent, false);  
	        }  
	        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);  
	        tv.setText(items[position]);  
	        tv.setTextColor(Color.BLACK);  
	        tv.setTextSize(12);  
	        return convertView;  
	    }
	}
	
	
	
	private OnClickListener l = new OnClickListener() {
		public void onClick(View v) {
			if(v == btnsend){//发布
				title=txtName.getText().toString();
				content=txtContent.getText().toString();
				sp11=sp1.getSelectedItem().toString();
				sp22=sp2.getSelectedItem().toString();
				sp33=sp3.getSelectedItem().toString();
				pd.show();
				Thread t =new  Thread(r);
				System.out.println("发布进程结束");
				t.start();
				Intent intent = new Intent(LYAddMemoryActivity.this,LYTabHostActivity.class);
				intent.putExtra("result", id);
				startActivity(intent);
			}

			//保存记忆
			if(v == btnsave){
				title=txtName.getText().toString();
				content=txtContent.getText().toString();
				sp111=sp1.getSelectedItemPosition();
				sp221=sp2.getSelectedItemPosition();
				sp331=sp3.getSelectedItemPosition();
				String s = sp111+","+sp221+","+sp331;

				DBHelper2 db1 = new DBHelper2(LYAddMemoryActivity.this);//创建本地数据库
				SQLiteDatabase sd1 = db1.getWritableDatabase();
				sd1.execSQL("insert into "+DBHelper2.table_name+" values(null,?,?,?,?,?)",new String[]{title,content,s,time,id});
				sd1.close();
				db1.close();

				Intent intent = new Intent(LYAddMemoryActivity.this,StoreMemoryActivity.class);
				intent.putExtra("result", id);
				startActivity(intent);
				Toast.makeText(LYAddMemoryActivity.this, "保存记忆到本地成功！", Toast.LENGTH_LONG).show();

			}
		}
	};
	
	Runnable r = new Runnable() {
		public void run() {
			try {
				URL url = new URL("http://192.168.21.12:8888/Ly/LYBuildMemoryServlet");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestProperty("Content-Type", "text/xml");
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.connect();//开始连接请求
				OutputStream out= con.getOutputStream();
				StringBuilder sb = new StringBuilder();
						sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
						sb.append("<users>");
						sb.append("<user>");
						sb.append("<uid>");					
						sb.append(id);
						sb.append("</uid>");
						sb.append("<title>");					
						sb.append(""+title);
						sb.append("</title>");
						sb.append("<address>");					
						sb.append(sp11+","+sp22+","+sp33);
						sb.append("</address>");
						sb.append("<content>");
						sb.append(""+content);
						sb.append("</content>");
						sb.append("<time>");
						sb.append(time);
						sb.append("</time>");
						sb.append("</user>");
						sb.append("</users>");
				byte userXML[] = sb.toString().getBytes();
				out.write(userXML);
				
				if(con.getResponseCode()==HttpURLConnection.HTTP_OK)
				{
					InputStream in =con.getInputStream();
					InputStreamReader read = new InputStreamReader(in);//从字节到字符
					BufferedReader bufferedReader = new BufferedReader(read);//从字符输入流中读取文本，缓冲各个字符
					String xml = bufferedReader.readLine();//获取到 进行解析
					System.out.println(xml);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pd.cancel();
		}
	};
	
	 Handler  h=new  Handler()
	  {
		  public void handleMessage(Message msg) {
			  String re = msg.obj+"";
			  if(re.equals("error"))
			  {
				  Toast.makeText(LYAddMemoryActivity.this, "发布失败", Toast.LENGTH_LONG).show();
			  }
			  else
			  {				
				  Toast.makeText(LYAddMemoryActivity.this, "发布成功", Toast.LENGTH_LONG).show();
			  }
		  };
	  };
}

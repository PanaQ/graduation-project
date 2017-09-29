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
	private String str02[][]={//���ڿ�ʹ��AS��������   ʹ�ÿ�Դ��Ŀhttps://github.com/crazyandcoder/citypicker
			{"��ѡ��"},
			{"��ݸ��","������","��ɽ��","������","������","�麣��","��ͷ��","��ɽ��","տ����","����"},
			{"������","�ൺ��","������","������","��̨��","�Ͳ���","̩����","������","������","����"},
			{"������","�γ���","������","�Ͼ���","��ͨ��","������","����","������","̩����","����"},
			{"������","������","������","������","����","������","������","��ɽ��","��ˮ��","����"},
			{"������","������","Ȫ����","������","������","������","������","��ƽ��","������","����"},
			{"������","�����","������","��ɽ��","�º���","������","��ͨ��","ŭ����","������","����"}
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
        Date   curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ��             
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
		
	    str00=new String[]{"�й�"};
	    str01=new String[]{"��ѡ��","�㶫ʡ", "ɽ��ʡ","����ʡ", "�㽭ʡ","����ʡ ","����ʡ"};
	    
	    
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
			if(v == btnsend){//����
				title=txtName.getText().toString();
				content=txtContent.getText().toString();
				sp11=sp1.getSelectedItem().toString();
				sp22=sp2.getSelectedItem().toString();
				sp33=sp3.getSelectedItem().toString();
				pd.show();
				Thread t =new  Thread(r);
				System.out.println("�������̽���");
				t.start();
				Intent intent = new Intent(LYAddMemoryActivity.this,LYTabHostActivity.class);
				intent.putExtra("result", id);
				startActivity(intent);
			}

			//�������
			if(v == btnsave){
				title=txtName.getText().toString();
				content=txtContent.getText().toString();
				sp111=sp1.getSelectedItemPosition();
				sp221=sp2.getSelectedItemPosition();
				sp331=sp3.getSelectedItemPosition();
				String s = sp111+","+sp221+","+sp331;

				DBHelper2 db1 = new DBHelper2(LYAddMemoryActivity.this);//�����������ݿ�
				SQLiteDatabase sd1 = db1.getWritableDatabase();
				sd1.execSQL("insert into "+DBHelper2.table_name+" values(null,?,?,?,?,?)",new String[]{title,content,s,time,id});
				sd1.close();
				db1.close();

				Intent intent = new Intent(LYAddMemoryActivity.this,StoreMemoryActivity.class);
				intent.putExtra("result", id);
				startActivity(intent);
				Toast.makeText(LYAddMemoryActivity.this, "������䵽���سɹ���", Toast.LENGTH_LONG).show();

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
				con.connect();//��ʼ��������
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
					InputStreamReader read = new InputStreamReader(in);//���ֽڵ��ַ�
					BufferedReader bufferedReader = new BufferedReader(read);//���ַ��������ж�ȡ�ı�����������ַ�
					String xml = bufferedReader.readLine();//��ȡ�� ���н���
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
				  Toast.makeText(LYAddMemoryActivity.this, "����ʧ��", Toast.LENGTH_LONG).show();
			  }
			  else
			  {				
				  Toast.makeText(LYAddMemoryActivity.this, "�����ɹ�", Toast.LENGTH_LONG).show();
			  }
		  };
	  };
}

package com.ly.control;

import java.util.Random;
import com.ly.control.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class YanzhengActivity extends Activity {
	private TextView tv1;
	private EditText et;
	private Button bt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yanzheng);
		
		tv1=(TextView)findViewById(R.id.TextView03);
		et = (EditText) findViewById(R.id.title);
		bt = (Button) findViewById(R.id.chakandibiao);
		tv1.setText(getRandomString(5));//随机赋值5位
		bt.setOnClickListener(l);
		
		}
	 public static String getRandomString(int length) {   
	        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
	        Random random = new Random();   
	        StringBuffer sb = new StringBuffer();   
	        for (int i = 0; i < length; i++) {   
	            int number = random.nextInt(base.length());   
	            sb.append(base.charAt(number));   
	        }   
	        return sb.toString();   
	    }   
	 private OnClickListener l = new OnClickListener() {
		public void onClick(View v) {
			String s = tv1.getText()+"";
			String s1 = et.getText()+"";
			
			if(s.equals(s1)){	finish();    }
			else{
				Toast.makeText(YanzhengActivity.this, "验证码输入错误请重新输入！", Toast.LENGTH_LONG).show();
			}
		}
	};
}


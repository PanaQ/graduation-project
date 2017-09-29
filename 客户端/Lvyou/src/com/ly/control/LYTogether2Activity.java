package com.ly.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.amo.demo.wheelview.NumericWheelAdapter;
import com.amo.demo.wheelview.OnWheelChangedListener;
import com.amo.demo.wheelview.WheelView;
import com.ly.control.R;


public class LYTogether2Activity extends Activity {
	private ImageButton btn;
	private Dialog dialog;
	private EditText et,et2,et3;
	private Button bt;
	private String id,gtime;
	private ProgressDialog pd;
	private static int START_YEAR = 2017, END_YEAR = 2100;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ftt);
		btn = (ImageButton) findViewById(R.id.button1);
		et = (EditText) findViewById(R.id.title);
		et2 = (EditText) findViewById(R.id.EditText02);
		et3 = (EditText) findViewById(R.id.EditText03);
		bt = (Button) findViewById(R.id.chakandibiao);
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd  HH:mm:ss");  
        Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间 
        gtime  =   formatter.format(curDate);  
		id = getIntent().getStringExtra("result");

		pd=new ProgressDialog(this);
		bt.setOnClickListener(l);
		btn.setOnClickListener(new OnClickListener() {//选择时间
			public void onClick(View v) {
				showDateTimePicker();
			}
		});
	}
	private OnClickListener l = new OnClickListener() {
		public void onClick(View v) {
			pd.show();
			Thread t = new Thread(r);//开始进程
			t.start();
			Intent intent = new Intent(LYTogether2Activity.this,LYTabHostActivity.class);
			intent.putExtra("result", id);
			startActivity(intent);
		}
	};
	
Runnable r = new Runnable() {
		
		public void run() {

			String title = et3.getText().toString();
			String time = et.getText().toString();
			String mes = et2.getText().toString();
			System.out.println("=======T2"+title+time+mes);

		System.out.println(id);
			
			try {
				URL url = new URL("http://192.168.21.12:8888/Ly/SendTogetherServlet");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "text/xml");  
				con.setDoOutput(true);
				con.setDoInput(true);
		
				con.connect();//开始连接请求	
				OutputStream out = con.getOutputStream();
				
				StringBuilder sbr = new StringBuilder();
				    sbr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
				    sbr.append("<file>");
				    sbr.append("<id>");
				    sbr.append(id);
				    sbr.append("</id>");
				    sbr.append("<title>");
				    sbr.append(title);
				    sbr.append("</title>");
				    sbr.append("<time>");
				    sbr.append(time);
				    sbr.append("</time>");
					sbr.append("<content>");		
					sbr.append(mes);	
				    sbr.append("</content>");
				    sbr.append("<gtime>");		
					sbr.append(gtime);	
				    sbr.append("</gtime>");
				    sbr.append("</file>");
		
				byte content[] = sbr.toString().getBytes();
				out.write(content);

				String str = "";
				if(con.getResponseCode()==HttpURLConnection.HTTP_OK){
					InputStream in =con.getInputStream();
					InputStreamReader read = new InputStreamReader(in);//
					BufferedReader bufferedReader = new BufferedReader(read);//
					String aa = bufferedReader.readLine();//获取到 进行解析
					System.out.println(aa);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			pd.cancel();
			}
	};
	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		dialog = new Dialog(this);
		dialog.setTitle("请选择日期与时间");
		// 找到dialog的布局文件
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.time_layout, null);

		// 年
		final WheelView wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

		// 月
		final WheelView wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 日
		final WheelView wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		// 时
		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		// 分
		final WheelView wv_mins = (WheelView) view.findViewById(R.id.mins);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		wv_mins.setCyclic(true);
		wv_mins.setCurrentItem(minute);

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String
						.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小
		int textSize = 0;
		textSize = 12;

		wv_day.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

		Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
		Button btn_cancel = (Button) view
				.findViewById(R.id.btn_datetime_cancel);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// 如果是个数,则显示为"02"的样式
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				// 设置日期的显示
				 et.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
				 + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				 + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				 + decimal.format(wv_hours.getCurrentItem()) + ":"
				 + decimal.format(wv_mins.getCurrentItem()));
			  	 dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}
}
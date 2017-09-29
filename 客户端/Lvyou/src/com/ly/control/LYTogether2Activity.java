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
        Date   curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ�� 
        gtime  =   formatter.format(curDate);  
		id = getIntent().getStringExtra("result");

		pd=new ProgressDialog(this);
		bt.setOnClickListener(l);
		btn.setOnClickListener(new OnClickListener() {//ѡ��ʱ��
			public void onClick(View v) {
				showDateTimePicker();
			}
		});
	}
	private OnClickListener l = new OnClickListener() {
		public void onClick(View v) {
			pd.show();
			Thread t = new Thread(r);//��ʼ����
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
		
				con.connect();//��ʼ��������	
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
					String aa = bufferedReader.readLine();//��ȡ�� ���н���
					System.out.println(aa);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			pd.cancel();
			}
	};
	/**
	 * @Description: TODO ��������ʱ��ѡ����
	 */
	private void showDateTimePicker() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		// ��Ӵ�С���·ݲ�����ת��Ϊlist,����֮����ж�
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		dialog = new Dialog(this);
		dialog.setTitle("��ѡ��������ʱ��");
		// �ҵ�dialog�Ĳ����ļ�
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.time_layout, null);

		// ��
		final WheelView wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// ����"��"����ʾ����
		wv_year.setCyclic(true);// ��ѭ������
		wv_year.setLabel("��");// �������
		wv_year.setCurrentItem(year - START_YEAR);// ��ʼ��ʱ��ʾ������

		// ��
		final WheelView wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("��");
		wv_month.setCurrentItem(month);

		// ��
		final WheelView wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// ����
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("��");
		wv_day.setCurrentItem(day - 1);

		// ʱ
		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		// ��
		final WheelView wv_mins = (WheelView) view.findViewById(R.id.mins);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		wv_mins.setCyclic(true);
		wv_mins.setCurrentItem(minute);

		// ���"��"����
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
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
		// ���"��"����
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// �жϴ�С�¼��Ƿ�����,����ȷ��"��"������
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

		// ������Ļ�ܶ���ָ��ѡ��������Ĵ�С
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
		// ȷ��
		btn_sure.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// ����Ǹ���,����ʾΪ"02"����ʽ
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				// �������ڵ���ʾ
				 et.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
				 + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				 + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				 + decimal.format(wv_hours.getCurrentItem()) + ":"
				 + decimal.format(wv_mins.getCurrentItem()));
			  	 dialog.dismiss();
			}
		});
		// ȡ��
		btn_cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		// ����dialog�Ĳ���,����ʾ
		dialog.setContentView(view);
		dialog.show();
	}
}
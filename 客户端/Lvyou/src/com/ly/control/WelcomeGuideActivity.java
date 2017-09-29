package com.ly.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import wjava.AppConstants;
import wjava.SpUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ӭҳ
 * 
 * @author wwj_748   �����ϰ��˶���~
 * 
 */
public class WelcomeGuideActivity extends Activity implements OnClickListener {

	private ViewPager vp;
	private GuideViewPagerAdapter adapter;
	private List<View> views;
	private Button startBtn;

	// ����ҳͼƬ��Դ
	private static final int[] pics = { R.layout.view11,R.layout.view22, R.layout.view33, R.layout.view44 };

	// �ײ�С��ͼƬ
	private ImageView[] dots;

	// ��¼��ǰѡ��λ��
	private int currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_guide);

		views = new ArrayList<View>();

		// ��ʼ������ҳ��ͼ�б�
		for (int i = 0; i < pics.length; i++) {
			View view = LayoutInflater.from(this).inflate(pics[i], null);
			
			if (i == pics.length - 1) {
				startBtn = (Button) view.findViewById(R.id.btn_login);
				startBtn.setTag("enter");
				startBtn.setOnClickListener(this);
			}
			
			views.add(view);

		}

		vp = (ViewPager) findViewById(R.id.vp_guide);
		// ��ʼ��adapter
		adapter = new GuideViewPagerAdapter(views);
		vp.setAdapter(adapter);
		vp.setOnPageChangeListener(new PageChangeListener());

		initDots();
		
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ����л�����̨���������´β����빦������ҳ
		SpUtils.putBoolean(WelcomeGuideActivity.this, AppConstants.FIRST_OPEN, true);
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		dots = new ImageView[pics.length];

		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < pics.length; i++) {
			// �õ�һ��LinearLayout�����ÿһ����Ԫ��
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(false);// ����Ϊ��ɫ
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// ����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(true); // ����Ϊ��ɫ����ѡ��״̬

	}

	/**
	 * ���õ�ǰview
	 * 
	 * @param position
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}
		vp.setCurrentItem(position);
	}

	/**
	 * ���õ�ǰָʾ��
	 * 
	 * @param position
	 */
	private void setCurDot(int position) {
		if (position < 0 || position > pics.length || currentIndex == position) {
			return;
		}
		dots[position].setEnabled(true);
		dots[currentIndex].setEnabled(false);
		currentIndex = position;
	}

	@Override
	public void onClick(View v) {
		if (v.getTag().equals("enter")) {
			enterMainActivity();
			return;
		}
		
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}
	
	
	private void enterMainActivity() {
		Intent intent = new Intent(WelcomeGuideActivity.this,LYTabHostActivity.class);
		startActivity(intent);
		SpUtils.putBoolean(WelcomeGuideActivity.this, AppConstants.FIRST_OPEN, true);
		finish();
	}

	private class PageChangeListener implements OnPageChangeListener {
		// ������״̬�ı�ʱ����
		@Override
		public void onPageScrollStateChanged(int position) {
			// arg0 ==1��ʱ��Ĭʾ���ڻ�����arg0==2��ʱ��Ĭʾ��������ˣ�arg0==0��ʱ��Ĭʾʲô��û����
		}

		// ��ǰҳ�汻����ʱ����
		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			// arg0 :��ǰҳ�棬������������ҳ��
			// arg1:��ǰҳ��ƫ�Ƶİٷֱ�
			// arg2:��ǰҳ��ƫ�Ƶ�����λ��

		}

		// ���µ�ҳ�汻ѡ��ʱ����
		@Override
		public void onPageSelected(int position) {
			// ���õײ�С��ѡ��״̬
			setCurDot(position);
		}

	}
}
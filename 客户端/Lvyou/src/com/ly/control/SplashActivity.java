package com.ly.control;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TableLayout;

import wjava.AppConstants;
import wjava.SpUtils;

/**
 * @desc ������
 * Created by devilwwj on 16/1/23.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // �ж��Ƿ��ǵ�һ�ο���Ӧ��
        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        // ����ǵ�һ�����������Ƚ��빦������ҳ
        if (!isFirstOpen) {
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // ������ǵ�һ������app����������ʾ������
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);
    }

    private void enterHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, LYTabHostActivity.class);
        startActivity(intent);
        finish();
    }
}
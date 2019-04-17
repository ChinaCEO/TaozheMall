package com.taozhemall.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {

  private Timer mTimer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    init();
  }
  /**
   *  初始化
   */
  private void  init() {
    mTimer = new Timer();
    mTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        toMain();
      }
    }, 3000);
  }

  private  void  toMain() {

    Intent intent = new Intent(SplashActivity.this, com.taozhemall.app.WXPageActivity.class);
    Uri data = getIntent().getData();
    if (data != null) {
      intent.setData(data);
    }
    intent.putExtra("from", "splash");
    startActivity(intent);
    finish();
  }
}


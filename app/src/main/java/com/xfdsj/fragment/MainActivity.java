package com.xfdsj.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_fragment_life) void fragmentLife() {
    Intent intent = new Intent(this, FragmentLifeActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.btn_fragment_retain) void fragmentRetain() {
    Intent intent = new Intent(this, FragmentRetainDataActivity.class);
    startActivity(intent);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }
}

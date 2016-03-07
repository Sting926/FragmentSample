package com.xfdsj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentRetainDataActivity extends Activity implements RetainedFragment.LoadComplete {

  private RetainedFragment retainedFragment;
  private ProgressDialogFragment progressDialog;
  @Bind(R.id.iv_pic) ImageView ivPic;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.a_fragment_retain_data);
    ButterKnife.bind(this);
    // find the retained fragment on activity restarts
    retainedFragment = (RetainedFragment) getFragmentManager().findFragmentByTag("data");
    progressDialog = (ProgressDialogFragment) getFragmentManager().findFragmentByTag("dialog");

    // create the fragment and data the first time
    if (retainedFragment == null) {
      // add the fragment
      retainedFragment = new RetainedFragment();
      getFragmentManager().beginTransaction().add(retainedFragment, "data").commit();
    } else {
      ivPic.setImageBitmap(retainedFragment.getPic());//注掉这行后横竖屏切换后图片消失
    }
  }

  @OnClick(R.id.iv_pic) void picClick() {
    if (ivPic.getDrawable() != null) return;
    progressDialog = new ProgressDialogFragment();
    progressDialog.show(getFragmentManager(), "dialog");
    retainedFragment.loadPic("http://img.blog.csdn.net/20160307170349803");
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }

  @Override public void onComplete() {
    runOnUiThread(new Runnable() {
      @Override public void run() {
        ivPic.setImageBitmap(retainedFragment.getPic());
        progressDialog.dismiss();
      }
    });
  }
}

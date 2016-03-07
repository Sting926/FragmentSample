package com.xfdsj.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentRetainDataActivity extends Activity {

  private RetainedFragment retainedFragment;
  private ProgressDialogFragment progressDialog;
  @Bind(R.id.iv_pic) ImageView ivPic;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.a_fragment_retain_data);
    ButterKnife.bind(this);
    // find the retained fragment on activity restarts
    retainedFragment = (RetainedFragment) getFragmentManager().findFragmentByTag("data");

    // create the fragment and data the first time
    if (retainedFragment == null) {
      // add the fragment
      retainedFragment = new RetainedFragment();
      getFragmentManager().beginTransaction().add(retainedFragment, "data").commit();
    }else {
      ivPic.setImageBitmap(retainedFragment.getPic());
    }
  }

  @OnClick(R.id.iv_pic) void picClick() {
    if (ivPic.getDrawable() != null)
      return;
    progressDialog = new ProgressDialogFragment();
    progressDialog.show(getFragmentManager(), "dialog");
    OkHttpClient client = new OkHttpClient();
    Request request =
        new Request.Builder().url("http://img.blog.csdn.net/20160307163830263")
            .build();
    client.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
          @Override public void run() {
            ivPic.setImageBitmap(bitmap);
            retainedFragment.setPic(bitmap);
            progressDialog.dismiss();
          }
        });
      }
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
  }
}

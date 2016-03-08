package com.xfdsj.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetainedFragment extends BaseFragment {

  private Bitmap mBitmap;

  private LoadComplete loadComplete;

  public interface LoadComplete {
    void onComplete();
  }

  public RetainedFragment() {
    // Required empty public constructor
    setRetainInstance(true);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      if (activity instanceof LoadComplete) {
        loadComplete = (LoadComplete) activity;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    loadComplete = null;
  }

  public void loadPic(String url) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    client.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
        try {
          Thread.sleep(5000);
          mBitmap = bitmap;
          if (loadComplete != null) {
            loadComplete.onComplete();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }

  public Bitmap getPic() {
    return mBitmap;
  }
}

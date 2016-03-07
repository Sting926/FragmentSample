package com.xfdsj.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetainedFragment extends BaseFragment {

  private Bitmap mBitmap;

  public RetainedFragment() {
    // Required empty public constructor
    setRetainInstance(true);
  }

  public void setPic(Bitmap bitmap){
    mBitmap = bitmap;
  }

  public Bitmap getPic(){
    return mBitmap;
  }
}

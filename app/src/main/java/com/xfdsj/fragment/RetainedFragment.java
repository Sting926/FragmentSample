package com.xfdsj.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetainedFragment extends Fragment {

  public RetainedFragment() {
    // Required empty public constructor
    setRetainInstance(true);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  public void setPic(Bitmap b){

  }

  public Bitmap getPic(){
    return null;
  }
}

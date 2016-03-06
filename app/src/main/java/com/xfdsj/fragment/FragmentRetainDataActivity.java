package com.xfdsj.fragment;

import android.app.Activity;
import android.os.Bundle;

public class FragmentRetainDataActivity extends Activity {

  private RetainedFragment retainedFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.a_fragment_retain_data);
    // find the retained fragment on activity restarts
    retainedFragment = (RetainedFragment) getFragmentManager().findFragmentByTag("data");

    // create the fragment and data the first time
    if (retainedFragment == null) {
      // add the fragment
      retainedFragment = new RetainedFragment();
      getFragmentManager().beginTransaction().add(retainedFragment, "data").commit();
      // load the data from the web
      retainedFragment.setPic(null);
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    retainedFragment.setPic(null);
  }
}

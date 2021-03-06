package com.xfdsj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentB extends BaseFragment {

  ActionB actionB;

  public FragmentB() {
  }

  public interface ActionB {
    void actionB();
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      if (activity instanceof ActionB) {
        actionB = (ActionB) activity;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.f_b, container, false);
    TextView textView = (TextView) view.findViewById(R.id.tv_b);
    textView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        actionB.actionB();
      }
    });
    return view;
  }
}

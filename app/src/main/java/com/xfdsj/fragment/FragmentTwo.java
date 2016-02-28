package com.xfdsj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentTwo extends BaseFragment {

  FragmentTwoAction fragmentTwoAction;

  public FragmentTwo() {
  }

  public interface FragmentTwoAction {
    void ActionTwo();
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      if (activity instanceof FragmentTwoAction) {
        fragmentTwoAction = (FragmentTwoAction) activity;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_two, container, false);
    ImageView imageView = (ImageView) view.findViewById(R.id.btn);
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        fragmentTwoAction.ActionTwo();
      }
    });
    return view;
  }
}

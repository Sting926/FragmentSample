package com.xfdsj.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentThree extends BaseFragment {

  public FragmentThree() {
  }

  public interface FragmentThreeAction{
    void ActionThree();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_three, container, false);
    ImageView imageView = (ImageView) view.findViewById(R.id.btn);
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

      }
    });
    return view;
  }
}

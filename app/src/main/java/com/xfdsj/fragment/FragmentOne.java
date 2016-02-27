package com.xfdsj.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentOne extends Fragment {
  FragmentOneAction fragmentOneAction;

  public FragmentOne() {
  }

  public interface FragmentOneAction {
    void ActionOne();
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      if (activity instanceof FragmentOneAction) {
        fragmentOneAction = (FragmentOneAction) activity;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_one, container, false);
    ImageView imageView = (ImageView) view.findViewById(R.id.btn);
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        fragmentOneAction.ActionOne();
      }
    });
    return view;
  }
}

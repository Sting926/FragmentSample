package com.xfdsj.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {

  public BaseFragment() {
    // Required empty public constructor
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    LogUtils.e(this, "onAttach");
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LogUtils.e(this, "onCreate");
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    LogUtils.e(this, "onCreateView");
    return null;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    LogUtils.e(this, "onActivityCreated");
  }

  @Override public void onStart() {
    super.onStart();
    LogUtils.e(this, "onStart");
  }

  @Override public void onResume() {
    super.onResume();
    LogUtils.e(this, "onResume");
  }

  @Override public void onPause() {
    super.onPause();
    LogUtils.e(this, "onPause");
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    LogUtils.e(this, "onSaveInstanceState");
  }

  @Override public void onStop() {
    super.onStop();
    LogUtils.e(this, "onStop");
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    LogUtils.e(this, "onDestroyView");
  }

  @Override public void onDestroy() {
    super.onDestroy();
    LogUtils.e(this, "onDestroy");
  }

  @Override public void onDetach() {
    super.onDetach();
    LogUtils.e(this, "onDetach");
  }
}

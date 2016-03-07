package com.xfdsj.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressDialogFragment extends DialogFragment {

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    ProgressDialog progressDialog = new ProgressDialog(getActivity());
    progressDialog.setTitle("加载中...");
    return progressDialog;
  }
}

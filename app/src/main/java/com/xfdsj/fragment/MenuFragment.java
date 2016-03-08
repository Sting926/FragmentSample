package com.xfdsj.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MenuFragment extends BaseFragment {

  public MenuFragment() {
    setHasOptionsMenu(true);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_fragment, menu);
    LogUtils.e(this, "onCreateOptionsMenu");
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    switch (id) {
      case R.id.action_fragment_menu1:
        Toast.makeText(getActivity(), "Fragment menu1", Toast.LENGTH_SHORT).show();
        return true;
      case R.id.action_fragment_menu2:
        Toast.makeText(getActivity(), "Fragment menu2", Toast.LENGTH_SHORT).show();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}

package com.xfdsj.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MenuFragment extends ListFragment {

  public MenuFragment() {
    setHasOptionsMenu(true);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    List<String> data = new ArrayList<>();
    data.add("RxJava");
    data.add("RxAndroid");
    data.add("Dagger2");
    data.add("Retrofit");
    setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data));
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

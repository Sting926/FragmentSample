package com.xfdsj.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, data));
    ListView listView = getListView();
    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
    listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener(){

      @Override public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
        return true;
      }

      @Override public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
      }

      @Override public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
          case R.id.action_context_menu1:
            mode.finish();
            return true;
          case R.id.action_context_menu2:
            mode.finish();
            return true;
        }
        return false;
      }

      @Override public void onDestroyActionMode(ActionMode mode) {

      }

      @Override public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
          boolean checked) {

      }
    });
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_fragment, menu);
    LogUtils.e(this, "onCreateOptionsMenu");
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
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

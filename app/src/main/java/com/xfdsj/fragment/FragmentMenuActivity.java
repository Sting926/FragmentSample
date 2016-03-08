package com.xfdsj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class FragmentMenuActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.a_fragment_menu);

    if (savedInstanceState == null) {
      MenuFragment menuFragment = new MenuFragment();
      getFragmentManager().beginTransaction().add(R.id.content_main, menuFragment).commit();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      Toast.makeText(this, "Activity Menu", Toast.LENGTH_SHORT).show();
      return true;
    }else if (id == R.id.action_fragment_menu2){
      Toast.makeText(this, "Activity 截胡菜单", Toast.LENGTH_SHORT).show();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}

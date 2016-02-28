package com.xfdsj.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity
    implements FragmentOne.FragmentOneAction, FragmentTwo.FragmentTwoAction {

  private FragmentOne fragmentOne;
  private FragmentTwo fragmentTwo;
  private FragmentThree fragmentThree;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    fragmentOne = new FragmentOne();
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.add(R.id.content_main, fragmentOne).commit();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void ActionOne() {
    if (fragmentTwo == null) {
      fragmentTwo = new FragmentTwo();
    }
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.content_main, fragmentTwo).addToBackStack(null).commit();
  }

  @Override public void ActionTwo() {
    if (fragmentThree == null) {
      fragmentThree = new FragmentThree();
    }
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.hide(fragmentTwo)
        .add(R.id.content_main, fragmentThree)
        .addToBackStack(null)
        .commit();
  }
}

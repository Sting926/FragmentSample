package com.xfdsj.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity
    implements FragmentA.ActionA, FragmentB.ActionB, FragmentC.ActionC {

  private FragmentA fragmentA;
  private FragmentB fragmentB;
  private FragmentC fragmentC;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    fragmentA = (FragmentA) getFragmentManager().findFragmentByTag("A");
    fragmentB = (FragmentB) getFragmentManager().findFragmentByTag("B");
    fragmentC = (FragmentC) getFragmentManager().findFragmentByTag("C");
    if (fragmentA == null) {
      fragmentA = new FragmentA();
      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
      fragmentTransaction.add(R.id.content_main, fragmentA, "A").commit();
    }
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
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

  @Override public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
  }

  @Override public void actionA() {
    if (fragmentB == null) {
      fragmentB = new FragmentB();
    }
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.content_main, fragmentB, "B").addToBackStack(null).commit();
  }

  @Override public void actionB() {
    if (fragmentC == null) {
      fragmentC = new FragmentC();
    }
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.hide(fragmentB)
        .add(R.id.content_main, fragmentC, "C")
        .addToBackStack(null)
        .commit();
  }

  @Override public void actionC() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.detach(fragmentC).addToBackStack(null).commit();
    Toast.makeText(this, "FragmentC 已被 detach", Toast.LENGTH_SHORT).show();
  }
}

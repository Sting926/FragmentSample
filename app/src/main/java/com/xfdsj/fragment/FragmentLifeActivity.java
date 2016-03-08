package com.xfdsj.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class FragmentLifeActivity extends Activity
    implements FragmentA.ActionA, FragmentB.ActionB, FragmentC.ActionC {

  private FragmentA fragmentA;
  private FragmentB fragmentB;
  private FragmentC fragmentC;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.a_fragment_life);

    fragmentA = (FragmentA) getFragmentManager().findFragmentByTag("A");
    fragmentB = (FragmentB) getFragmentManager().findFragmentByTag("B");
    fragmentC = (FragmentC) getFragmentManager().findFragmentByTag("C");
    if (fragmentA == null) {
      fragmentA = new FragmentA();
      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
      fragmentTransaction.add(R.id.content_main, fragmentA, "A").commit();
    }
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

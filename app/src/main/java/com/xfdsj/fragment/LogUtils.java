package com.xfdsj.fragment;

import android.util.Log;

/**
 * Created by jian on 2015/6/5.
 */
public class LogUtils {

  public static boolean isLog = true;

  private LogUtils() { /* cannot be instantiated */}

  public static void v(Object c, String msg) {
    if (isLog) {
      Log.v(getTag(c), getMsg(msg));
    }
  }

  public static void d(Object c, String msg) {
    if (isLog) {
      Log.d(getTag(c), getMsg(msg));
    }
  }

  public static void i(Object c, String msg) {
    if (isLog) {
      Log.i(getTag(c), getMsg(msg));
    }
  }

  public static void w(Object c, String msg) {
    if (isLog) {
      Log.w(getTag(c), getMsg(msg));
    }
  }

  public static void wtf(Object c, String msg) {
    if (isLog) {
      Log.wtf(getTag(c), getMsg(msg));
    }
  }

  public static void e(Object c, String msg) {
    if (isLog) {
      Log.e(getTag(c), getMsg(msg));
    }
  }

  private static String getTag(Object c) {
    return c.getClass().getSimpleName() + "@" + c.hashCode();
  }

  private static String getMsg(String msg) {
    return msg + ";";
  }
}

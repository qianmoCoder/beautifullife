package com.ddu.icore.aspect;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ddu.icore.annotation.AspectJ;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import java.util.LinkedHashMap;

/**
 * Created by yzbzz on 2018/1/10.
 */

public class AspectHelper {

    public static void sendEventForData(JoinPoint joinPoint, AspectJ aspectJ) {

        int[] sid = aspectJ.id();
        String value = aspectJ.value();
        Log.v("lhz", "aspectJ id: " + sid + " value:" + value);
        String fileName = joinPoint.getSourceLocation().getFileName();
        Object target = joinPoint.getTarget();

        Object joinPointThis = joinPoint.getThis();
        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
        String king = joinPoint.getKind();
        Signature signature = joinPoint.getSignature();

        LinkedHashMap<String, String> params = new LinkedHashMap();
        params.put("log", "evt");
        params.put("evt", "click");

        if (null != target) {
            StringBuilder sb = new StringBuilder();
            if (target instanceof View) {
                View tempTarget = (View) target;
                Context context = tempTarget.getContext();
                if (null != context) {
                    sb.append(context.getClass().getName());
                    sb.append("_");
                }
            }
            sb.append(target.getClass().getName());
            params.put("pgId", sb.toString());
        }

        View view = null;
        Object[] args = joinPoint.getArgs();
        if (null != args) {
            for (Object arg : args) {
                if (arg instanceof View) {
                    view = ((View) arg);
                    break;
                }
            }
        }
        if (view != null) {
            int id = view.getId();
            params.put("ctrlId", "" + id);
            try {
                if (id != -1) {
                    params.put("ctrln", view.getResources().getResourceEntryName(view.getId()));
                } else if (view instanceof TextView) {
                    params.put("ctrln", ((TextView) view).getText().toString());
                } else {
                    String ctrlnName = view.getClass().getName();
                    Object tag = view.getTag();
                    if (tag instanceof String) {
                        String t = (String) tag;
                        if (!TextUtils.isEmpty(t)) {
                            ctrlnName = t;
                        }
                    }
                    params.put("ctrln", ctrlnName);
                }
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            params.put("pt", location[0] + "_" + location[1]);
        }
        Log.v("lhz", "pgId-ctrlId: " + params.get("pgId") + "-" + params.get("ctrln"));
        params.put("fileName", fileName);

        params.put("ts", System.currentTimeMillis() + "");
        params.put("activity_id", "app");
        params.put("devId", Build.SERIAL);

    }

    public static void sendEventForData(JoinPoint joinPoint) {

        String fileName = joinPoint.getSourceLocation().getFileName();
        Object target = joinPoint.getTarget();

        Object joinPointThis = joinPoint.getThis();
        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
        String king = joinPoint.getKind();
        Signature signature = joinPoint.getSignature();

        LinkedHashMap<String, String> params = new LinkedHashMap();
        params.put("log", "evt");
        params.put("evt", "click");

        if (null != target) {
            StringBuilder sb = new StringBuilder();
            if (target instanceof View) {
                View tempTarget = (View) target;
                Context context = tempTarget.getContext();
                if (null != context) {
                    sb.append(context.getClass().getName());
                    sb.append("_");
                }
            }
            sb.append(target.getClass().getName());
            params.put("pgId", sb.toString());
        }

        View view = null;
        Object[] args = joinPoint.getArgs();
        if (null != args) {
            for (Object arg : args) {
                if (arg instanceof View) {
                    view = ((View) arg);
                    break;
                }
            }
        }
        if (view != null) {
            int id = view.getId();
            params.put("ctrlId", "" + id);
            try {
                if (id != -1) {
                    params.put("ctrln", view.getResources().getResourceEntryName(view.getId()));
                } else if (view instanceof TextView) {
                    params.put("ctrln", ((TextView) view).getText().toString());
                } else {
                    String ctrlnName = view.getClass().getName();
                    Object tag = view.getTag();
                    if (tag instanceof String) {
                        String t = (String) tag;
                        if (!TextUtils.isEmpty(t)) {
                            ctrlnName = t;
                        }
                    }
                    params.put("ctrln", ctrlnName);
                }
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            params.put("pt", location[0] + "_" + location[1]);
        }
        Log.v("lhz", "pgId-ctrlId: " + params.get("pgId") + "-" + params.get("ctrln"));
        params.put("fileName", fileName);

        params.put("ts", System.currentTimeMillis() + "");
        params.put("activity_id", "app");
        params.put("devId", Build.SERIAL);

    }
}

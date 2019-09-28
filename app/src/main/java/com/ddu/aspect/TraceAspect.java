package com.ddu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by yzbzz on 2017/9/6.
 */
@Aspect
public class TraceAspect {

    // normal onClick
    private static final String ON_CLICK_POINTCUTS = "execution(* android.view.View.OnClickListener.onClick(..))";
    private static final String ON_CLICK_POINTCUTS_TOUCH = "execution(* android.view.View.OnTouchListener.onTouch(..))";
//    private static final String ON_CLICK_POINTCUTS_L = "execution(void *..lambda*(*..View))";
    private static final String ON_CLICK_POINTCUTS_L = "execution(*..(*..))";
    // 如果 onClick 是写在 xml 里面的
    private static final String ON_CLICK_IN_XML_POINTCUTS = "execution(* android.support.v7.app.AppCompatViewInflater.DeclaredOnClickListener.onClick(..))";
    // butterknife on click
    private static final String ON_CLICK_IN_BUTTER_KNIFE_POINTCUTS = "execution(@butterknife.OnClick * *(..))";
    private static final String ON_TOUCH_IN_BUTTER_KNIFE_POINTCUTS = "execution(@butterknife.onTouch * *(..))";

    @Pointcut(ON_CLICK_POINTCUTS)
    public void onClickPointcuts() {
    }

    @Pointcut(ON_CLICK_POINTCUTS_TOUCH)
    public void onTouchPointcuts() {
    }

    @Pointcut(ON_CLICK_POINTCUTS_L)
    public void onLambdaPointcuts() {
    }

    @Pointcut(ON_CLICK_IN_XML_POINTCUTS)
    public void onClickInXmlPointcuts() {
    }

    @Pointcut(ON_CLICK_IN_BUTTER_KNIFE_POINTCUTS)
    public void onClickInButterKnifePointcuts() {
    }

    @Pointcut(ON_TOUCH_IN_BUTTER_KNIFE_POINTCUTS)
    public void onTouchInButterKnifePointcuts() {
    }

    @After("onClickPointcuts() || onTouchPointcuts()||onLambdaPointcuts() || onClickInButterKnifePointcuts() || onTouchInButterKnifePointcuts()")
    public void onActivityMethodOnClickBefore1(JoinPoint joinPoint) throws Throwable {
        try {
            AspectHelper.sendEventForData(joinPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @After(ON_CLICK_IN_BUTTER_KNIFE_POINTCUTS)
//    public void onActivityMethodOnClickBefore2(JoinPoint joinPoint) throws Throwable {
//        try {
//            AspectHelper.sendEventForData(joinPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After(ON_CLICK_POINTCUTS_L)
//    public void onActivityMethodOnClickBefore3(JoinPoint joinPoint) throws Throwable {
//        try {
//            AspectHelper.sendEventForData(joinPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

//    @After(ON_CLICK_IN_XML_POINTCUTS)
//    public void onActivityMethodOnClickBefore3(JoinPoint joinPoint) throws Throwable {
//        try {
//            AspectHelper.sendEventForData(joinPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @After(ON_CLICK_IN_BUTTER_KNIFE_POINTCUTS)
//    public void onActivityMethodOnClickBefore4(JoinPoint joinPoint) throws Throwable {
//        try {
//            AspectHelper.sendEventForData(joinPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @After(ON_CLICK_POINTCUTS_TOUCH)
//    public void onActivityMethodOnClickBefore5(JoinPoint joinPoint) throws Throwable {
//        try {
//            AspectHelper.sendEventForData(joinPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}

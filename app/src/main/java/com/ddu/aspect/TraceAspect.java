package com.ddu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by yzbzz on 2017/9/6.
 */
@Aspect
public class TraceAspect {

    private static final String BEFORE_DISPATCH_TOUCH = "execution(* android.view.View.dispatchPointerEvent(..))";
    private static final String BEFORE_DISPATCH_TOUCH_VIEWGROUP = "execution(* android.view.View.onFilterTouchEventForSecurity(..))";

    private static final String BEFORE_ONTOUCH = "execution(* android.view.View.OnTouchListener.onTouch(..))";

    private static final String BEFORE_ONCLICK = "execution(* android.view.View.OnClickListener.onClick(..))";

    private static final String BEFORE_ACTIVITY_ONCREATE = "execution(* android.app.Activity.onCreate(..))";
    private static final String BEFORE_FRAGMENT_ONCREATE = "execution(* android.support.v4.app.Fragment.onCreate(..))";

    private static final String BEFORE_ASPECTJ = "execution(@com.ddu.annotation.AspectJ * *(..))";

//    @Before(BEFORE_DISPATCH_TOUCH_VIEWGROUP)
//    public boolean onActivityMethodDispatchTouchEventBefore(ProceedingJoinPoint joinPoint) throws Throwable {
//        boolean temp = false;
//        try {
//            Log.v("lhz", "dispatch_touch");
//            temp = (boolean)joinPoint.proceed();
//            AspectHelper.sendEventForData(joinPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//            temp = false;
//        }
//        return temp;
//
//    }

//    @Around(BEFORE_ONTOUCH)
//    public boolean onActivityMethodOnTouchBefore(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            joinPoint.proceed();
//            Log.v("lhz", "onTouch");
//            AspectHelper.sendEventForData(joinPoint);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    @Before(BEFORE_ONCLICK)
    public void onActivityMethodOnClickBefore(JoinPoint joinPoint) throws Throwable {
        try {
            AspectHelper.sendEventForData(joinPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Before(BEFORE_ASPECTJ)
//    public void onAspectjBefore(JoinPoint joinPoint, AspectJ aspectJ) throws Throwable {
//        try {
//            AspectHelper.sendEventForData(joinPoint, aspectJ);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Before(BEFORE_FRAGMENT_ONCREATE)
    public void onFragmentMethodOnCreateBefore(JoinPoint joinPoint) throws Throwable {
    }

    @Before(BEFORE_ACTIVITY_ONCREATE)
    public void onActivityMethodOnCreateBefore(JoinPoint joinPoint) throws Throwable {
    }

}

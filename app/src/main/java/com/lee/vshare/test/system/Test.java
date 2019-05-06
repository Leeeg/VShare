package com.lee.vshare.test.system;

import android.content.Context;
import android.telephony.SubscriptionManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * CreateDate：19-3-27 on 下午5:58
 * Describe:
 * Coder: lee
 */
public class Test {

    //获取当前数据卡信息
    public static Integer getDefaultDataSubId(Context context)
    {
        Integer id = -1;


        try {
            SubscriptionManager sm = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                sm = SubscriptionManager.from(context.getApplicationContext());
                Method getSubId = sm.getClass().getMethod("getDefaultDataSubId");
                if(getSubId != null)
                {
                    id = (int) getSubId.invoke(sm);
                }
            }
        } catch (NoSuchMethodException e) {
            try {
                SubscriptionManager sm = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                    sm = SubscriptionManager.from(context.getApplicationContext());
                    Method getSubId = sm.getClass().getMethod("getDefaultDataSubscrptionId");
                    if(getSubId != null)
                    {
                        id = (int) getSubId.invoke(sm);
                    }
                }
            } catch (NoSuchMethodException e1) {
//
                try {
                    SubscriptionManager sm = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                        sm = SubscriptionManager.from(context.getApplicationContext());
                        Method getSubId = sm.getClass().getMethod("getDefaultDataPhoneId");
//            Method getSubId = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("getSubId", new Class[]{Integer.TYPE});
                        if(getSubId != null)
                        {
                            id = (int) getSubId.invoke(sm);
                            Log.v("",(int) getSubId.invoke(sm) + "");
                        }
                    }
                } catch (NoSuchMethodException e2) {
                    e.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e.printStackTrace();
                } catch (InvocationTargetException e2) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e1) {
                e.printStackTrace();
            } catch (InvocationTargetException e1) {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return  id;

    }

}

package com.example.timedifferenceutils.utils;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.timedifferenceutils.base.App;
import com.example.timedifferenceutils.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态加载fragment
 */
public class FragmentBuilder {

    //    记录上一个fragment
    public static BaseFragment lastFragment;
    private static Map<String ,Integer> containIds = new HashMap();
    /**
     *切换fragment的方法
     * @param fragmentClass  要加载的fragment
     * @param containId     容器ID
     * @param isHidden      是否隐藏
     * @param isBack        是否添加回退栈
     * @return
     */
    public static BaseFragment changeFragment(Class<? extends BaseFragment> fragmentClass
            ,int containId,boolean isHidden,boolean isBack){
        FragmentManager manager = App.getAcitivityContext().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        获取当前类名，当做tag
        String simpleName = fragmentClass.getSimpleName();
        BaseFragment currentFragment = (BaseFragment) manager.findFragmentByTag(simpleName);

        if(currentFragment == null) {
            try {
                currentFragment = fragmentClass.newInstance();
                transaction.add(containId,currentFragment,simpleName);
                containIds.put(simpleName,containId);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else {
            if(containId != containIds.get(simpleName)) {
                currentFragment = null;
                try {
                    currentFragment = fragmentClass.newInstance();
                    transaction.add(containId,currentFragment,simpleName);
                    containIds.put(simpleName,containId);

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if(isHidden) {
//            隐藏上一个fragment
            if(lastFragment != null && isBack) {
                transaction.hide(lastFragment);
            }
//            显示当前fragment
            transaction.show(currentFragment);
        }else {
            transaction.replace(containId,currentFragment,simpleName);
        }
        if(isBack) {
            transaction.addToBackStack(simpleName);
            lastFragment  = currentFragment;
        }
        transaction.commit();
        return lastFragment;
    }
}

package com.example.timedifferenceutils.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.timedifferenceutils.manager.ActivityStackManager;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author DeepCoding 2017.10.25
 * @version 1.0
 */
public abstract class BaseActivity extends RxFragmentActivity {

    Unbinder mUnbinder;

    protected Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        // 隐藏软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // 隐藏actionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityStackManager.getInstance().addActivity(this);

        View inflate = View.inflate(this, getLayoutId(), null);
        setContentView(inflate);

        mUnbinder = ButterKnife.bind(this);
        initView(inflate);
        initListenter(inflate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //bugtags记录按键操作步骤
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().finishActivity(this);
        mUnbinder.unbind();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initListenter(View view);

    protected abstract void initData();

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 1000) {
            exitTime = System.currentTimeMillis();
        } else {
            ActivityStackManager.getInstance().AppExit(this);
            System.exit(0);
        }
    }



}

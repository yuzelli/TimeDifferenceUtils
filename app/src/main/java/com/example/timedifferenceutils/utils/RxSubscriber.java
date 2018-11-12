package com.example.timedifferenceutils.utils;


import rx.Subscriber;

/**
 * Created by a on 2016/5/6.
 */
public abstract class RxSubscriber<T> extends Subscriber<T>{
    @Override
    public void onCompleted() {
        //ProgressDialogUtil.dismiss();

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public abstract void _onNext(T t);

    public abstract void _onError(String msg);
}

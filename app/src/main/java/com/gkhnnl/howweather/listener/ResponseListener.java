package com.gkhnnl.howweather.listener;

/**
 * Created by gkhnnl on 03/09/16.
 */
public interface ResponseListener<T> {
    void onSuccess(T obj);
    void onError();
}

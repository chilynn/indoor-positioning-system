package com.neu.wifilocalization.mvc;

public interface RequestCallback<T> {

    public void execute(T obj);

}

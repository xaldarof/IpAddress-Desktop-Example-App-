package com.company.data;

public interface ConnectionCallBack {
    void onSuccess(String ip);
    void onError(Exception e);
}

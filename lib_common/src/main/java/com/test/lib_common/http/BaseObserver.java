package com.test.lib_common.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.test.lib_common.dialog.RxDialogShapeLoading;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserver<T> implements Observer<HttpResponse<T>> {

    private int errorCode;
    private String errorMsg ;
    private final int RESPONSE_CODE_FAILED = -1;
    private Disposable disposable;
    private final RxDialogShapeLoading mRxDialogShapeLoading;
    //    private final Dialog mLoadingDialog;

    public BaseObserver(Context mContext) {
//        mLoadingDialog = DialogUtils.createLoadingDialog(mContext, "加载中...");
        mRxDialogShapeLoading = new RxDialogShapeLoading(mContext);
        mRxDialogShapeLoading.setLoadingText("加载中...");
        mRxDialogShapeLoading.show();
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        onRequestStart();
    }

    @Override
    public void onNext(HttpResponse<T> response) {
        Log.i("OkHttp",new Gson().toJson(response));
        try {
            if (response.getCode() == 1 || !response.error) {
//                DialogUtils.closeDialog(mLoadingDialog);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRxDialogShapeLoading.dismiss();
                    }
                },3000);

                onSuccess(response);
            }else {
//                DialogUtils.closeDialog(mLoadingDialog);
                mRxDialogShapeLoading.dismiss();
                onFailure(response.getCode(), response.getMsg());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
//        DialogUtils.closeDialog(mLoadingDialog);
        mRxDialogShapeLoading.dismiss();
        try {
            if (t instanceof HttpException) {
                HttpException httpException = (HttpException) t;
                errorCode = httpException.code();
                errorMsg = httpException.getMessage();
            } else if (t instanceof SocketTimeoutException) {
                SocketTimeoutException timeoutException = (SocketTimeoutException) t;
                errorCode = RESPONSE_CODE_FAILED;
                errorMsg = timeoutException.getMessage();
            }else {
                errorMsg = t.getMessage();
                errorCode = RESPONSE_CODE_FAILED;
            }
            // .....其他的异常处理
            onFailure(errorCode, errorMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(HttpResponse<T> t)throws Exception;
    protected abstract void onFailure(int errorCode, String errorMsg)throws Exception;

    protected void onRequestStart() {
    }
}

package com.test.lib_common.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.test.lib_common.config.Config;
import com.test.lib_common.dialog.RxDialogShapeLoading;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
/**
 * 对retrofit和rxjava请求结果封装
 */
public abstract class BaseObserver<T> implements Observer<HttpResponse<T>> {

    private int errorCode;
    private String errorMsg ;
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
            if (response.getCode() == Config.RESPONSE_CODE_SUCCESS || !response.error) {
//                DialogUtils.closeDialog(mLoadingDialog);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRxDialogShapeLoading.dismiss();
                    }
                },Config.HANDLER_POSTDELAYED_TIME);

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
                errorCode = Config.RESPONSE_CODE_FAILED;
                errorMsg = timeoutException.getMessage();
            }else {
                errorMsg = t.getMessage();
                errorCode = Config.RESPONSE_CODE_FAILED;
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

    /**
     * 请求成功回调
     * @param t 请求成功实体
     */
    public abstract void onSuccess(HttpResponse<T> t)throws Exception;

    /**
     * 请求失败回调
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     */
    protected abstract void onFailure(int errorCode, String errorMsg)throws Exception;

    protected void onRequestStart() {
    }
}

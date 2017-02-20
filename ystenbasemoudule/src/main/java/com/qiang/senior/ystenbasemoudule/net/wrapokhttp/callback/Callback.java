package com.qiang.senior.ystenbasemoudule.net.wrapokhttp.callback;

//import com.istv.ystframework.thirdParty.okhttp3.Call;
//import com.istv.ystframework.thirdParty.okhttp3.Request;
//import com.istv.ystframework.thirdParty.okhttp3.Response;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangqiang on 2016/5/6.
 */
public abstract class Callback<T> {

    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request) {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter() {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total ) {

    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(Call call, Exception e);

    public abstract void onResponse(T response);


    public static Callback CALLBACK_DEFAULT = new Callback() {

        @Override
        public Object parseNetworkResponse(Response response) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e) {

        }

        @Override
        public void onResponse(Object response) {

        }
    };

}

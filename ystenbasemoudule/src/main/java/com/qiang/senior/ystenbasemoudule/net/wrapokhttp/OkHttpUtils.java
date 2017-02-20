package com.qiang.senior.ystenbasemoudule.net.wrapokhttp;

import android.os.Handler;
import android.os.Looper;

import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.builder.GetBuilder;
import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.builder.PostStringBuilder;
import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.builder.PostformBuilder;
import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.callback.Callback;
import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.request.RequestCall;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangQ
 * on 2017/2/10.
 */

public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10000;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            mOkHttpClient = okHttpClientBuilder.build();
        } else {
            mOkHttpClient = okHttpClient;
        }

        init();
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(null);
                }
            }
        }
        return mInstance;
    }

    private void init() {
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }

    public static GetBuilder get()
    {
        return new GetBuilder();
    }
    public static PostformBuilder post()
    {
        return new PostformBuilder();
    }

    public static PostStringBuilder postString()
    {
        return new PostStringBuilder();
    }

    public static PostformBuilder postform(){
        return  new PostformBuilder();
    }
    public void execute(final RequestCall requestCall, Callback callback)
    {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;

        requestCall.getCall().enqueue(new okhttp3.Callback()
        {
            @Override
            public void onFailure(Call call, final IOException e)
            {
                sendFailResultCallback(call, e, finalCallback);
            }
            @Override
            public void onResponse(final Call call, final Response response)
            {

                try
                {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendSuccessResultCallback(o, finalCallback);
                } catch (Exception e)
                {
                    sendFailResultCallback(call, e, finalCallback);

                }

            }

        });
    }

    public boolean isTimeOut(Exception e){
        if (e instanceof SocketTimeoutException) {
            return true;
        }else{
            return false;
        }
    }
    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback)
    {
        if (callback == null) return;

        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback)
    {
        if (callback == null) return;
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }

    public String syncHttp(String url){

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                //				System.out.println(response.body().string());
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public String syncHttpPost(String url,Map<String, String> params){

        try {
            OkHttpClient mOkHttpClient = new OkHttpClient();

            FormBody.Builder builder = new FormBody.Builder();
            Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                builder.add(entry.getKey(), entry.getValue());
            }
            FormBody formBody =builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .tag(url)
                    .post(formBody)
                    .build();
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    public String syncHttpget(String url,Map<String, String> params){

        try {
            url = appendParams(url, params);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }else{
                return null;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }


    }

    public void asyncHttp(String url){
        Request request = new Request.Builder()
                .url(url).build();
        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // NOT ui Thread
                if (response.isSuccessful()) {
                    System.out.println(response.body().string());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void asyncHttp(String url,Map<String, String> params){
        url = appendParams(url, params);
        Request request = new Request.Builder()
                .url(url).build();
        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // NOT ui Thread
                if (response.isSuccessful()) {
                    System.out.println(response.body().string());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });

    }

    protected String appendParams(String url, Map<String, String> params)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty())
        {
            for (String key : params.keySet())
            {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}

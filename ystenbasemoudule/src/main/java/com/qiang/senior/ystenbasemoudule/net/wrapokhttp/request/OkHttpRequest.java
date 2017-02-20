package com.qiang.senior.ystenbasemoudule.net.wrapokhttp.request;

import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.callback.Callback;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

//import com.istv.ystframework.thirdParty.okhttp3.Headers;
//import com.istv.ystframework.thirdParty.okhttp3.Request;
//import com.istv.ystframework.thirdParty.okhttp3.RequestBody;
//import com.istv.ystframework.thirdParty.wrapokhttp.callback.Callback;

/**
 * Created by wangqiang on 2016/5/6.
 */
public abstract class OkHttpRequest {
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected Request.Builder mBuilder = new Request.Builder();

    public OkHttpRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;

        initBuilder();
    }

    private void initBuilder() {
        mBuilder.url(url).tag(tag);
        appendHeaders();
    }

    private void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet())
        {
            headerBuilder.add(key, headers.get(key));
        }
        mBuilder.headers(headerBuilder.build());
    }

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback)
    {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build()
    {
        return new RequestCall(this);
    }


    public Request generateRequest(Callback callback)
    {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }


}

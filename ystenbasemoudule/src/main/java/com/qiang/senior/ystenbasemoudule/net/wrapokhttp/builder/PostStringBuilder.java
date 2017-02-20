package com.qiang.senior.ystenbasemoudule.net.wrapokhttp.builder;

import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.request.PostStringRequest;
import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;

//import com.istv.ystframework.thirdParty.okhttp3.MediaType;
//import com.istv.ystframework.thirdParty.wrapokhttp.request.PostStringRequest;
//import com.istv.ystframework.thirdParty.wrapokhttp.request.RequestCall;

/**
 * Created by wangqiang on 2016/5/7.
 */
public class PostStringBuilder extends OkHttpRequestBuilder {
    private String content;
    private MediaType mMediaType;

    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        mMediaType = mediaType;
        return this;
    }

    @Override
    public PostStringBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public PostStringBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public PostStringBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public PostStringBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key,val);
        return this;
    }


    @Override
    public RequestCall build() {
        return new PostStringRequest(url,tag,params,headers,content,mMediaType).build();
    }
}

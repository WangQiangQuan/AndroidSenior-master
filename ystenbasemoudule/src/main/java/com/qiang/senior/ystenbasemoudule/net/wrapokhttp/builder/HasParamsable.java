package com.qiang.senior.ystenbasemoudule.net.wrapokhttp.builder;

import java.util.Map;

/**
 *
 */
public interface HasParamsable
{
    public abstract OkHttpRequestBuilder params(Map<String, String> params);

    public abstract OkHttpRequestBuilder addParams(String key, String val);

}

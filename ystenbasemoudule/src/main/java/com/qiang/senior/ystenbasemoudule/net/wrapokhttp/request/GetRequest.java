package com.qiang.senior.ystenbasemoudule.net.wrapokhttp.request;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

//import com.istv.ystframework.thirdParty.okhttp3.Request;
//import com.istv.ystframework.thirdParty.okhttp3.RequestBody;

/**
 *
 */
public class GetRequest extends OkHttpRequest
{
    public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers)
    {
        super(url, tag, params, headers);
    }


    @Override
    protected RequestBody buildRequestBody()
    {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        return mBuilder.get().build();
    }


}

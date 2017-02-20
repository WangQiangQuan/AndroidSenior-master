package com.qiang.senior.ystenbasemoudule.net.wrapokhttp.request;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

//import com.istv.ystframework.thirdParty.okhttp3.MediaType;
//import com.istv.ystframework.thirdParty.okhttp3.Request;
//import com.istv.ystframework.thirdParty.okhttp3.RequestBody;

/**
 * Created by wangqiang on 2016/5/7.
 */
public class PostStringRequest extends OkHttpRequest{

    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    private String content;
    private MediaType mMediaType;

    public PostStringRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers,String content,MediaType mMediaType) {
        super(url, tag, params, headers);

        this.content = content;
        this.mMediaType = mMediaType;

        if (this.mMediaType == null){
            mMediaType = MEDIA_TYPE_PLAIN;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType,content);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return mBuilder.post(requestBody).build();
    }
}

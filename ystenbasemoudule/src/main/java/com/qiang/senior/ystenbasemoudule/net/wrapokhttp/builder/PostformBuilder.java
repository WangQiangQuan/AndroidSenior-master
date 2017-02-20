package com.qiang.senior.ystenbasemoudule.net.wrapokhttp.builder;


import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.request.PostFormRequest;
import com.qiang.senior.ystenbasemoudule.net.wrapokhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangqiang on 2016/5/7.
 */
public class PostformBuilder extends OkHttpRequestBuilder implements HasParamsable{

    private List<FileInput> files = new ArrayList<>();

    @Override
    public PostformBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostformBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public PostformBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public PostformBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public PostformBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public PostformBuilder addHeader(String key, String val) {

        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }


    @Override
    public RequestCall build() {
        return new PostFormRequest(url,tag,params,headers,files).build();
    }

    public static class FileInput
    {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file)
        {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString()
        {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }
}

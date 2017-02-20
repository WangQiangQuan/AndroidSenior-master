package com.qiang.senior.ystenbasemoudule.json;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by wangQ
 * on 2017/2/10.
 * json解析帮助类
 */

public class FastJsonHelper {

    /**
     * json --> obj
     * @param json
     * @param clzObj
     * @param <T>
     * @return
     */
    public static <T> T parseJson2Obj(String json, Class<T> clzObj) {
        try {
            if (json != null && !"".equals(json.trim())) {
                T res = JSONArray.parseObject(json.trim(), clzObj);
                return res;
            }
        } catch (Exception e) {
            Log.e("数据转换出错", "exception:" + e.getMessage());
        }
        return null;
    }

    /**
     * json --> array
     * @param json
     * @param clzArray
     * @param <T>
     * @return
     */
    public  static <T> List<T> parseJson2Array(String json , Class<T> clzArray){
        try {
            if (json != null && !"".equals(json.trim())) {
                List<T> res = JSONArray.parseArray(json.trim(), clzArray);
                return res;
            }
        } catch (Exception e) {
            Log.e("数据转换出错", "exception:" + e.getMessage());
        }
        return null;
    }

    /**
     * 把类对象转化成json string
     *
     * @param t
     * @return
     */
    public static <T> String obj2Json(T t) {
        try {
            return JSONObject.toJSONString(t);
        } catch (Exception e) {
            Log.e("数据转换出错", "exception:" + e.getMessage());
        }
        return "";
    }
}

package com.qiang.senior.ystenbasemoudule.net.wrapokhttp.callback;

import java.io.IOException;

import okhttp3.Response;

//import com.istv.ystframework.thirdParty.okhttp3.Response;
//import com.tandong.sa.sql.util.Log;

/**
 *
 */
public abstract class StringCallback extends Callback<String>
{
	@Override
	public String parseNetworkResponse(Response response) throws IOException
	{
//		Log.i("Ytag", "StringCallback:response.isSuccessful():"+response.isSuccessful());
		if (response.isSuccessful()){
			return response.body().string();
		}
		return "";
	}

}

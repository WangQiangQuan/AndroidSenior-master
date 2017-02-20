package com.qiang.senior.ystenbasemoudule.net.wrapokhttp.callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

//import com.istv.ystframework.thirdParty.okhttp3.Response;
//import com.istv.ystframework.thirdParty.wrapokhttp.util.OkHttpUtils;

public abstract class FileCallBack extends Callback<File>{
	/**
	 * 目标文件存储的文件夹路径
	 */
	private String destFileDir;
	/**
	 * 目标文件存储的文件名
	 */
	private String destFileName;

	public FileCallBack(String destFileDir, String destFileName)
	{
		this.destFileDir = destFileDir;
		this.destFileName = destFileName;
	}
	@Override
	public File parseNetworkResponse(Response response) throws Exception
	{
		if (response.isSuccessful()){
			return saveFile(response);
		}
		return null;
	}
	public File saveFile(Response response) throws IOException
	{


		InputStream is = null;
		byte[] buf = new byte[2048];
		int len = 0;
		FileOutputStream fos = null;
		try
		{
			is = response.body().byteStream();
			final long total = response.body().contentLength();

			long sum = 0;

			File dir = new File(destFileDir);
			if (!dir.exists())
			{
				dir.mkdirs();
			}
			File file = new File(dir, destFileName);
			fos = new FileOutputStream(file);
			while ((len = is.read(buf)) != -1)
			{
				sum += len;
				fos.write(buf, 0, len);
				final long finalSum = sum;
				//                OkHttpUtils.getInstance().getDelivery().execute(new Runnable()
				//                {
					//                    @Override
					//                    public void run()
					//                    {
						//
						//                        inProgress(finalSum * 1.0f / total,total);
				//                    }
				//                });
			}
			fos.flush();

			return file;

		} finally
		{
			try
			{
				response.body().close();
				if (is != null) is.close();
			} catch (IOException e)
			{
			}
			try
			{
				if (fos != null) fos.close();
			} catch (IOException e)
			{
			}

		}
	}

}
/**
 * 
 *    OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test", "fndwjzdy.apk")//
                {

                    @Override
                    public void onBefore(Request request, int id)
                    {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id)
                    {
                        mProgressBar.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id)
                    {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                        Log.e(TAG, "onResponse :" + file.exists());
                        Log.e(TAG, "onResponse :" + file.length());
                    }
                });
 */

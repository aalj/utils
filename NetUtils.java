package com.aalj22.aa21.NetUtils;

import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.util.Log;

import com.aalj22.aa21.NEWS.News;
import com.aalj22.aa21.xml.XmlPull;

public class NetUtils {

	private static final String TAG = "netUtils";

	/**
	 * 使用GET方法连接服务器
	 * 
	 * @return text 返回连接服务器得到的数据
	 */
	public static List<News> doGET() {
		HttpClient client = null;

		try {
			client = new DefaultHttpClient();
			HttpGet get = new HttpGet(
					"http://192.168.0.15:8080/NetEaseServer/new.xml");

			HttpResponse response = client.execute(get);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				InputStream stream = response.getEntity().getContent();
				 List<News> xmlPull = XmlPull.getXmlPull(stream);
				 
				return xmlPull;

			} else {

				Log.i(TAG, "服务器连接失败：" + statusCode);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}
		return null;
	}

//	private static String getStringFromInputStream(InputStream stream)
//			throws Exception {
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		byte[] buffer = new byte[1024];
//		int len = -1;
//		if ((len = stream.read()) != -1) {
//			output.write(buffer);
//		}
//		stream.close();
//		String text = new String(output.toByteArray(), "utf-8");
//
//		return text;
//	}
	
	
	public Bitmap getImage(){
		
		
		
		
		
		
		
		
		return null;
		
	}
	
	

}

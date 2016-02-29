package com.snail.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 
 * ClassName:NetUtils<br/>
 * 
 * Function: 通过最基础的的方式实现get与post请求 同时在客户端实现解决乱码提交<br/>
 * 
 * Reason: TODO ADD REASON<br/>
 *
 * @author Stone
 * @version
 * @since Ver 1.1
 * @Date 2016 2016年2月27日 下午1:59:31
 *
 * @see
 */
public class NetUtils {
	/*
	 * get 查询数据：提交给服务器参数接到统一资源url后面 post 提交数据 请求参数放在请求体里面
	 */

	public static void getConnectionByGet(final URL url, final Handler handler) {

		new Thread(new Runnable() {
			public void run() {

				try {
					Log.i("TAG", "lianjie  ");
					Log.i("TAG", url.toString());
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// 连接超时时间
					conn.setConnectTimeout(10000);
					// 读取数据时间
					conn.setReadTimeout(5000);
					conn.setRequestMethod("GET");
					if (conn.getResponseCode() == 200) {

						Log.i("TAG", "youlianjie  ");
						InputStream inputStream = conn.getInputStream();
						byte[] buffer = new byte[1024];
						// StringBuder
						String string = converStreamToString(inputStream);

						// 将resilt传给主线程

						Message message = new Message();
						// 使用消息池
						Message msg = Message.obtain();
						msg.what = 1;
						msg.obj = string;
						handler.sendMessage(msg);

					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private static String converStreamToString(InputStream in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// 创建字节数组

		int b;
		try {
			while ((b = in.read()) != -1) {
				baos.write(b);

			}
			return baos.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * postConnetction:(通过post方法的连接网络)<br/>
	 *
	 * 
	 * @param @param
	 *            url
	 * @param @param
	 *            handler
	 * @param @param
	 *            map 设定文件
	 * @return void DOM对象
	 * @throws @since
	 *             CodingExample Ver 1.1
	 */
	public static void postConnetction(final URL url, final Handler handler, final Map<String, String> map) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("POST");
					StringBuffer buf= new StringBuffer();
					for (Map.Entry<String , String> entry : map.entrySet()) {
						buf.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),"utf-8"))
						.append("&");
					}
					
					//通过URLEncoder把地址转化成带百分号的字符形式 通过UTF-8的形式 可以避免提交的中文乱码问题
					
					buf.deleteCharAt(buf.length()-1);
					Log.i("TAG", buf.toString());
					byte[] bytes = buf.toString().getBytes();
					OutputStream outputStream = conn.getOutputStream();
					outputStream.write(bytes);
					outputStream.flush();
					outputStream.close();
					
					if(conn.getResponseCode()==200){
						Log.i("TAG", "连接成功");
						InputStream in = conn.getInputStream();
						String converStreamToString = converStreamToString(in);
						Message msg= Message.obtain();
						msg.what=1;
						msg.obj=converStreamToString;
						handler.sendMessage(msg);
					}
					
					
					
					
					
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

}

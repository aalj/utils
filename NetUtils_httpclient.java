package com.aalj21.aa51.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class NetUtils2 {

	private static final String TAG = "NetUtils";

	/**
	 * 
	 * 使用get的方式
	 * 
	 * @param username
	 * @param password
	 * @return 登陆的状态
	 * @throws UnsupportedEncodingException
	 */
	public static String getLogin(String username, String password) {
		String data = "username" + username + "&passwoed" + password;
		// http://172.16.33.198:8080/Serveraalj21/servlet/LoginServlet?
		HttpClient client = null;

		try {
			// 定义一个客户端
			client = new DefaultHttpClient();

			// 定义一个get方法
			HttpGet get = new HttpGet(
					"http://172.16.33.198:8080/Serveraalj21/servlet/LoginServlet?"
							+ data);
			// response服务器响应对象，其中包含了状态信息和服务返回的数据
			HttpResponse response = client.execute(get);// 开始执行get方法，请求网络

			// 通过状态行，取得状态码
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {

				InputStream input = response.getEntity().getContent();

				String text = getStringFromInputStream(input);

				return text;
			} else {
				Log.i(TAG, "失败" + statusCode);
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();// 关闭连接并且释放资源

			}
		}

		return null;

	}

	/**
	 * 使用post方式提交
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String postLogin(String username, String password) {

		/*
		 * 定义一个客户端 定义post方法 使用客户端执行post方法 使用相应对象，获取状态码
		 */
		String data = "username=" + username + "&password=" + password;
		// http://172.16.33.198:8080/Serveraalj21/servlet/LoginServlet?
		HttpClient client = null;

		try {
			client = new DefaultHttpClient();
			//定义post方法
			HttpPost post = new HttpPost(
					"http://172.16.33.198:8080/Serveraalj21/servlet/LoginServlet?"
							+ data);
			//定义post请求参数
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("username", username));
			parameters.add(new BasicNameValuePair("password", password));
			//把post请求的参数包装一层,不写编码名称服务器收到的是乱码，需要指定字符集为utf-8
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,"utf-8");
			//设置参数
			post.setEntity(entity);
			//设置请求头消息
//			post.addHeader("", data);
			
			//使用客户端执行post方法
			HttpResponse response = client.execute(post);
			//使用相应对象，获得状态码，处理内容
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				//使用相应对象获取实体，获得输入流
				InputStream input = response.getEntity().getContent();
				String text = getStringFromInputStream(input);
				return text;

			} else {
				Log.i(TAG, "失败" + statusCode);
				return null;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}

		return null;

	}

	public static String getStringFromInputStream(InputStream is)
			throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] by = new byte[1024];
		int len = -1;
		if ((len = is.read(by)) != -1) {

			output.write(by);

		}

		is.close();
		// String html = output.toString();
		String html = new String(output.toByteArray(), "gbk");

		output.close();

		return html;

	}

}

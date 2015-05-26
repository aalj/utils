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
	 * ʹ��get�ķ�ʽ
	 * 
	 * @param username
	 * @param password
	 * @return ��½��״̬
	 * @throws UnsupportedEncodingException
	 */
	public static String getLogin(String username, String password) {
		String data = "username" + username + "&passwoed" + password;
		// http://172.16.33.198:8080/Serveraalj21/servlet/LoginServlet?
		HttpClient client = null;

		try {
			// ����һ���ͻ���
			client = new DefaultHttpClient();

			// ����һ��get����
			HttpGet get = new HttpGet(
					"http://172.16.33.198:8080/Serveraalj21/servlet/LoginServlet?"
							+ data);
			// response��������Ӧ�������а�����״̬��Ϣ�ͷ��񷵻ص�����
			HttpResponse response = client.execute(get);// ��ʼִ��get��������������

			// ͨ��״̬�У�ȡ��״̬��
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {

				InputStream input = response.getEntity().getContent();

				String text = getStringFromInputStream(input);

				return text;
			} else {
				Log.i(TAG, "ʧ��" + statusCode);
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();// �ر����Ӳ����ͷ���Դ

			}
		}

		return null;

	}

	/**
	 * ʹ��post��ʽ�ύ
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String postLogin(String username, String password) {

		/*
		 * ����һ���ͻ��� ����post���� ʹ�ÿͻ���ִ��post���� ʹ����Ӧ���󣬻�ȡ״̬��
		 */
		String data = "username=" + username + "&password=" + password;
		// http://172.16.33.198:8080/Serveraalj21/servlet/LoginServlet?
		HttpClient client = null;

		try {
			client = new DefaultHttpClient();
			//����post����
			HttpPost post = new HttpPost(
					"http://172.16.33.198:8080/Serveraalj21/servlet/LoginServlet?"
							+ data);
			//����post�������
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("username", username));
			parameters.add(new BasicNameValuePair("password", password));
			//��post����Ĳ�����װһ��,��д�������Ʒ������յ��������룬��Ҫָ���ַ���Ϊutf-8
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,"utf-8");
			//���ò���
			post.setEntity(entity);
			//��������ͷ��Ϣ
//			post.addHeader("", data);
			
			//ʹ�ÿͻ���ִ��post����
			HttpResponse response = client.execute(post);
			//ʹ����Ӧ���󣬻��״̬�룬��������
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				//ʹ����Ӧ�����ȡʵ�壬���������
				InputStream input = response.getEntity().getContent();
				String text = getStringFromInputStream(input);
				return text;

			} else {
				Log.i(TAG, "ʧ��" + statusCode);
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

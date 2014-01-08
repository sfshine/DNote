package com.ndialog.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.util.Log;

public final class HttpGetPost {

	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param queryString
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 */
	public static String doGet(String url, String type, String date, String username) {
		String strResult = null;
		try {// 转码
			date = URLEncoder.encode(date, "UTF-8");
			type = URLEncoder.encode(type, "UTF-8");
			username = URLEncoder.encode(username, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String uriAPI = url + "?type=" + type + "&date=" + date + "&username=" + username;

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uriAPI);
		HttpResponse httpResponse;
		try {
			httpResponse = client.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 第3步：使用getEntity方法获得返回结果
				strResult = EntityUtils.toString(httpResponse.getEntity());
				// 去掉返回结果中的"\r"字符，

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(" 000", strResult);
		return strResult;
	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static String doPost(String url, Map<String, String> params)
			throws IllegalStateException, IOException {
		String strResult = "";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			postData.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
			//Log.i("为什么不执行?",""+entry.getValue());
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData,
				HTTP.UTF_8);// 过时了?
		post.setEntity(entity);
		HttpResponse response = httpClient.execute(post);

		// 若状态码为200 ok
		if (response.getStatusLine().getStatusCode() == 200) {
			// 取出回应字串
			byte[] bResultXml = EntityUtils.toByteArray(response.getEntity());
			strResult = new String(bResultXml, "GB2312");
		}
		return strResult;
	}

	public static void getImg(String urlPath, String savePath) throws Exception {// 获取图片
		String str =java.net.URLEncoder.encode(urlPath,"UTF-8");
		 str = str.replaceAll("%2F","/");//需要把网址的特殊字符转过来
		 str = str.replaceAll("%3A",":");
		// str = str.replaceAll("\+","%20");
		Log.i("还是乱码??", str);
		URL url = new URL(str);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(6 * 1000); // 注意要设置超时，设置时间不要超过10秒，避免被android系统回收
		if (conn.getResponseCode() != 200)
			throw new RuntimeException("请求url失败");
		InputStream inSream = conn.getInputStream();
		File file = new File(savePath);
//		if (!file.exists()) {
//			file.createNewFile();
//		}
		readAsFile(inSream, file);

	}

	public static void readAsFile(InputStream inSream, File file)
			throws Exception {// is 到 file转换
		FileOutputStream outStream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inSream.close();
	}

	public static void getImgAll(List<String> httpList, final String savePath)
			throws Exception {
		int i = 0;
		for (final String http : httpList) {
			Log.i("LLLLLL", http + savePath);
			if (http.contains("gif")) {
				getImg(http, savePath + i + ".gif");
			}
			else {
				getImg(http, savePath + i + ".jpg");
			}
			
			i++;
		}
	}

	public static List<String> parse(String url, String name, String type, String username) {
		String get1 = HttpGetPost.doGet(url, name, type ,username).trim();
		List<String> httpList = new ArrayList<String>();
		int start = get1.lastIndexOf("http");
		while (start >= 0) {
			String temp = get1.substring(start, get1.length());
			httpList.add(temp);
			get1 = get1.substring(0, start);
			start = get1.lastIndexOf("http");
		}
		return httpList;

	}

	public static void main(String[] args) throws IllegalStateException,
			IOException {
		HashMap<String, String> user = new HashMap<String, String>();
		user.put("username", "admin");
		user.put("password", "123");
		String post = doPost("http://127.0.0.1/html4/login_mobile.php", user);
		//String get = doGet("http://127.0.0.1/html4/login_mobile.php", "name",
		//		"admin");
		//String get1 = doGet("http://127.0.0.1/html4/project_desc_mobile.php",
		//		"type", "traffic_route");
		System.out.println("Post:" + post);
		//System.out.println("Get:" + get1);
	}
}
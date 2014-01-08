package com.ndialog.wheather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ndialog.util.HttpGetPost;


import android.util.Log;

public class ChinaWeather {
	private String weather;

	public String getWheather(String str) {
		try {
			String url = "http://m.weather.com.cn/data/101120202.html";
			String result = HttpGetPost.doGet(url, "", "", "");
			Log.e("Result", result);

			JSONObject jsonObject = new JSONObject(
					new JSONObject(result).getString("weatherinfo"));
			weather = jsonObject.getString("date_y")
					+ jsonObject.getString("week") + "\n" + "温度"
					+ jsonObject.getString("temp1") + "\n" + "天气"
					+ jsonObject.getString("weather2") + "\n" + "提醒:"
					+ jsonObject.getString("index_d");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return weather;

	}
}
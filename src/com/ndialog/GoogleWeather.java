package com.ndialog;

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
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class GoogleWeather {

	public String getWeatherData(Integer latitude, Integer longtitude)
			throws ClientProtocolException, IOException,
			ParserConfigurationException, FactoryConfigurationError,
			SAXException {
		String wheatherResult = "";
		String url = "http://www.google.com/ig/api?hl=zh_cn&weather=,,,"
				+ latitude + "," + longtitude;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpUriRequest Request = new HttpGet(url);
		HttpResponse Response = client.execute(Request);
		HttpEntity Entity = Response.getEntity();
		InputStream stream = Entity.getContent();
		InputStreamReader in = new InputStreamReader(stream, "GBK");

		DocumentBuilder Builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = Builder.parse(new InputSource(in));
		NodeList nforecast_conditions = doc
				.getElementsByTagName("forecast_conditions");
		for (int i = 0; i < nforecast_conditions.getLength(); i++)// 遍列current_condition所有节点
		{
			// 获取节点的天气数据
			String week = nforecast_conditions.item(i).getChildNodes().item(0)
					.getAttributes().item(0).getNodeValue();
			String low = nforecast_conditions.item(i).getChildNodes().item(1)
					.getAttributes().item(0).getNodeValue();
			String high = nforecast_conditions.item(i).getChildNodes().item(2)
					.getAttributes().item(0).getNodeValue();
			String condition = nforecast_conditions.item(i).getChildNodes()
					.item(4).getAttributes().item(0).getNodeValue();
			wheatherResult = wheatherResult + week + "天气:" + condition
					+ "\n最低:" + low + "  最高:" + high +"\n";
		}
		Log.d("log", "sTemp=" + wheatherResult);
		return wheatherResult.trim();
	}
}
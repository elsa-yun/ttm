package com.elsa.ttm.test;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class T2 {

	public static String doGet(String url, String charset) throws Exception {
		/*
		 * 使用 GetMethod 来访问一个 URL 对应的网页,实现步骤: 1:生成一个 HttpClinet 对象并设置相应的参数。
		 * 2:生成一个 GetMethod 对象并设置响应的参数。 3:用 HttpClinet 生成的对象来执行 GetMethod 生成的Get
		 * 方法。 4:处理响应状态码。 5:若响应正常，处理 HTTP 响应内容。 6:释放连接。
		 */
		/* 1 生成 HttpClinet 对象并设置参数 */
		HttpClient httpClient = new HttpClient();
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		/* 2 生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = new GetMethod(url);
		NameValuePair nv = new NameValuePair();
		nv.setName("s");
		nv.setValue(T.SELECT);
		NameValuePair nv2 = new NameValuePair();
		nv2.setName("d");
		nv2.setValue("uc_user");
		NameValuePair[] nva = new NameValuePair[2];
		nva[0] = nv;
		nva[0] = nv2;
//		getMethod.setQueryString(nva);
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		String response = "";
		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 判断访问的状态码 */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("请求出错: " + getMethod.getStatusLine());
			}
			/* 5 处理 HTTP 响应内容 */
			// HTTP响应头部信息，这里简单打印
			Header[] headers = getMethod.getResponseHeaders();
			for (Header h : headers)
				System.out.println(h.getName() + "------------ " + h.getValue());
			// 读取 HTTP 响应内容，这里简单打印网页内容
			byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
			response = new String(responseBody, charset);
			System.out.println("----------response:" + response);
			// 读取为 InputStream，在网页内容数据量大时候推荐使用
			// InputStream response = getMethod.getResponseBodyAsStream();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("请检查输入的URL!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			System.out.println("发生网络异常!");
			e.printStackTrace();
		} finally {
			/* 6 .释放连接 */
			getMethod.releaseConnection();
		}
		return response;
	}

	public static void main(String args[]) throws Exception {
		/*
		 * HttpClientUtil client = new HttpClientUtil(); String htmlContent =
		 * client.getHtmlContent("http://www.baidu.com", 5000); for (int i = 0;
		 * i < 20; i++) { FileUtil.writeFile("D:/data/1.java", htmlContent); }
		 */

		// ExecutorService ex = Executors.newFixedThreadPool(10);
		// String s = "str#f#";
		// ThreadOne t1 = new ThreadOne(0, 1, 100, s);
		// ThreadOne t2 = new ThreadOne(0, 2, 100, s);
		// ThreadOne t3 = new ThreadOne(0, 3, 100, s);
		// ThreadOne t4 = new ThreadOne(0, 4, 100, s);
		// ThreadOne t5 = new ThreadOne(0, 5, 100, s);
		// ThreadOne t6 = new ThreadOne(0, 6, 100, s);
		// ThreadOne t7 = new ThreadOne(0, 7, 100, s);
		// ThreadOne t8 = new ThreadOne(0, 8, 100, s);
		// ex.execute(t1);
		// ex.execute(t2);
		// ex.execute(t3);
		// ex.execute(t4);
		// ex.execute(t5);
		// ex.execute(t6);
		// ex.execute(t7);
		// ex.execute(t8);
		// ex.shutdown();
		String str=URLEncoder.encode(T.ss, "utf-8");
		String doG = doGet(str, "utf-8");
		System.out.println(doG);
		// HttpClientUtil client = new HttpClientUtil();
		// String ss = "";
		// try {
		// ss = URLEncoder.encode(T.ss, "utf-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// // String htmlContent = client.getHtmlContent(ss, 5000);
		// System.out.println(ss);
	}

	public static void request(String d, String s) {

	}

}

class ThreadOne implements Runnable {

	private int f = 0;

	private int max = 100;

	private int one = 50;

	private int l;

	private String s;

	HttpClientUtil client = new HttpClientUtil();

	public ThreadOne(int f, int l, int one, String s) {
		super();
		this.f = f;
		this.l = l;
		this.max = l * max;
		this.s = s;
		this.one = one;
	}

	@Override
	public void run() {
		int size = max / this.one;
		for (int i = 0; i < size; i++) {
			int mod = size % 10;
			String fname = T.P + this.l + "_" + mod + ".java";
			f = i * this.one;
			String str = T.SELECT.replace("#f#", this.f + "");

			String uri = T.S + str;
			uri = uri.replaceAll(" ", "%20");
			String htmlContent = client.getHtmlContent(uri, 5000);
			FileUtil.writeFile(fname, htmlContent);
			System.out.println(uri);
			System.out.println(str);
		}
	}

}
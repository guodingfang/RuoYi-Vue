package com.jlkj.common.utils.http;


import com.jlkj.common.utils.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;


/**
    * @Author liujie  HTTP请求对象

   **/
public class HttpRequester {

    private String defaultContentEncoding;

    public HttpRequester() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }

    /**
     * 发送GET请求
     *
     * @param urlString
     *            URL地址
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendGet(String urlString) throws IOException {
        return this.send(urlString, "GET", null, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString
     *            URL地址
     * @param params
     *            参数集合
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendGet(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "GET", params, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString
     *            URL地址
     * @param params
     *            参数集合
     * @param propertys
     *            请求属性
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendGet(String urlString, Map<String, String> params, Map<String, String> propertys)
            throws IOException {
        return this.send(urlString, "GET", params, propertys);
    }

    /**
     * 发送POST请求
     *
     * @param urlString
     *            URL地址
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendPost(String urlString) throws IOException {
        return this.send(urlString, "POST", null, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString
     *            URL地址
     * @param params
     *            参数集合
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendPost(String urlString, Map<String, String> params) throws IOException {
        return this.send(urlString, "POST", params, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString
     *            URL地址
     * @param params
     *            参数集合
     * @param propertys
     *            请求属性
     * @return 响应对象
     * @throws IOException
     */
    public HttpRespons sendPost(String urlString, Map<String, String> params, Map<String, String> propertys)
            throws IOException {
        return this.send(urlString, "POST", params, propertys);
    }


    public HttpRespons sendDelete(String urlString, Map<String, String> params)
            throws IOException {
        return this.send(urlString, "DELETE", params, null);
    }

    public HttpRespons sendDelete(String urlString, Map<String, String> params,Map<String, String> propertys)
            throws IOException {
        return this.send(urlString, "DELETE", params, propertys);
    }


    public HttpRespons sendPut(String urlString, Map<String, String> params)
            throws IOException {
        return this.send(urlString, "PUT", params, null);
    }

    /**
     * 发送HTTP请求
     *
     * @param urlString
     * @return 响映对象
     * @throws IOException
     */
    private HttpRespons send(String urlString, String method, Map<String, String> parameters,
            Map<String, String> propertys) throws IOException {
        HttpURLConnection urlConnection = null;
        String getStr = "GET";
        String deleteStr = "DELETE";
        String putStr = "PUT";
        if ((method.equalsIgnoreCase(getStr) || method.equalsIgnoreCase(deleteStr) || method.equalsIgnoreCase(putStr)) && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(parameters.get(key));
                i++;
            }
            urlString += param;
        }
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);

        if (propertys != null) {
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, propertys.get(key));
            }
        }
        String postStr = "POST";
        if (method.equalsIgnoreCase(postStr) && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");

            out.write(param.toString());
            out.flush();
            out.close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    /**
     * 得到响应对象
     *
     * @param urlConnection
     * @return 响应对象
     * @throws IOException
     */
    private HttpRespons makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
        HttpRespons httpResponser = new HttpRespons();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            httpResponser.contentCollection = new Vector<String>();
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                httpResponser.contentCollection.add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String ecod = urlConnection.getContentEncoding();
            if (ecod == null) {
                ecod = this.defaultContentEncoding;
            }

            httpResponser.urlString = urlString;

            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
            httpResponser.file = urlConnection.getURL().getFile();
            httpResponser.host = urlConnection.getURL().getHost();
            httpResponser.path = urlConnection.getURL().getPath();
            httpResponser.port = urlConnection.getURL().getPort();
            httpResponser.protocol = urlConnection.getURL().getProtocol();
            httpResponser.query = urlConnection.getURL().getQuery();
            httpResponser.ref = urlConnection.getURL().getRef();
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();

            httpResponser.content = new String(temp.toString().getBytes(), ecod);
            httpResponser.contentEncoding = ecod;
            httpResponser.code = urlConnection.getResponseCode();
            httpResponser.message = urlConnection.getResponseMessage();
            httpResponser.contentType = urlConnection.getContentType();
            httpResponser.method = urlConnection.getRequestMethod();
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
            httpResponser.readTimeout = urlConnection.getReadTimeout();

            return httpResponser;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     *            文件下载地址
     * @param savePath
     *            下载文件保存路径
     * @param fileName
     *            文件名称
     */
    public static void downLoadFromUrl(String urlStr, String savePath, String fileName) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        // 获取自己数组
        byte[] getData = readInputStream(inputStream);

        // 文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 默认的响应字符集
     */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    /**
     * 设置默认的响应字符集
     */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }


    public HttpRespons sendPostJson(String urlString, String method, String jsonContent,
            Map<String, String> propertys) throws IOException {
    	String postStr = "POST";
    	if(!postStr.equalsIgnoreCase(method)){
    		return new HttpRespons();
    	}
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(postStr);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);

        if (propertys != null) {
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, propertys.get(key));
            }
        }

        if (StringUtils.isNotEmpty(jsonContent)) {
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");

            out.write(jsonContent);
            out.flush();
            out.close();
        }

        return this.makeContent(urlString, urlConnection);
    }
}

package com.basicfu.system.util

import org.apache.http.Consts
import org.apache.http.HttpHost
import org.apache.http.NameValuePair
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


/**
 * @author fuliang
 * *
 * @date 4/21/2017
 */
object HttpUtil {
    /**
     * 封装一个带随机ua的请求头
     */
    fun ua(url: String): String? {
        val headerMap = HashMap<String, String>()
        headerMap.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
        headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
        return get(url, headerMap)
    }

    /**针对某url获取302地址*/
    fun getRealUrl(url: String): String? {
        val httpClient = HttpClients.createDefault()
        val nurl = url.replace(" ", "%20")
        val httpGet = HttpGet(nurl)
        val requestConfig = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(10000).setSocketTimeout(60000).setRedirectsEnabled(false).build()
        httpGet.config = requestConfig
        val response: CloseableHttpResponse
        try {
            response = httpClient.execute(httpGet)
            if (response.statusLine.statusCode == 302) {
                val headers = response.getHeaders("Location")
                if (headers.isNotEmpty()) {
                    return headers[0].value
                }
            }
            return null
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun get(url: String): String? {
        val httpClient = HttpClients.createDefault()
        val nurl = url.replace(" ", "%20")
        val httpGet = HttpGet(nurl)
        val requestConfig = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(10000).setSocketTimeout(60000).build()
        httpGet.config = requestConfig
        val response: CloseableHttpResponse
        try {
            response = httpClient.execute(httpGet)
            val entity = response.entity
            return EntityUtils.toString(entity)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun get(url: String, header: Map<String, String>): String? {
        val httpClient = HttpClients.createDefault()
        val httpGet = HttpGet(url)
        val requestConfig = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(10000).setSocketTimeout(60000).build()
        httpGet.config = requestConfig
        header.keys.forEach { e -> httpGet.setHeader(e, header[e]) }
        val response: CloseableHttpResponse
        try {
            response = httpClient.execute(httpGet)
            val entity = response.entity
            return EntityUtils.toString(entity)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun post(url: String, postData: Map<String, String>): String? {
        var response: CloseableHttpResponse? = null
        val httpClient = HttpClients.createDefault()
        val httpPost = HttpPost(url)
        try {
            val params = ArrayList<NameValuePair>()
            postData.keys.forEach { e -> params.add(BasicNameValuePair(e, postData[e])) }
            val entity = UrlEncodedFormEntity(params, "UTF-8")
            httpPost.entity = entity
            response = httpClient!!.execute(httpPost)
            return EntityUtils.toString(response!!.entity)
        } catch (e: IOException) {
        } finally {
            try {
                httpClient?.close()
                if (response != null) {
                    response.close()
                }
            } catch (e: IOException) {
            }

        }
        return null
    }

    fun postForm(url: String,header:Map<String, String>, data: Map<String, String>): String? {
        var response: CloseableHttpResponse? = null
        val httpClient = HttpClients.createDefault()
        val httpPost = HttpPost(url)
        try {
            header.keys.forEach { e -> httpPost.setHeader(e, header[e]) }
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded")
            val params = ArrayList<NameValuePair>()
            data.keys.forEach { e -> params.add(BasicNameValuePair(e, data[e])) }
            val entity = UrlEncodedFormEntity(params, "UTF-8")
            httpPost.entity = entity
            response = httpClient!!.execute(httpPost)
            return EntityUtils.toString(response!!.entity)
        } catch (e: IOException) {
        } finally {
            httpClient.close()
            response?.close()
        }
        return null
    }

    fun post(url: String, header: Map<String, String>, postData: Map<String, String>): String? {
        var response: CloseableHttpResponse? = null
        val httpClient = HttpClients.createDefault()
        val httpPost = HttpPost(url)
        try {
            header.keys.forEach { e -> httpPost.setHeader(e, header[e]) }
            val params = ArrayList<NameValuePair>()
            postData.keys.forEach { e -> params.add(BasicNameValuePair(e, postData[e])) }
            val entity = UrlEncodedFormEntity(params, "UTF-8")
            httpPost.entity = entity
            response = httpClient!!.execute(httpPost)
            return EntityUtils.toString(response!!.entity)
        } catch (e: IOException) {
        } finally {
            try {
                httpClient?.close()
                if (response != null) {
                    response.close()
                }
            } catch (e: IOException) {
            }

        }
        return null
    }

    fun post(url: String, postJson: Any): String? {
        var response: CloseableHttpResponse? = null
        val httpClient = HttpClients.createDefault()
        val httpPost = HttpPost(url)
        try {
            val entity = StringEntity(postJson.toString(), Charset.forName("UTF-8"))
            entity.setContentType("application/json")
            httpPost.entity = entity
            response = httpClient!!.execute(httpPost)
            return EntityUtils.toString(response!!.entity)
        } catch (e: IOException) {
        } finally {
            httpClient.close()
            response?.close()
        }
        return null
    }

    fun postFile(url: String, header: Map<String, String>, postData: Map<String, Any>): String? {
        var response: CloseableHttpResponse? = null
        val httpClient = HttpClients.createDefault()
        val httpPost = HttpPost(url)
        try {
            header.keys.forEach { e -> httpPost.setHeader(e, header[e]) }
            val meb = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
            for (key in postData.keys) {
                val obj = postData[key]
                if (obj is File) {
                    meb.addPart(key, FileBody(obj, ContentType.create("image/jpeg", Consts.UTF_8)))
                } else if (obj is String) {
                    meb.addPart(key, StringBody(obj.toString(), ContentType.create("text/plain", Consts.UTF_8)))
                }
            }
            httpPost.entity = meb.build()
            response = httpClient!!.execute(httpPost)
            return EntityUtils.toString(response!!.entity)
        } catch (e: IOException) {
        } finally {
            try {
                httpClient?.close()
                if (response != null) {
                    response.close()
                }
            } catch (e: IOException) {
            }

        }
        return null
    }

}
//val build = HttpClients.custom()
//val proxy = HttpHost("127.0.0.1", 8812)
//val httpClient = build.setProxy(proxy).build()
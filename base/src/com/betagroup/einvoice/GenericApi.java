package com.betagroup.einvoice;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.compiere.util.KeyNamePair;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericApi {
    protected String passwd;
	protected String userName;


	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public void setUsername(String userName) {
		this.userName = userName;
	}


	public String invokeGetApi(String url) throws Exception {
//        HttpTransport httpTransport = new NetHttpTransport();
//		HttpRequest request = httpTransport.createRequestFactory().buildGetRequest(new GenericUrl(url, false));
//		HttpResponse response = request.execute();
//		String resp = null;
//		int statusCode = response.getStatusCode();
//		if (statusCode == 200) {
//		    resp = response.parseAsString();
//		} else {
//		    // Handle error response
//		}
//		log.warning("GET API Request: " + url + " Response:" + resp);
//		return resp;
    	
        // Create an instance of CloseableHttpClient
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        // Example of a GET request

    	HttpGet getRequest = new HttpGet(url);
    	getRequest.setHeader("Content-Type", "application/json");
        		

    	HttpResponse getResponse = httpClient.execute(getRequest);
    	String response = EntityUtils.toString(getResponse.getEntity());

        return response;
    }


	public KeyNamePair invokePostApi(String url, Object data) throws Exception {
//		GsonFactory gsonFactory = new GsonFactory();
//		String dataJson = gsonFactory.toString(data);
//		log.warning("POST API : " + url + " Data: " + dataJson) ;
//		HttpContent content = ByteArrayContent.fromString("application/json", dataJson);
//		
//		HttpTransport httpTransport = new NetHttpTransport();
//		HttpRequest request = httpTransport.createRequestFactory().buildPostRequest(new GenericUrl(url, false), content);
//		HttpResponse response = request.execute();
//		int statusCode = response.getStatusCode();
//		String resp = null;
//		if (statusCode == 200) {
//		    resp = response.parseAsString();
//		} else {
//		    // Handle error response
//		}
//		log.warning("POST API : " + url + " Data: " + data + " Response:" + resp);
//		return resp;
		DefaultHttpClient httpClient = new DefaultHttpClient();
        // Example of a POST request

        HttpPost postRequest = new HttpPost(url);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(data);
        StringEntity entity = new StringEntity(jsonData);
        postRequest.setEntity(entity);
        postRequest.setHeader("Content-Type", "application/json");
//        postRequest.setHeader("Accept", "application/json, text/plain");
        postRequest.setHeader("Accept", "*/*");
        postRequest.setHeader(HttpHeaders.AUTHORIZATION, getAuthHeader(userName, passwd));
        postRequest.setHeader("Accept-Version", "V2"); // Required by portal
        postRequest.setHeader("Accept-Language", "en"); // en / ar // default = en

        //   -H "accept-language: en"  -H "Clearance-Status: 1" // TODO what are these?
        postRequest.setHeader("OTP", "123345"); // 123345 = Valid, 111111 = Invalid, 222222 = Expired

        HttpResponse response = httpClient.execute(postRequest);
        System.out.println(response);
        String out = EntityUtils.toString(response.getEntity());
        System.out.println(out);
        return new KeyNamePair(response.getStatusLine().getStatusCode(), out);
	}

	
	public String getAuthHeader(String username, String passwd) throws Exception {

		String auth = username + ":" + passwd;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

		String authHeader = "Basic " + new String(encodedAuth);
		return authHeader;
	}		
}

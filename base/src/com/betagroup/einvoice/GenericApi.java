package com.betagroup.einvoice;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.compiere.util.KeyNamePair;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericApi {
    protected String passwd;
	protected String userName;
	private Map<String, String> extraHeaders;

	public GenericApi() {
		extraHeaders = new HashMap<String, String>();
	}

	public void setAuth(String userName, String passwd) {
		this.userName = userName;
		this.passwd = passwd;
	}

	public void addHeader(String header, String value) {
		extraHeaders.put(header, value);
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
    	extraHeaders.forEach((k,v)->getRequest.addHeader(k, v));
    	getRequest.setHeader("Content-Type", "application/json");
        		

    	HttpResponse getResponse = httpClient.execute(getRequest);
    	String response = EntityUtils.toString(getResponse.getEntity());

        return response;
    }

	public KeyNamePair invokePatchApi(String url, Object data) throws Exception {
		return invokePostOrPatchApi(url, data, true);
	}

	public KeyNamePair invokePostApi(String url, Object data) throws Exception {
		return invokePostOrPatchApi(url, data, false);
	}
	
	private KeyNamePair invokePostOrPatchApi(String url, Object data, boolean isPatch) throws Exception {
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

		// Renewal API requires PATCH request
		HttpEntityEnclosingRequestBase postRequest = isPatch ? new HttpPatch(url) : new HttpPost(url);
    	extraHeaders.forEach((k,v)->postRequest.addHeader(k, v));
		
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

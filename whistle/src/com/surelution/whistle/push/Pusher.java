package com.surelution.whistle.push;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Pusher {

//    private String accessToken;
    private String apiUrl;

    private String requestMethod = "POST";
    
    public String getApiUrl() {
        return apiUrl;
    }
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

//    public void setAccessToken(String accessToken) {
//        this.accessToken = accessToken;
//    }
    public String getRequestMethod() {
        return requestMethod;
    }
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
   
    public String push(String content) throws NetworkException {
    	try{
	        String fullApi = apiUrl + "access_token=" + CredentialHelper.getAccessToken();
	        URL url = new URL(fullApi);
	        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	        
	        conn.setRequestMethod(requestMethod);
	        conn.setRequestProperty("Content-Type","text/plain; charset=utf-8");
	        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
	        conn.setDoOutput(true);
	        if(content != null) {
		        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
		        writer.write(content);
		        writer.flush();
		        writer.close();
	        }
	 
	        int responseCode = conn.getResponseCode(); //TODO how to handle the code?
	 
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(conn.getInputStream(), "utf-8"));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
//	        System.out.println(response);
	        return response.toString();
    	} catch(MalformedURLException e) {
    		System.out.println("api url error");
    		throw new NetworkException();
    	} catch(IOException e) {
    		System.out.println("network error");
    		throw new NetworkException();
    	}
    }
}

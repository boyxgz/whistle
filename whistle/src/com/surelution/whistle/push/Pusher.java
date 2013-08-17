package com.surelution.whistle.push;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Pusher {

    private String accessToken;
    private String apiUrl;

    private String requestMethod = "POST";
    
    public String getApiUrl() {
        return apiUrl;
    }
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getRequestMethod() {
        return requestMethod;
    }
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
   
    public String push(String content) throws Exception {
        String fullApi = apiUrl + "?access_token=" + accessToken;
        URL url = new URL(fullApi);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("Content-Type","text/plain; charset=utf-8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
        conn.setDoOutput(true);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(content);
        writer.flush();
        writer.close();
 
        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + fullApi);
        System.out.println("Post parameters : " + content);
        System.out.println("Response Code : " + responseCode);
 
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
 
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response);
        return response.toString();
    }
}

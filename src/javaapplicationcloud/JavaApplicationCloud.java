/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplicationcloud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import org.json.JSONObject;

/**
 *
 * @author sumit
 */
public class JavaApplicationCloud {

    private final String USER_AGENT = "Mozilla/5.0";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        JavaApplicationCloud javaApplicationCloud = new JavaApplicationCloud();
	javaApplicationCloud.sendGet();
        
    }
    
    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&explaintext=&exsectionformat=plain&titles=Africa";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        String content = response.toString();
        JSONObject jsonobj = new JSONObject(content);
        JSONObject pages = jsonobj.getJSONObject("query").getJSONObject("pages");        
        Iterator<?> keys = pages.keys();
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            if ( pages.get(key) instanceof JSONObject ) {
                String toSpeak = pages.getJSONObject(key).getString("extract");
                System.out.println(toSpeak);
            }
        }
        
    }
    
}

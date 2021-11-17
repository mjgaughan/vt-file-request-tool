package edu.middlebury.foiaspringapi;

//import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api")
public class SolrQueryController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/solr")
    public JSONObject query(@RequestParam(value = "q", defaultValue = "*:*") String query) throws IOException {
        String url0 = "http://localhost:8983/solr/vtstatefiles/select?q=";
        String replaced = query.replace(" ", "+");

        URL url = new URL((url0 + replaced));
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("Accept", "application/json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            responseStrBuilder.append(inputLine);
        }
        JSONObject jsonResponse = new JSONObject(responseStrBuilder.toString());
        // System.out.println(jsonResponse);

        reader.close();
        http.disconnect();

        return jsonResponse;

    }

}

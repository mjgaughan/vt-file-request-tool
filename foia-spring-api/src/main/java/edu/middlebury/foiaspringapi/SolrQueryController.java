package edu.middlebury.foiaspringapi;

//import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api")
public class SolrQueryController {

    @GetMapping("/solr")
    public StringBuffer query(@RequestParam(value = "q", defaultValue = "*:*") String query) throws IOException {
        String url0 = "http://localhost:8983/solr/vtstatefiles/select?query=";

        URL url = new URL((url0 + query));
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("Accept", "application/json");
        // System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            if (inputLine.contains("Contact")) {
                content.append(inputLine);
            } else if (inputLine.contains("Name")) {
                content.append(inputLine);
            }
            // content.append(inputLine);
        }
        // System.out.println(content);

        reader.close();
        http.disconnect();

        return content;

    }

}

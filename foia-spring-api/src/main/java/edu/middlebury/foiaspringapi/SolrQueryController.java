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
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import edu.middlebury.foiaspringapi.FedState;

@RestController
@RequestMapping("/api")
public class SolrQueryController {

    @Autowired
    private FedState transportationDecision;
    @Autowired
    private FedState leDecision;
    @Autowired
    private FedState laborDecision;

    @GetMapping("/solr")
    public String query(@RequestParam(value = "q", defaultValue = "*:*") String query) throws IOException {
        String url0 = "http://localhost:8983/solr/vtstatefiles/select?q=";
        String urlQuery = query.replace(" ", "+");
        URL url = new URL((url0 + urlQuery));
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
        }

        reader.close();
        http.disconnect();

        String contentString = content.toString();
        checkFed(contentString, query);
        return contentString;

    }

    public void checkFed(String solrEntry, String query) {
        Boolean fed = false;
        if (solrEntry.contains("Vermont Labor Relations Board") || solrEntry.contains("Vermont Department of Labor")) {
            fed = FedState.laborDecision(query);
        } else if ((solrEntry.contains("Vermont Department of Transportation") || solrEntry.contains("VTrans"))) {
            fed = FedState.transportationDecision(query);
        } else if ((solrEntry.contains("Department of Public Safety") || solrEntry.contains("Vermont State Police"))) {
            fed = FedState.leDecision(query);
        }
        System.out.println(fed);
    }

}

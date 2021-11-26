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

import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/solr")
    public JSONObject query(@RequestParam(value = "q", defaultValue = "*:*") String query, String stands4User,
            String stands4Token) throws IOException {
        String url0 = "http://localhost:8983/solr/vtstatefiles/select?q=";
        String urlQuery = query.replace(" ", "+");
        URL url = new URL((url0 + urlQuery));
        StringBuffer content = new StringBuffer();
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("Accept", "application/json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {

            if (inputLine.contains("Contact") && !inputLine.contains("Bio")) {
                content.append(inputLine);
            } else if (inputLine.contains("Name") && !inputLine.contains("Bio")) {
                content.append(inputLine);
            }
            responseStrBuilder.append(inputLine);
        }

        reader.close();
        http.disconnect();
        JSONObject jsonResponse = new JSONObject(responseStrBuilder.toString());
        String contentString = content.toString();
        String fedCheck = checkFed(contentString, query, stands4User, stands4Token);
        jsonResponse.put("Federal", fedCheck);
        return jsonResponse;

    }

    public String checkFed(String solrEntry, String query, String stands4User, String stands4Token) throws IOException {
        Boolean fed = false;
        String url = "";
        if (solrEntry.contains("Vermont Labor Relations Board") || solrEntry.contains("Vermont Department of Labor")) {
            FedState labor = new FedState();
            System.out.println("labor");
            fed = labor.laborDecision(query);
            System.out.println(fed);
            if (fed) {
                url += "https://www.dol.gov/general/foia; ";
            }
        }
        if (solrEntry.contains("Vermont Department of Transportation")
                || solrEntry.contains("Vermont Agency of Transportation")
                || solrEntry.contains("Vermont National Guard")
                || solrEntry.contains("State of Vermont Transportaiton Board")) {
            FedState transportation = new FedState();
            System.out.println("transportation");
            fed = transportation.transportationDecision(query, stands4User, stands4Token);
            System.out.println(fed);
            if (fed) {
                url += "https://www.transportation.gov/foia; ";
            }
            if (solrEntry.contains("Vermont National Guard")) {
                url += "; https://www.nationalguard.mil/Resources/FOIA/; ";
            }
        }
        if ((solrEntry.contains("Department of Public Safety") || solrEntry.contains("Vermont State Police")
                || solrEntry.contains("Vermont Department for Children and Families"))) {
            System.out.println("law enforcement");
            FedState le = new FedState();
            fed = le.leDecision(query, stands4User, stands4Token);
            System.out.println(fed);
            if (fed) {
                url += "https://www.cbp.gov/site-policy-notices/foia; https://forms.fbi.gov/fbi-efoia-request-form";
            }
        }
        return url;
    }

}

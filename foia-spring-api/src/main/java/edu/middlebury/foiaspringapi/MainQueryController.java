package edu.middlebury.foiaspringapi;

import org.json.JSONObject;
//import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.ipc.http.HttpSender.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.ProcessBuilder;
import java.lang.Process;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api")
public class MainQueryController {

    @Autowired
    private SempreQueryController sempreController;
    @Autowired
    private SolrQueryController solrController;

    @GetMapping("/main")
    public String mainQuery(@RequestParam(value = "q", defaultValue = "*:*") String query) throws IOException {
        // ArrayList<String> relevantTerms = sempreController.query(query);
        // String nouns = String.join("+", relevantTerms);
        JSONObject results = solrController.query(query);
        System.out.println(results.toString());
        return results.toString();
    }

}
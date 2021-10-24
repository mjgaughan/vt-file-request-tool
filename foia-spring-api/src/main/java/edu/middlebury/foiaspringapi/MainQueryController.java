package edu.middlebury.foiaspringapi;

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
import java.lang.ProcessBuilder;
import java.lang.Process;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api")
public class MainQueryController {
	
	@Autowired
	private SempreQueryController sempreController;
	private SolrQueryController solrController;

    @GetMapping("/main")
    public String solr(@RequestParam(value = "q", defaultValue = "*:*") String query) throws IOException {
        /*
         * need centralized file to rout api calls --- construction, api call goes here,
         * this queries SEMPRE --- with SEMPRE resutlts, query SOLR --- return SOLR
         * results
         */
    	ArrayList<String> relevantTerms = sempreController.query(query);
    	String nouns = String.join(" ", relevantTerms);
    	StringBuffer results = solrController.solr(nouns);
    	return results.toString();

        // String url = "http://localhost:8983/solr/vtstatefiles/select?q=";
        // Document doc = Jsoup.connect(url + query).ignoreContentType(true).get();
        // Element body = doc.select("body").first();
    	
        return (query);

    }

}
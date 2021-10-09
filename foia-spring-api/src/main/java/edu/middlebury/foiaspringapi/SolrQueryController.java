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

import io.micrometer.core.ipc.http.HttpSender.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.lang.ProcessBuilder;
import java.lang.Process;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api")
public class SolrQueryController {

    @GetMapping("/solr")
    public String solr(@RequestParam(value = "q", defaultValue = "*:*") String query) throws IOException {
        /*
         * DEBUG: set up solr using A dataset need to name the index 'vtstatefiles' run
         * on port 8983 on request, ERROR 64104: null pointer exception
         */

        // String command = "curl 'http://localhost:8983/solr/vtstatefiles/select?q='";
        String url = "http://localhost:8983/solr/vtstatefiles/select?q=*:*";
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        Element pre = doc.select("pre").first();
        return pre.text();

    }

}

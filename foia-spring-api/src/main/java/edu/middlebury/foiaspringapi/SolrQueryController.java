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
import org.apache.coyote.Request;
import org.apache.http.HttpResponse;

@RestController
@RequestMapping("/api")
public class SolrQueryController {

    @GetMapping("/solr")
    public String solr(@RequestParam(value = "q", defaultValue = "*:*") String query) throws IOException {
        String url = "http://localhost:8983/solr/vtstatefiles/select?q=";
        Document doc = Jsoup.connect(url + query).ignoreContentType(true).get();
        Element body = doc.select("body").first();

        String command = "curl -X GET http://localhost:8983/solr/vtstatefiles/select?q=" + query;
        // Process process = Runtime.getRuntime().exec(command);
        // System.out.println(process.getInputStream());
        // process.destroy();
        // ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        // processBuilder.directory(new File("/home/"));
        // Process process = processBuilder.start();

        // URL url0 = new URL((url + query));
        // HttpURLConnection http = (HttpURLConnection) url0.openConnection();
        // http.setRequestProperty("Accept", "application/json");
        // System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        // System.out.println(http.getE());
        // http.disconnect();

        Request request = Request.get("http://localhost:8983/solr/vtstatefiles/select?q=a");
        HttpResponse httpResponse = request.execute().returnResponse();
        System.out.println(httpResponse.getStatusLine());
        if (httpResponse.getEntity() != null) {
            String html = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(html);
        }

        return body.text();

    }

}

package edu.middlebury.foiaspringapi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.lang.ProcessBuilder;
import java.lang.Process;

@RestController
@RequestMapping("/api")
public class SolrQueryController {

    @GetMapping("/solr")
    public String solr(@RequestParam(value = "q", defaultValue = "") String query) throws IOException {
        System.out.print("hi");
        String command = "curl 'http://localhost:8983/solr/vtstatefiles/select?q=*:*'";
        Process process = Runtime.getRuntime().exec(command);
        System.out.println(process.getInputStream());
        return (command);
    }

}

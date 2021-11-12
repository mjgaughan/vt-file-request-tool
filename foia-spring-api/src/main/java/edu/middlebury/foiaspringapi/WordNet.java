package edu.middlebury.foiaspringapi;

//import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import java.util.ArrayList;
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
public class WordNet {

    @GetMapping("/wordnet")
    public ArrayList<String> getSynonym(String keyword) throws IOException {
        String url = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
                + keyword + "&i=1&h=100000#c";
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc);
        return new ArrayList<String>();
    }
}

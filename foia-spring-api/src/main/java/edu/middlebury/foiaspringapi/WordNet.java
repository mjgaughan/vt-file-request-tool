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
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import edu.middlebury.foiaspringapi.FedState;

@RestController
@RequestMapping("/api")
public class WordNet {

    @Value("stands4User")
    private String stands4User;

    @Value("stands4Token")
    private String stands4Token;

    @GetMapping("/wordnet")
    public ArrayList<String> getSynonym(String keyword) throws IOException {
        String url = "https://www.stands4.com/services/v2/syno.php?uid=" + stands4User + "&tokenid=" + stands4Token
                + "&word=" + keyword + "&format=xml";
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc);
        return new ArrayList<String>();
    }
}

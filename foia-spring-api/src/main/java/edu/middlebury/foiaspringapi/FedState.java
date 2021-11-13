package edu.middlebury.foiaspringapi;

// import org.apache.http.client.HttpClient;
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
public class FedState {
    private static String apiUid = "1001";
    private static String apiToken = "tk324324";

    // true if fed, false if state; just department of transportations
    public static boolean transportationDecision(String query) throws IOException {
        URL url = new URL(("https://www.stands4.com/services/v2/syno.php?uid=" + apiUid + "&tokenid=" + apiToken
                + "&word=interstate&format=json"));
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("Accept", "application/json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String inputLine;
        String content;
        while ((inputLine = reader.readLine()) != null) {
            System.out.println(inputLine);
            // if(inputLine.contains("synonyms")){
            // content.append(inputLine)
            // }
        }
        content = "synonyms: interstate, highway, road, ateriel road";

        if ((query.contains("interstate")) || (query.contains("route")) || (query.contains("airport"))
                || (query.contains("train")) || (query.contains("Amtrak") || (query.contains("rail")))) {
            return true;
        }
        return false;
    }

    // labor departments
    // VOSHA v. OSHA
    // unemployment is now just state
    public static boolean laborDecision(String query) {
        // should be very slim list of Fed words, VOSHA covers all private employers,
        // OSHA only covers fed and Covid things
        if ((query.contains("federal")) || (query.contains("covid")) || (query.contains("coronavirus"))) {
            return true;
        }
        return false;

    }

    // law enforcement
    public static boolean leDecision(String query) throws IOException {
        // URL url = new
        // URL(("https://www.stands4.com/services/v2/syno.php?uid=1001&tokenid=tk324324&word=consistent&format=json"));
        // HttpURLConnection http = (HttpURLConnection) url.openConnection();
        URL url = new URL(("https://www.stands4.com/services/v2/syno.php?uid=" + apiUid + "&tokenid=" + apiToken
                + "&word=immigrant&format=json"));
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("Accept", "application/json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String inputLine;
        String content;
        while ((inputLine = reader.readLine()) != null) {
            System.out.println(inputLine);
            // if(inputLine.contains("synonyms")){
            // content.append(inputLine)
            // }
        }
        content = "synonyms: immigrants, border, migrants";
        if ((query.contains("border")) || (query.contains("immigrant")) || (query.contains("ICE"))
                || (query.contains("computers")) || (query.contains("cyber")) || (query.contains("financ"))) {
            return true;
        }
        return false;
    }

    //
}
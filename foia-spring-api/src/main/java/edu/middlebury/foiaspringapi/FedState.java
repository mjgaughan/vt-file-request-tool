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

    // true if fed, false if state; just department of transportations
    public static boolean transportationDecision(String query) {
        if ((query.contains("interstate")) || (query.contains("route")) || (query.contains("airport"))
                || (query.contains("train")) || (query.contains("Amtrak"))) {
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
    public static boolean leDecision(String query) {
        if ((query.contains("border")) || (query.contains("immigrant")) || (query.contains("ICE"))
                || (query.contains("computers")) || (query.contains("cyber")) || (query.contains("financ"))) {
            return true;
        }
        return false;
    }

    //
}
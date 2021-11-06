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
    public boolean transportationDecision(String query) {
        if ((query.contains("interstate")) || (query.contains("route")) || (query.contains("airport"))
                || (query.contains("train")) || (query.contains("Amtrak"))) {
            return true;
        }
        return false;
    }

    // TODO: labor departments
    // VOSHA v. OSHA
    // unemployment is now just state
    public boolean laborDecision(String query) {
        return false;
    }

    // TODO: law enforcement
    // --CBP/ICE
    public boolean leDecision(String query) {
        return false;
    }

    //
}
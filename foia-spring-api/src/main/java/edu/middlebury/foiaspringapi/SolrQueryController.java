package edu.middlebury.foiaspringapi;

//import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api")
public class SolrQueryController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/solr")
    public Hashtable<String, Hashtable<String, String>> query(
            @RequestParam(value = "q", defaultValue = "*:*") String query) throws IOException {
        String url0 = "http://localhost:8983/solr/vtstatefiles/select?q=";
        String replaced = query.replace(" ", "+");

        URL url = new URL((url0 + replaced));
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("Accept", "application/json");
        Hashtable<String, Hashtable<String, String>> allEntries = new Hashtable<String, Hashtable<String, String>>();
        Hashtable<String, String> returnedContent = new Hashtable<String, String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String inputLine;
        // StringBuffer content = new StringBuffer();
        Integer rudimentary = 0;
        while ((inputLine = reader.readLine()) != null) {
            String[] firstSplit = inputLine.split("\\[");
            String relevantInformation = new String();
            // System.out.println("---**");
            for (int i = 0; i < firstSplit.length; i++) {
                if (firstSplit[i].contains(",")) {
                    relevantInformation = firstSplit[i].substring(0, firstSplit[i].length() - 2);
                }
            }
            if (inputLine.contains("Name_of_Agency")) {
                rudimentary += 1;
                System.out.println(returnedContent);
                // Hashtable<String, String> temporaryContent = new Hashtable<String, String>();
                // temporaryContent = returnedContent;
                allEntries.put(relevantInformation, returnedContent);
                System.out.println(allEntries);
                returnedContent.clear();
                returnedContent.put("Agency", relevantInformation);
            }
            if (inputLine.contains("Name_of_Contact")) {
                returnedContent.put("Name of Contact", relevantInformation);
            } else if (inputLine.contains("Contact_Email")) {
                returnedContent.put("Contact Email", relevantInformation);
            } else if (inputLine.contains("Contact_URL")) {
                returnedContent.put("URL", relevantInformation);
            }
            // content.append(inputLine);
        }
        // allEntries.add(returnedContent);
        // System.out.println(allEntries);

        reader.close();
        http.disconnect();

        return allEntries;

    }

}

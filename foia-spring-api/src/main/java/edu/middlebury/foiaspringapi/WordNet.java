package edu.middlebury.foiaspringapi;

//import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import java.util.ArrayList;
import java.util.Arrays;

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
import java.io.StringReader;

import edu.middlebury.foiaspringapi.FedState;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@RestController
@RequestMapping("/api")
public class WordNet {

    @Value("${stands4User}")
    private String defaultStands4User;

    @Value("${stands4Token}")
    private String defaultStands4Token;

    @GetMapping("/wordnet")
    public ArrayList<String> getSynonym(@RequestParam(value = "q", defaultValue = "*:*") String keyword,
            String stands4User, String stands4Token) throws IOException {
        if (stands4User == null) {
            stands4User = defaultStands4User;
        }
        if (stands4Token == null) {
            stands4Token = defaultStands4Token;
        }

        String url = "https://www.stands4.com/services/v2/syno.php?uid=" + stands4User + "&tokenid=" + stands4Token
                + "&word=" + keyword + "&format=xml";
        System.out.println(url);
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc);
        String docString = doc.toString();
        ArrayList<String> synonymString = new ArrayList<String>();
        char[] ch = new char[docString.length()];
        for (int i = 0; i < docString.length(); i++) {
            ch[i] = docString.charAt(i);
        }
        int j = 8;
        Boolean synonymBool = false;
        ArrayList<Character> tempSynonym2 = new ArrayList<Character>();
        while (j < docString.length()) {
            char[] synonymCheck = { ch[j - 6], ch[j - 5], ch[j - 4], ch[j - 3], ch[j - 2], ch[j - 1], ch[j] };
            char[] synonymCharCheck = { 's', 'y', 'n', 'o', 'n', 'y', 'm' };
            if (Arrays.equals(synonymCheck, synonymCharCheck) && !synonymBool) {
                synonymBool = true;
            } else if (Arrays.equals(synonymCheck, synonymCharCheck) && synonymBool) {
                synonymBool = false;
            }
            if (synonymBool) {
                if (ch[j] == ',') {
                    StringBuilder tempSynonym = new StringBuilder(tempSynonym2.size());
                    for (Character c : tempSynonym2) {
                        tempSynonym.append(c);
                    }
                    String tempString = tempSynonym.toString();
                    if (!tempString.contains("<")) {
                        if (tempString.contains(">")) {
                            tempString = tempString.substring(3);
                        } else {
                            tempString = tempString.substring(1);
                        }
                        synonymString.add(tempString);
                    }
                    tempSynonym2 = new ArrayList<Character>();
                } else {
                    tempSynonym2.add(ch[j]);
                }
            }
            j += 1;
        }
        System.out.println(synonymString);
        return synonymString;
    }
}

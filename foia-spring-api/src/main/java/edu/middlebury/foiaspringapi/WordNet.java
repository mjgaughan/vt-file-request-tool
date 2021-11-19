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
    private String stands4User;

    @Value("${stands4Token}")
    private String stands4Token;

    @GetMapping("/wordnet")
    public ArrayList<String> getSynonym(@RequestParam(value = "q", defaultValue = "*:*") String keyword)
            throws IOException {
        String url = "https://www.stands4.com/services/v2/syno.php?uid=" + stands4User + "&tokenid=" + stands4Token
                + "&word=" + keyword + "&format=xml";
        System.out.println(url);
        Document doc = Jsoup.connect(url).get();
        String docString = doc.toString();
        System.out.println(doc);
        ArrayList<Character> synonymChar = new ArrayList<Character>();
        char[] ch = new char[docString.length()];
        for (int i = 0; i < docString.length(); i++) {
            ch[i] = docString.charAt(i);
        }
        int j = 8;
        Boolean synonymBool = false;
        while (j < docString.length()) {
            char[] synonymCheck = { ch[j - 7], ch[j - 6], ch[j - 5], ch[j - 4], ch[j - 3], ch[j - 2], ch[j - 1],
                    ch[j] };
            if (synonymCheck.toString() == "synonyms" && !synonymBool) {
                synonymBool = true;
            } else if (synonymCheck.toString() == "synonyms" && synonymBool) {
                synonymBool = false;
            }
            if (synonymBool) {
                synonymChar.add(ch[j]);
            }
        }
        System.out.println(synonymChar);
        return new ArrayList<String>();
    }
}

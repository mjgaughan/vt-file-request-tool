package edu.middlebury.foiaspringapi;

// import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

@RestController
@RequestMapping("/api")
public class FedState {

    @Autowired
    private WordNet getSynonym;

    private static InputStream tokenModelIn = null;
    private static InputStream posModelIn = null;

    // true if fed, false if state; just department of transportations
    @GetMapping("/fedtrans")
    public boolean transportationDecision(String query) throws IOException {
        // OpenNLP POS tagging (lines 45-59) helped w/
        // https://www.tutorialkart.com/opennlp/pos-tagger-example-in-apache-opennlp/

        query = "I want to know who regulates the planes at burlington airport";
        tokenModelIn = new FileInputStream("src/main/resources/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
        Tokenizer tokenizer = new TokenizerME(tokenModel);
        String tokens[] = tokenizer.tokenize(query);
        posModelIn = new FileInputStream("src/main/resources/en-pos-maxent.bin");
        POSModel posModel = new POSModel(posModelIn);
        POSTaggerME posTagger = new POSTaggerME(posModel);
        String tags[] = posTagger.tag(tokens);
        ArrayList<String> queryNouns = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {
            if (tags[i].contains("NN")) {
                queryNouns.add(tokens[i]);
            }
        }
        WordNet testing = new WordNet();
        ArrayList<String> synonyms = testing.getSynonym(queryNouns.get(0));
        System.out.println(synonyms);

        if ((query.contains("interstate")) || (query.contains("route")) || (query.contains("airport"))
                || (query.contains("train")) || (query.contains("Amtrak") || (query.contains("rail")))) {
            return true;
        }
        return false;
    }

    // labor departments
    // VOSHA v. OSHA
    // unemployment is now just state
    @GetMapping("/fedlabor")
    public boolean laborDecision(String query) {
        // should be very slim list of Fed words, VOSHA covers all private employers,
        // OSHA only covers fed and Covid things
        if ((query.contains("federal")) || (query.contains("covid")) || (query.contains("coronavirus"))) {
            return true;
        }
        return false;

    }

    // law enforcement
    @GetMapping("/fedle")
    public boolean leDecision(String query) throws IOException {

        if ((query.contains("border")) || (query.contains("immigrant")) || (query.contains("ICE"))
                || (query.contains("computers")) || (query.contains("cyber")) || (query.contains("financ"))) {
            return true;
        }
        return false;
    }

    //
}
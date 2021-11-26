package edu.middlebury.foiaspringapi;

// import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public boolean transportationDecision(@RequestParam(value = "q", defaultValue = "") String query,
            String stands4User, String stands4Token) throws IOException {
        // System.out.println(query);
        // OpenNLP POS tagging (lines 45-59) helped w/
        // https://www.tutorialkart.com/opennlp/pos-tagger-example-in-apache-opennlp/
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
        for (String noun : queryNouns) {
            ArrayList<String> synonyms = testing.getSynonym(noun, stands4User, stands4Token);
            if ((synonyms.contains("interstate")) || (synonyms.contains("route")) || (synonyms.contains("airport"))
                    || (synonyms.contains("train"))
                    || (synonyms.contains("Amtrak") || (synonyms.contains("rail")) || synonyms.contains("plane"))) {
                return true;
            }

        }
        return false;
    }

    // labor departments
    // VOSHA v. OSHA
    // unemployment is now just state
    @GetMapping("/fedlabor")
    public boolean laborDecision(@RequestParam(value = "q", defaultValue = "") String query) {
        // should be very slim list of Fed words, VOSHA covers all private employers,
        // OSHA only covers fed and Covid things
        if ((query.contains("federal")) || (query.contains("covid")) || (query.contains("coronavirus"))
                || (query.contains("government"))) {
            return true;
        }
        return false;

    }

    // law enforcement
    @GetMapping("/fedle")
    public boolean leDecision(@RequestParam(value = "q", defaultValue = "") String query, String stands4User,
            String stands4Token) throws IOException {
        // OpenNLP POS tagging (lines 95-108) helped w/
        // https://www.tutorialkart.com/opennlp/pos-tagger-example-in-apache-opennlp/
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
        for (String noun : queryNouns) {
            ArrayList<String> synonyms = testing.getSynonym(noun, stands4User, stands4Token);
            System.out.println("-----");
            System.out.println(synonyms);
            if ((synonyms.contains("borderline")) || (synonyms.contains("immigrant")) || (synonyms.contains("ICE"))
                    || (synonyms.contains("computers")) || (synonyms.contains("cyber")) || (synonyms.contains("financ"))
                    || (synonyms.contains("migrant")) || (synonyms.contains("border"))) {
                return true;
            }

        }
        if ((query.contains("borderline")) || (query.contains("immigrant")) || (query.contains("ICE"))
                || (query.contains("computers")) || query.contains("cyber") || (query.contains("financ"))
                || (query.contains("migrant")) || query.contains("border") || query.contains("trafficking")) {
            return true;
        }
        return false;
    }

    //
}
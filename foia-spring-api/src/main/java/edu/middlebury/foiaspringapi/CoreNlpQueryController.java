package edu.middlebury.foiaspringapi;

//import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

public class CoreNlpQueryController {

    @GetMapping("/corenlp")
    public String query(@RequestParam(value = "q", defaultValue = "") String query) throws IOException {
        return query;
    }

    public static void coreApiCall(String[] args) {
        // https://stanfordnlp.github.io/CoreNLP/api.html
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
        props.setProperty("coref.algorithm", "neural");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // CoreDocument document = new CoreDocument(text);
    }

}

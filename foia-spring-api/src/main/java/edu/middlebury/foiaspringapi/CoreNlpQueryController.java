package edu.middlebury.foiaspringapi;

//import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
//import edu.stanford.nlp.hcoref.CorefProperties;
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

@RestController
@RequestMapping("/api")
public class CoreNlpQueryController {

    @GetMapping("/corenlp")
    public String query(@RequestParam(value = "q", defaultValue = "") String query) throws IOException {
        coreApiCall();
        return query;
    }

    public static void coreApiCall() {
        // https://stanfordnlp.github.io/CoreNLP/api.html
        // trying to follow the directions for the Simple API
        // need to change the routes in settings.json in order to make sure your machine
        // is set up
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,lemma,ner,parse,depparse,coref,kbp,quote");
        props.setProperty("coref.algorithm", "neural");
        //props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        // props.setProperty("annotators", "tokenize, ssplit");
        // props.setProperty("annotators",
        // "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
        // props.setProperty("coref.algorithm", "neural");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // CoreDocument document = new CoreDocument(text);
        // read some text in the text variable
        String text = "the quick brown fox jumped over the lazy god";

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        // System.out.println(document);
    }

}

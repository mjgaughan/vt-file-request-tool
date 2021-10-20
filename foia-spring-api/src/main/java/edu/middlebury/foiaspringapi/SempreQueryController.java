package edu.middlebury.foiaspringapi;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.hateoas.server.mvc.RepresentationModelProcessorHandlerMethodReturnValueHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class SempreQueryController {

	@GetMapping("/sempre")
	public ArrayList<String> query(@RequestParam(value = "q", defaultValue = "") String query) throws IOException {
		String url = "http://localhost:8400/sempre?q=";
		Document doc = Jsoup.connect(url + query).get();
		Element pre = doc.select("pre").first();
		String returnValue = pre.text();
		return parsingResults(returnValue);
	}

	public ArrayList<String> parsingResults(String returnValue) {
		String[] returnValueArray = returnValue.split(" ");
		ArrayList<String> lemmatizedTokens = new ArrayList<String>();
		ArrayList<String> posTags = new ArrayList<String>();
		Boolean isTokens = false;
		Boolean isTags = false;
		String previousWord = "";
		for (String word : returnValueArray) {

			if (isTokens) {
				if (word.contains("]")) {
					isTokens = false;
					// word = word[];
				}
				lemmatizedTokens.add(word);
			} else if (isTags) {
				if (word.contains("]")) {
					isTags = false;
				}
				posTags.add(word);
			}
			// opening the arrays
			if (word.contains("tokens:")) {
				isTokens = true;
			} else if (word.contains("tags:") && previousWord.contains("POS")) {
				isTags = true;
			}
			previousWord = word;
		}
		ArrayList<String> relevantTerms = new ArrayList<String>();
		for (int i = 0; i < posTags.size(); i++) {
			if (posTags.get(i).contains("NN")) {
				relevantTerms.add(lemmatizedTokens.get(i));
			}
		}
		// System.out.println(lemmatizedTokens);
		// System.out.println(posTags);
		// System.out.println(relevantTerms);
		return relevantTerms;
	}

}

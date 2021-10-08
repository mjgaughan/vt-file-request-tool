package edu.middlebury.foiaspringapi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SempreQueryController {
	
	@GetMapping("/sempre")
	public SempreQuery query(@RequestParam(value="query", defaultValue = "") String query) throws IOException {
		String url = "http://localhost:8400/sempre?q=";
		String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
		Document doc = Jsoup.connect(url + encodedQuery).get();
		Elements content = doc.getElementsByClass("answer");
		String response = null;
		for (Element answer : content) {
			response = answer.text();
		}
		SempreQuery result = new SempreQuery(query);
		result.setResponse(response);
		return result;
	}

}

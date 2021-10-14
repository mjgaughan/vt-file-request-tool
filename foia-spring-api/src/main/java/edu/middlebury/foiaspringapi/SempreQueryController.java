package edu.middlebury.foiaspringapi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
	public String query(@RequestParam(value = "q", defaultValue = "") String query) throws IOException {
		String url = "http://localhost:8400/sempre?q=";
		Document doc = Jsoup.connect(url + query).get();
		Element pre = doc.select("pre").first();
		return pre.text();
	}

}

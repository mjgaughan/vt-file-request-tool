package edu.middlebury.foiaspringapi;

public class SempreQuery {
	private String query;
	private String response;

	public SempreQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}

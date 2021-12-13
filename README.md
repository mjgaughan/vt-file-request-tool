## An Algorithmic Assist for Transparency: Locating Relevant State Agencies for Vermont File Requests
### Middlebury College, Computer Science CSCI 701 (Fall 2021)

### about

Our project provides a web application that—given an appropriate natural language query from the user—will identify the correct State of Vermont department for the user to request relevant public information from. We have implemented this using an instance of the Apache Solr search engine, a Spring Boot API framework, a simple AngularJS front end, as well as some near-term matching for demarcating federal or state jurisdiction. 

View our website [here](http://40.71.97.215/)!


# Usage
Once running, navigate to the frontend interface. Query the searchbar for anything pertaining to state agency records in Vermont. Use the returned information to file a record request to the relevant contact source as provided in the returned information.

### Limitations
Currently, this service is limited to the state of Vermont. There is also limited functionality in the demarcation between state and federal agencies.

### References
This work was heavily by the following resources: 
- [Managing the freedom of information act and federal information policy. Lotte Feinberg. 1986](https://www.jstor.org/stable/976227?refreqid=excelsior%3A6721fced974b2d46b5670068eb01bb9a&seq=1#metadata_info_tab_contents)
- [What makes a good foia request? we studied 33,000 to find out. Nicholas Dias, Rashida Kamal and Laurent Bastien. 2017](https://www.cjr.org/analysis/foia-request-how-to-study.php)
- [Inspector general says pentagon’s foia process is ’outdated’. Tony Bertuca. 2016](https://www.jstor.org/stable/insipent.32.34.08)

We also used the following technical references throughout our development:
- [Java libraries for accessing the princeton wordnet: Comparison and evaluation. Mark Findlayson. 2014](https://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.651.1152)
- [Spring Boot Documentation](https://spring.io/guides)
- [Solr Documentation](https://solr.apache.org/guide/8_11/solr-tutorial.html)


# Building
### Requirements:
- Java 8
- wget
- Node 12.18.3
- Solr 8.1
- Spring Boot 2.5.5
- Apache OpenNLP
- Angular 12.2.10

You'll also need API credentials for [Synonyms API](https://www.synonyms.com/synonyms_api.php).

### To run on local, set-up:
1. Clone Git Repository
  ``` 
  git clone https://github.com/mjgaughan/vt-file-request-tool 
  ```
2. Navigate to root Solr directory
3. Initialize Solr instance 
 ```
./bin/solr start -e cloud 
```
4. Follow prompts, using defaults for nearly everything but instead of 'gettingstarted' name index 'vtstatefiles'
5. Initialize Solr schema
``` 
curl -X POST -H 'Content-type:application/csv' --data-binary '{"add-copy-field" : {"source":"*","dest":"_text_"}}' http://localhost:8983/solr/vtstatefiles/schema
```
6. Index CSV file 
```
bin/post -c vtstatefiles ../vtfiles-temp.csv -params "f.genre.split=true&f.directed_by.split=true&f.genre.separator=|&f.directed_by.separator=|"
```
7. Navigate to root directory of Spring Boot 
```
$ cd foia-spring-api
```
8. Initialize Spring Boot
```
$ mvn spring-boot:run     
```
9. Navigate to root directory of Angular 
```
$ cd ../vt-files-frontend
```
10. Initialize Angular front end
```
$ ng serve
```
11. Happy searching!

# An Algorithmic Assist for Transparency: Streamlining FOIA Requests With a Novel Web Service
### Middlebury College, Computer Science CSCI 701 (Fall 2020)

### About our project
Our project provides a web application that—given an appropriate natural language query from the user—will identify the correct State of Vermont department for the user to request relevant public information from. We have implemented this using an instance of the Apache Solr search engine, a Spring Boot API framework, a simple AngularJS front end, as well as some near-term matching for demarcating federal or state jurisdiction.

See the README in our [project repository](https://github.com/mjgaughan/vt-file-request-tool) for information on building and using our software.

View our website [here](http://40.71.97.215/)!

## Usage
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

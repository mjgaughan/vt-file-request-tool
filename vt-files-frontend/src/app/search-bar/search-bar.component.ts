import { Component, OnInit } from '@angular/core';
import {MatFormFieldModule} from '@angular/material/form-field';  
import { MatInputModule } from '@angular/material/input';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';

interface apiEntry {  
  bio: String;  
  email: String;  
  url: String;  
  agency: String;  
  contact: String; 
  instructions: String; 
  tags: String;    
  id: String;  
}  

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  searchInput = '';
  apiResults: any;
  //var federalResults: new Array();
  federalResults: any;
  //federalResults["location"] = 0;


  constructor(private http: HttpClient) {
   }

  ngOnInit(
  ): void {
  }

  async onSearch(query: string) {
    //this.apiResults = {};
    var replaced = query.replace(" ", "+");
    await this.apiQuery(replaced);
  }

  apiQuery(query:string){
    const url = 'http://40.71.97.215:8080/api/main?q='
    var searchResults
    this.http.get(url + query, {responseType: 'text'}).toPromise().then((res) => {
      searchResults = res;
      var temporaryString = JSON.parse(searchResults);
      this.apiResults = temporaryString;
      //not sure why the bottom logic works but it seems to, could use more testing
      if (temporaryString.Federal.length != 0){
        this.federalResults = temporaryString.Federal;
        console.log(this.federalResults);
        //console.log(typeof temporaryString.Federal);
      } else if (this.federalResults != undefined) {
        this.federalResults.length = 0;
      } else {
        this.federalResults = temporaryString.Federal;
      }
      console.log(this.apiResults);
      //console.log(this.federalResults);
      return temporaryString;
  });
  }

}

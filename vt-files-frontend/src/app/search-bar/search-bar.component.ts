import { Component, OnInit } from '@angular/core';
import {MatFormFieldModule} from '@angular/material/form-field';  
import { MatInputModule } from '@angular/material/input';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  searchInput = '';
  apiResults: any;

  constructor(private http: HttpClient) {
   }

  ngOnInit(
  ): void {
  }

  async onSearch(query: string) {
    var replaced = query.replace(" ", "+");
    this.apiResults = await this.apiQuery(replaced);
  }

  apiQuery(query:string){
    const url = 'http://localhost:8080/api/solr?q='
    var searchResults
    this.http.get(url + query, {responseType: 'text'}).toPromise().then((res) => {
      searchResults = res;
      this.apiResults = searchResults;
      //console.log(this.apiResults);
      return searchResults || {};
  });
  }

}

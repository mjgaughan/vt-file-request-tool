import { Component, OnInit } from '@angular/core';
import {MatFormFieldModule} from '@angular/material/form-field';  

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  searchInput = '';

  constructor() { }

  ngOnInit(): void {
  }

  onSearch(query: string) {

  }

}

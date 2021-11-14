import { Component, OnInit, Input } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { SearchBarComponent } from '../search-bar/search-bar.component';

@Component({
  selector: 'app-return-list',
  templateUrl: './return-list.component.html',
  styleUrls: ['./return-list.component.css']
})
export class ReturnListComponent implements OnInit {
  @Input() searchContent!: SearchBarComponent;

  constructor() { }

  ngOnInit(): void {
    //console.log(this.searchContent.apiResults)
  }

}

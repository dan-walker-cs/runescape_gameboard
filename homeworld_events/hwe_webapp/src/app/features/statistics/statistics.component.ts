import { HttpClient } from "@angular/common/http";
import { Component, inject, OnInit } from "@angular/core";

@Component({
    selector: 'app-statistics',
    standalone: true,
    imports: [],
    templateUrl: './statistics.component.html',
    styleUrls: [ './statistics.component.css' ]
})
export class StatisticsComponent implements OnInit {
    private http = inject(HttpClient);
    
    async ngOnInit(): Promise<void> {
        
    }
}
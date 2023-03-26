import { Component, OnInit } from '@angular/core';
import { UserStat } from '../../../model/user-stat';
import { StatisticsService } from '../../../service/statistics.service';

@Component({
  selector: 'app-top-buyers',
  templateUrl: './top-buyers.component.html',
  styleUrls: ['./top-buyers.component.css']
})
export class TopBuyersComponent implements OnInit {

    userStat: UserStat;
    userStats: UserStat[];

    constructor(private statisticsService: StatisticsService) {
        this.userStat = new UserStat();
    }

    ngOnInit() {
        this.statisticsService.getTop10Buyers()
            .subscribe(data => {this.userStats = data});
    }

}

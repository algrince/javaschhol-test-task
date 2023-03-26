import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { NgForm } from '@angular/forms';
import { StatisticsService } from '../../../service/statistics.service';
import { RevenueResult } from '../../../model/revenue-result';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-revenue',
  templateUrl: './revenue.component.html',
  styleUrls: ['./revenue.component.css']
})
export class RevenueComponent {

    @ViewChild('selectRevenue') selectRevenue: NgForm;

    months: string;
    weeks: string;
    period: string;
    number: number;
    results: any;
    revenueResult: RevenueResult;
    revenueResults: RevenueResult[];
    chartHidden = true;

  view: any[] = [700, 400];

  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'Period';
  showYAxisLabel = true;
  yAxisLabel = 'Revenue';


    constructor(private statisticsService: StatisticsService) {
        this.revenueResult = new RevenueResult();
    }

    onSubmit() {
        this.period = this.selectRevenue.value.period;
        this.number = this.selectRevenue.value.number;

        if (this.period === "months") {
            this.statisticsService.getMonthlyRevenue({number: this.number})
                .subscribe(data => {
                    this.revenueResults = data;
                    this.constructChart();});
        } else if (this.period === "weeks") {
            this.statisticsService.getWeeklyRevenue({number: this.number})
                .subscribe(data => {
                    this.revenueResults = data;
                    this.constructChart();});
        }
    }

    constructChart() {
        this.chartHidden = false;
        this.results = this.revenueResults.map(result => {
                    return {
                        name: result.period,
                        value: result.revenue
                    }
                });
        }


}

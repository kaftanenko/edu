import { Component, OnInit } from '@angular/core';
import { JenkinsFolderDetails } from './model/jenkins-folder-details';
import { JenkinsInfoService } from './service/jenkins-info.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  jenkinsStateInfo: JenkinsFolderDetails | any;
  jenkinsStateInfoTimestamp: Date;

  constructor(private jenkinsInfoService: JenkinsInfoService) { }

  ngOnInit() {

    this.jenkinsInfoService.getSummaryInfo("assets/data/jenkins-info-mock-data.json")
      .subscribe(data => {
        console.log(data);
        this.jenkinsStateInfo = data;
        this.jenkinsStateInfoTimestamp = new Date();
      });
  }

}

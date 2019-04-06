import { Component } from '@angular/core';
import { JenkinsFolderDetails } from './model/jenkins-folder-details';
import { JenkinsInfoService } from './service/jenkins-info.service';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Rx';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  {

  //RESOURCE_URL:string = "http://10.35.50.137/JENKINS.JSN";
  RESOURCE_URL:string = "assets/data/jenkins-info-mock-data.json";

  jenkinsStateInfo: JenkinsFolderDetails | any;
  jenkinsStateInfoTimestamp: Date;

  pollingData: any;      

  constructor(private jenkinsInfoService: JenkinsInfoService) {

    this.pollingData = Observable.interval(5000).subscribe(
       () => this.jenkinsInfoService.getSummaryInfo(this.RESOURCE_URL)
        .subscribe(data => {
          console.log(data);
          this.jenkinsStateInfo = data;
          this.jenkinsStateInfoTimestamp = new Date();
        })
      );
  }


  ngOnDestroy() {
    this.pollingData.unsubscribe();
  }

}

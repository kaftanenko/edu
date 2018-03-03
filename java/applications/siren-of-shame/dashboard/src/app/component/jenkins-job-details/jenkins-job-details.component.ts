import { Component, Input, OnInit } from '@angular/core';
import { JobDetails } from '../../model/jenkins-job-details';

@Component({
  selector: 'app-jenkins-job-details',
  templateUrl: './jenkins-job-details.component.html',
  styleUrls: ['./jenkins-job-details.component.css']
})
export class JobDetailsComponent implements OnInit {

  @Input()
  jobDetails: JobDetails;

  constructor() { }

  ngOnInit() {
  }

  lastStateStyle() {
    return "jenkins-job-details " + this.jobDetails.color;
  }

}

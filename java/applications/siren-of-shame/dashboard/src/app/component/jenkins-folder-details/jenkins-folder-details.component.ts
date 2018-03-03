import { Component, Input, OnInit } from '@angular/core';
import { JenkinsFolderDetails } from '../../model/jenkins-folder-details';

@Component({
  selector: 'app-jenkins-folder-details',
  templateUrl: './jenkins-folder-details.component.html',
  styleUrls: ['./jenkins-folder-details.component.css']
})
export class JenkinsFolderDetailsComponent implements OnInit {

  @Input()
  groupDetails: JenkinsFolderDetails;

  constructor() { }

  ngOnInit() {
  }

  getGroupName() {

    if (this.groupDetails.fullDisplayName) {      
      return (this.groupDetails.fullDisplayName)
    } else {
      return this.groupDetails.name;
    }
  }

  getFolders() {
    return this.getJobsByClassRegEx("^.*\.Folder$");
  }

  getWorkflowJobs() {
    return this.getJobsByClassRegEx("^.*\.WorkflowJob$");
  }

  getJobsByClassRegEx(_classRegExPattern: string) {

    if (this.groupDetails.jobs) {

      return this.groupDetails.jobs
        .filter(job => job._class.match(_classRegExPattern));
    } else {
      return [];
    }
  }

}

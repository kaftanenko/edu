import { Component, Input, OnInit } from '@angular/core';
import { JenkinsFolderDetails } from '../../model/jenkins-folder-details';

@Component({
  selector: 'app-jenkins-folder-details',
  templateUrl: './jenkins-folder-details.component.html',
  styleUrls: ['./jenkins-folder-details.component.css']
})
export class JenkinsFolderDetailsComponent implements OnInit {

  @Input()
  hierarchyLevel: number;

  @Input()
  groupDetails: JenkinsFolderDetails;

  constructor() { }

  ngOnInit() {
  }

  getGroupName() {

    if (this.groupDetails.fullDisplayName) {
      // return (this.groupDetails.fullDisplayName)
      return "... » " + this.groupDetails.name;
    } else {
      return "... » " + this.groupDetails.name;
    }
  }

  getFolders() {
    return this.getJobsByClassRegEx("^.*\.Folder$");
  }

  getWorkflowJobs() {
    return this.getJobsByClassRegEx("^.*\.(MavenModuleSet|FreeStyleProject|WorkflowJob)$");
  }

  // ...

  getBootstrapColumnClasses() {

    if (this.getFolders().length <= 1) {
      return "col-lg-12 col-md-12 col-sm-12 col-xs-12";
    } else {
      switch (this.hierarchyLevel) {
        case 0:
          return "col-lg-6 col-md-6 col-sm-6 col-xs-6";
        case 1:
          return "col-lg-6 col-md-6 col-sm-6 col-xs-12";
        case 2:
          return "col-lg-6 col-md-6 col-sm-12 col-xs-12";
        case 3:
          return "col-lg-6 col-md-12 col-sm-12 col-xs-12";
        default:
          return "col-lg-12 col-md-12 col-sm-12 col-xs-12";
      }
    }
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
